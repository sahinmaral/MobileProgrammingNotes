package com.example.learnpassvariablewithintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        Intent intent = getIntent();

        Person person1 = (Person) intent.getSerializableExtra("person1");
        String message = intent.getStringExtra("message");

        TextView textViewPersonInfo = (TextView)findViewById(R.id.textViewPersonInfo);
        TextView textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        textViewMessage.setText(message);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        textViewPersonInfo.setText(person1.getName().concat(" ")
                .concat(person1.getSurname()).concat(" ")
                .concat(sdf.format(person1.getBirthDate().getTime()))
        );
    }
}