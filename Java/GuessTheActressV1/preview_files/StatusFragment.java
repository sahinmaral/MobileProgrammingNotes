package com.example.guesstheactressv1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class StatusFragment extends Fragment {

    Context mainContext;
    TextView textViewCorrectQuestionCount;
    TextView textViewTotalQuestionCount;
    Button buttonStartAgain;
    int totalActressCount;
    int correctGuessedActressCount;
    public StatusFragment(int totalActressCount,int correctGuessedActressCount,Context mainContext) {
        this.totalActressCount = totalActressCount;
        this.correctGuessedActressCount = correctGuessedActressCount;
        this.mainContext = mainContext;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_status, container, false);
        textViewCorrectQuestionCount = view.findViewById(R.id.textViewCorrectQuestionCount);
        textViewTotalQuestionCount = view.findViewById(R.id.textViewTotalQuestionCount);
        buttonStartAgain = view.findViewById(R.id.buttonStartAgain);

        buttonStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        });

        textViewTotalQuestionCount.setText(Integer.toString(totalActressCount));
        textViewCorrectQuestionCount.setText(Integer.toString(correctGuessedActressCount));

        return view;
    }


    public void handleClick(View view) {
        Intent intent = new Intent(mainContext.getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}