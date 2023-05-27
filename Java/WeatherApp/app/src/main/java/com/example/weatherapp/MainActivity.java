package com.example.weatherapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;
import android.widget.Toast;

import com.example.weatherapp.Services.LocationService;

public class MainActivity extends AppCompatActivity {

    static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, new LoadingFragment(this));
        fragmentTransaction.commit();

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fetchIfPermissionIsGranted();

        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_REQUEST_CODE);
        }
    }

    private void fetchIfPermissionDenied() {
        Log.d("fetchStatus","false");
        try {
            WeatherFetch fetch = new WeatherFetch(this, null);
            fetch.execute();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fetchIfPermissionIsGranted() {
        try {
            Location lastLocation = LocationService.getLocationWithCheckNetworkAndGPS(this);
            WeatherFetch fetch = new WeatherFetch(this, lastLocation);
            fetch.execute();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchIfPermissionIsGranted();
            } else {
                fetchIfPermissionDenied();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}