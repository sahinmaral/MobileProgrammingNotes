package com.example.learnpassvariablewithintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        Intent intent = getIntent();
        Log.d("myTag",intent.getStringExtra("message"));
        String message = intent.getStringExtra("message");
        TextView textView = (TextView) findViewById(R.id.textViewMessage);
        textView.setText(message);
    }
}