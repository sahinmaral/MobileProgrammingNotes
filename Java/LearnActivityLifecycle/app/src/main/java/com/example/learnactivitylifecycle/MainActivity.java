package com.example.learnactivitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toast toast = Toast.makeText(getApplicationContext(),"Main Activity is started", Toast.LENGTH_SHORT);
       toast.show();
    }

    public void handleClick(View view){
        Intent intent = new Intent(getApplicationContext(),ExtraActivity.class);
        startActivity(intent);
    }
}