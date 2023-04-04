package com.example.braintimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.braintimer.db.BrainTimerAppDb;

public class ScoreboardActivity extends AppCompatActivity {

    TableLayout tableLayoutScoreboard;
    BrainTimerAppDb db;
    String previousRoute = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Intent intent = getIntent();
        previousRoute = intent.getStringExtra("previousRoute");

        db = new BrainTimerAppDb(getApplicationContext());

        tableLayoutScoreboard = findViewById(R.id.tableLayoutScoreboard);

        db.loadOrderedScoreboard(tableLayoutScoreboard);
    }
}