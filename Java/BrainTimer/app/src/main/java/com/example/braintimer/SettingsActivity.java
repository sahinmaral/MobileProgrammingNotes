package com.example.braintimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    SeekBar seekBarQuestionCount;
    TextView textViewQuestionCount;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBarQuestionCount = findViewById(R.id.seekBarQuestionCount);
        textViewQuestionCount = findViewById(R.id.textViewQuestionCount);

        sharedPreferences = this.getSharedPreferences(getApplicationInfo().name, Context.MODE_PRIVATE);

        int questionCount = sharedPreferences.getInt("questionCount",10);
        textViewQuestionCount.setText(Integer.toString(questionCount));
        seekBarQuestionCount.setProgress(questionCount);

        seekBarQuestionCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewQuestionCount.setText(Integer.toString(seekBarQuestionCount.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        textViewQuestionCount.setText(Integer.toString(seekBarQuestionCount.getProgress()));

    }

    public void handleSaveSettings(View view){
        int questionCount = seekBarQuestionCount.getProgress();
        sharedPreferences = this.getSharedPreferences(getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("questionCount",questionCount);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(),StartingActivity.class);
        startActivity(intent);
    }
}