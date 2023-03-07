package com.example.learnpassvariablewithintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(getApplicationContext(),ExtraActivity.class);
    }
    public void handleSubmit(View view){
        EditText editText = (EditText) findViewById(R.id.editTextMessage);

        intent.putExtra("message",editText.getText().toString());
        startActivity(intent);
    }
}