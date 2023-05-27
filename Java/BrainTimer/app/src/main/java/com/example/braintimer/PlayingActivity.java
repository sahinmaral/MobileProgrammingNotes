package com.example.braintimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.braintimer.db.BrainTimerAppDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayingActivity extends AppCompatActivity {

    TextView textViewTimer;
    TextView textViewQuestionCount;
    TextView textViewQuestion;
    TextView textViewAnswer1;
    TextView textViewAnswer2;
    TextView textViewAnswer3;
    TextView textViewAnswer4;
    SharedPreferences sharedPreferences;
    CountDownTimer timer;
    String username;
    int round = 0;
    int firstNumber = 0;
    int secondNumber = 0;
    int answer = 0;
    int questionCount = 0;
    BrainTimerAppDb db;

    @Override
    public void onBackPressed() {

        timer.cancel();
        long remainingTimer = Integer.parseInt(textViewTimer.getText().toString()) * 1000;

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Warning");
        dialog.setMessage("Are you sure you want to exit the game ?");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer = initializeTimerAndReturn(remainingTimer,1000);
                timer.start();
            }
        });

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),StartingActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        db = new BrainTimerAppDb(getApplicationContext());

        Intent intent = getIntent();
        username = intent.getStringExtra("name");

        textViewQuestionCount = findViewById(R.id.textViewQuestionCount);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewAnswer1 = findViewById(R.id.textViewAnswer1);
        textViewAnswer2 = findViewById(R.id.textViewAnswer2);
        textViewAnswer3 = findViewById(R.id.textViewAnswer3);
        textViewAnswer4 = findViewById(R.id.textViewAnswer4);
        textViewTimer = findViewById(R.id.textViewTimer);

        timer = initializeTimerAndReturn(30000,1000);

        timer.start();

        sharedPreferences = this.getSharedPreferences(getApplicationInfo().name, Context.MODE_PRIVATE);
        questionCount = sharedPreferences.getInt("questionCount",10);
        textViewQuestionCount.setText(String.format("%d/%d",round,questionCount));

        loadQuestion();
        loadAnswers();
    }
    private void loadQuestion(){
        Random rand = new Random();
        firstNumber = rand.nextInt(100) + 1;
        secondNumber = rand.nextInt(100) + 1;
        answer = firstNumber + secondNumber;

        textViewQuestion.setText(String.format("%d + %d",firstNumber,secondNumber));
    }
    private void selectAnswer(int selectedAnswer){

        timer.cancel();
        long remainingTimer = Integer.parseInt(textViewTimer.getText().toString()) * 1000;

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Warning");
        dialog.setMessage("Are you sure you want to choose this answer ?");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer = initializeTimerAndReturn(remainingTimer,1000);
                timer.start();
            }
        });

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkAnswer(selectedAnswer);
            }
        });

        dialog.show();

    }
    private void checkAnswer(int selectedAnswer){
        if(selectedAnswer != answer){

            db.insertScore(username,round,questionCount);

            Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
            intent.putExtra("status","lose");
            intent.putExtra("correctQuestions",round);
            intent.putExtra("totalRounds",questionCount);
            startActivity(intent);
        }else if(round == questionCount){
            round++;
            Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
            intent.putExtra("status","win");
            intent.putExtra("correctQuestions",questionCount);
            intent.putExtra("totalRounds",questionCount);
            startActivity(intent);
        }else{
            round++;

            loadQuestion();
            loadAnswers();
            textViewQuestionCount.setText(String.format("%d/%d",round,questionCount));
        }

    }
    private void loadAnswers(){
        Random rand = new Random();
        int firstAnswer = rand.nextInt(200) + 2;
        int secondAnswer = rand.nextInt(200) + 2;
        int thirdAnswer = rand.nextInt(200) + 2;
        int realAnswer = answer;

        TextView[] textViews = new TextView[]{textViewAnswer1,textViewAnswer2,textViewAnswer3,textViewAnswer4};
        int textViewCounter = 0;

        List<Integer> answers = new ArrayList<>();
        answers.add(firstAnswer);
        answers.add(secondAnswer);
        answers.add(thirdAnswer);
        answers.add(realAnswer);

        while(answers.size() != 0){
            Random answersRandom = new Random();
            int randomAnswerIndex = answersRandom.nextInt(answers.size());

            int answer = answers.get(randomAnswerIndex);
            textViews[textViewCounter].setText(Integer.toString(answer));

            textViews[textViewCounter].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectAnswer(answer);
                }
            });
            textViewCounter++;
            answers.remove(randomAnswerIndex);
        }

    }
    private CountDownTimer initializeTimerAndReturn(long millisInFuture,long countDownInterval){
        return new CountDownTimer(millisInFuture,countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(Long.toString(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                intent.putExtra("status","lose");
                intent.putExtra("correctQuestions",round);
                intent.putExtra("totalRounds",questionCount);
                startActivity(intent);
            }
        };
    }
}