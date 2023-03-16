package com.example.loadeddiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setIntentToPlayingActivity(View view){
        intent = new Intent(getApplicationContext(),PlayingActivity.class);
        startActivity(intent);
    }

    public void setIntentToStatisticsActivity(View view){
        intent = new Intent(getApplicationContext(),StatisticsActivity.class);
        startActivity(intent);
    }

}