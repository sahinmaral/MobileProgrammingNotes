package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCookingType;
    TextView textViewTimer;
    Button buttonStartProcess;
    Button buttonStopProcess;
    String[] cookingTypes;
    HashMap<String,Integer> cookingTypeDictionary;
    CountDownTimer timer;
    int remainingSeconds = 0;
    int remainingMinutes = 0;

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCookingType = findViewById(R.id.spinnerCookingType);
        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStartProcess = findViewById(R.id.buttonStartProcess);
        buttonStopProcess = findViewById(R.id.buttonStopProcess);

        cookingTypeDictionary = new HashMap<>();

        cookingTypeDictionary.put("Soft Boiled",(1000*60*5));
        cookingTypeDictionary.put("Medium Boiled",(1000*60*7));
        cookingTypeDictionary.put("Hard Boiled",(1000*60*10));

        cookingTypes = cookingTypeDictionary.keySet().toArray(new String[0]);

        ArrayAdapter<String> cookingTypeAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,cookingTypes);
        spinnerCookingType.setAdapter(cookingTypeAdapter);
        spinnerCookingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                remainingMinutes = 0;
                remainingSeconds = 0;

                int totalMillis = cookingTypeDictionary.get(cookingTypes[position]);
                remainingMinutes = (totalMillis / 1000) / 60;
                remainingSeconds = (totalMillis / 1000) % 60;

                String minutesFormat = remainingMinutes < 10 ? String.format("0%d",remainingMinutes) : Integer.toString(remainingMinutes);
                String secondsFormat = remainingSeconds < 10 ? String.format("0%d",remainingSeconds) : Integer.toString(remainingSeconds);

                textViewTimer.setText(String.format("%s:%s",minutesFormat,secondsFormat));

                setTimer(totalMillis);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm);

        setTimer(cookingTypeDictionary.get(cookingTypes[spinnerCookingType.getSelectedItemPosition()]));
    }

    private void setTimer(int remainingMillis){
        timer = new CountDownTimer(remainingMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                remainingMinutes = (int) ((millisUntilFinished / 1000) / 60);
                remainingSeconds = (int) ((millisUntilFinished / 1000) % 60);

                String minutesFormat = remainingMinutes < 10 ? String.format("0%d",remainingMinutes) : Integer.toString(remainingMinutes);
                String secondsFormat = remainingSeconds < 10 ? String.format("0%d",remainingSeconds) : Integer.toString(remainingSeconds);

                textViewTimer.setText(String.format("%s:%s",minutesFormat,secondsFormat));
            }

            public void onFinish() {
                textViewTimer.setText("00:00");
                if(remainingMinutes == 0 && remainingSeconds == 0){
                    mediaPlayer.start();
                }
                timer.cancel();
            }
        };
    }
    public void stopProcess(View view){
        timer.onFinish();
    }
    public void startProcess(View view){
        setTimer(cookingTypeDictionary.get(cookingTypes[spinnerCookingType.getSelectedItemPosition()]));
        timer.start();
    }
}