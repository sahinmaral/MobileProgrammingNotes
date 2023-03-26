package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class SettingActivity extends AppCompatActivity {
    ConstraintLayout background;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SeekBar seekBarColorSettings;
    int colorCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        seekBarColorSettings = findViewById(R.id.seekBarColorSettings);
        background = findViewById(R.id.background);

        preferences = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = preferences.edit();
        String backgroundColor = preferences.getString("backgroundColor","#0000ff");

        colorCode = Color.parseColor(backgroundColor);
        background.setBackgroundColor(colorCode);

        int red = Color.red(colorCode);
        System.out.println(red);

        seekBarColorSettings.setProgress(red);

        seekBarColorSettings.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorCode = Color.rgb(progress, 0, 255-progress);
                background.setBackgroundColor(colorCode);

                editor.putString("backgroundColor",String.format("#%06X", (0xFFFFFF & colorCode)));
                editor.commit();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void getMainActivity(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}