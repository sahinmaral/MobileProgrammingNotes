package com.example.learnpassvariablewithintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.GregorianCalendar;

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

        Person person1 = new Person("Sahin","MARAL",new GregorianCalendar(2000,1,11));

        intent.putExtra("person1",person1);
        intent.putExtra("message",editText.getText().toString());
        startActivity(intent);
    }
}