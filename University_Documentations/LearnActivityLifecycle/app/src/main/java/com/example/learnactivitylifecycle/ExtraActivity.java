package com.example.learnactivitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        Toast toast = Toast.makeText(getApplicationContext(),"Second Activity is started", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast toast = Toast.makeText(getApplicationContext(),"Second Activity is paused", Toast.LENGTH_SHORT);
        toast.show();
    }
}