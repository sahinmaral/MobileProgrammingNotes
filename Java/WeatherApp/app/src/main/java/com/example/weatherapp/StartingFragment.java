package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class StartingFragment extends Fragment {

    WeakReference<Context> mainContext;
    TextView textViewHeaderWeatherMaxToday;
    TextView textViewHeaderWeatherMinToday;
    ImageView imageViewWeatherIconToday;
    ArrayList<WeatherInformation> weatherInformations;
    TextView textViewCloudinessValue;
    TextView textViewHumidityValue;
    TextView textViewWindSpeedValue;

    public StartingFragment(ArrayList<WeatherInformation> weatherInformations,WeakReference<Context> mainContext) {
        this.weatherInformations = weatherInformations;
        this.mainContext = mainContext;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starting, container, false);

        imageViewWeatherIconToday = view.findViewById(R.id.imageViewWeatherIconToday);
        textViewHeaderWeatherMaxToday = view.findViewById(R.id.textViewHeaderWeatherMaxToday);
        textViewHeaderWeatherMinToday = view.findViewById(R.id.textViewHeaderWeatherMinToday);

        textViewCloudinessValue = view.findViewById(R.id.textViewCloudinessValue);
        textViewHumidityValue = view.findViewById(R.id.textViewHumidityValue);
        textViewWindSpeedValue = view.findViewById(R.id.textViewWindSpeedValue);

        WeatherInformation todayWeatherInformation = weatherInformations.get(0);

        int drawableResourceId = this.getResources().getIdentifier("weathericon_".concat(todayWeatherInformation.getIconName()), "drawable", mainContext.get().getPackageName());
        imageViewWeatherIconToday.setImageResource(drawableResourceId);

        int roundedMaxTemp = (int) todayWeatherInformation.getMaxTemp();
        int roundedMinTemp = (int) todayWeatherInformation.getMinTemp();

        textViewHeaderWeatherMaxToday.setText(Integer.toString(roundedMaxTemp));
        textViewHeaderWeatherMinToday.setText(Integer.toString(roundedMinTemp));

        textViewCloudinessValue.setText(Double.toString(todayWeatherInformation.getCloudiness()));
        textViewHumidityValue.setText(Double.toString(todayWeatherInformation.getHumidity()));
        textViewWindSpeedValue.setText(Double.toString(todayWeatherInformation.getWindSpeed()));

        RecyclerView recyclerViewOtherDaysWeatherCondition = view.findViewById(R.id.recyclerViewOtherDaysWeatherCondition);

        WeatherInformation[] filteredWeatherInformations = {
                weatherInformations.get(7),
                weatherInformations.get(15),
                weatherInformations.get(23),
                weatherInformations.get(31),
                weatherInformations.get(39),
        };

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(view.getContext(), filteredWeatherInformations);
        recyclerViewOtherDaysWeatherCondition.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewOtherDaysWeatherCondition.setLayoutManager(linearLayoutManager);

        return view;
    }
}