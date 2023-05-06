package com.example.weatherapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class WeatherFetch extends AsyncTask<Void,Void, ArrayList<WeatherInformation>> {
    private WeakReference<Context> mainContext;
    private Location lastLocation;

    WeatherFetch(Context mainContext, Location lastLocation){
        this.mainContext = new WeakReference<>(mainContext);
        this.lastLocation = lastLocation;
    }

    @Override
    protected void onPostExecute(ArrayList<WeatherInformation> weatherInformations) {
        super.onPostExecute(weatherInformations);
        if(mainContext.get() != null){
            AppCompatActivity activity = (AppCompatActivity)mainContext.get();

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new StartingFragment(weatherInformations,mainContext));
            fragmentTransaction.commit();
        }

    }

    @Override
    protected ArrayList<WeatherInformation> doInBackground(Void... voids) {
        try {
            String fetchUrl;
            ApplicationInfo applicationInfo = mainContext.get().getApplicationContext().getPackageManager().getApplicationInfo(mainContext.get().getPackageName(),PackageManager.GET_META_DATA);
            String openWeatherAPIKEY = String.valueOf(applicationInfo.metaData.get("WEATHER_API_KEY"));
            if(lastLocation == null){
                fetchUrl = String.format("https://api.openweathermap.org/data/2.5/forecast?q=Istanbul&appid=%s&units=metric",openWeatherAPIKEY);
            }else{
                fetchUrl = String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s&units=metric",lastLocation.getLatitude(),lastLocation.getLongitude(),openWeatherAPIKEY);
            }

            Log.d("fetchURL",fetchUrl);
            URL url = new URL(fetchUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = bufferedReader.readLine()) != null){
                    response.append(inputLine);
                }
                bufferedReader.close();
                httpURLConnection.disconnect();

                Gson gson = new Gson();
                CustomResponse customResponse = gson.fromJson(response.toString(),CustomResponse.class);

                ArrayList<WeatherInformation> weatherInformations= new ArrayList<>();

                for(WeatherResponse listItem : customResponse.list){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(listItem.dt * 1000L);

                    WeatherInformation weatherInformation = new WeatherInformation(
                            listItem.clouds.all,
                            listItem.wind.speed,
                            listItem.main.humidity,
                            listItem.main.temp_max,
                            listItem.main.temp_min,
                            listItem.weather[0].icon,
                            calendar,
                            listItem.weather[0].main);

                    weatherInformations.add(weatherInformation);
                }

                return weatherInformations;
            }
            else if (responseCode >= 400 && responseCode < 500){
                throw new HttpRetryException("Client error",responseCode);
            }else if(responseCode >= 500 && responseCode < 600){
                throw new HttpRetryException("Server error",responseCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
