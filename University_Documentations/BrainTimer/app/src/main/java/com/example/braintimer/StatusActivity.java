package com.example.braintimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {

    TextView textViewStatus;
    TextView textViewTotalQuestionCount;
    TextView textViewCorrectQuestionCount;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),StartingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        textViewStatus = findViewById(R.id.textViewStatus);
        textViewCorrectQuestionCount = findViewById(R.id.textViewCorrectQuestionCount);
        textViewTotalQuestionCount = findViewById(R.id.textViewTotalQuestionCount);

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        int correctQuestions = intent.getIntExtra("correctQuestions",0);
        int totalRounds = intent.getIntExtra("totalRounds",10);

        textViewCorrectQuestionCount.setText(Integer.toString(correctQuestions));
        textViewTotalQuestionCount.setText(Integer.toString(totalRounds));

        if (status.equals("win")) {
            textViewStatus.setText("YOU WIN");
            textViewStatus.setTextColor(Color.parseColor("#023020"));
        }else if(status.equals("lose")){
            textViewStatus.setText("YOU LOSE");
            textViewStatus.setTextColor(Color.parseColor("#8B0000"));
        }
    }

    public void handleStartAgain(View view){
        Intent intent = new Intent(getApplicationContext(),StartingActivity.class);
        startActivity(intent);
    }

    public void handleShowScore(View view){
        Intent intent = new Intent(getApplicationContext(),ScoreboardActivity.class);
        startActivity(intent);
    }
}