package com.example.braintimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class StartingActivity extends AppCompatActivity {

    EditText editTextName;
    TextView textViewNameErrors;

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        editTextName = findViewById(R.id.editTextName);
        textViewNameErrors = findViewById(R.id.textViewNameErrors);
    }

    public void handleOpenSettings(View view){
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);
    }

    public void handleShowScores(View view){
        Intent intent = new Intent(getApplicationContext(), ScoreboardActivity.class);
        startActivity(intent);
    }

    public void handleStartGame(View view){
        String name = editTextName.getText().toString();

        if(name.length() == 0){
            textViewNameErrors.setText("Please enter name");
            textViewNameErrors.setVisibility(View.VISIBLE);
        }else if(name.length() > 15 || name.length() < 2){
            textViewNameErrors.setText("Name length must be between 2 and 15");
            textViewNameErrors.setVisibility(View.VISIBLE);
        }else{
            textViewNameErrors.setVisibility(View.INVISIBLE);
            textViewNameErrors.setText("");
            Intent intent = new Intent(getApplicationContext(),PlayingActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }
    }


}