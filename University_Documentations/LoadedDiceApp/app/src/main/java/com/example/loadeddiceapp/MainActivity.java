package com.example.loadeddiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewFirstDice;
    ImageView imageViewSecondDice;
    ImageView imageViewBackground;

    boolean cheatActivatedStatus = false;

    HashMap<String,Double> normalPossibilities = new HashMap<>();
    HashMap<String,Double> cheatedPossibilities = new HashMap<>();
    List<String> allDices = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDices();
        loadNormalPossibilities();
        loadCheatedPossibilities();

        imageViewFirstDice = (ImageView)findViewById(R.id.imageViewFirstDice);
        imageViewSecondDice = (ImageView)findViewById(R.id.imageViewSecondDice);
        imageViewBackground = (ImageView)findViewById(R.id.imageViewBackground);

        imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheatActivatedStatus = true;
            }
        });

    }

    private void setImageViewByRandomNumber(ImageView imageView,int randomNumber){
        switch (randomNumber){
            case 1:
                imageView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice5);
                break;
            default:
                imageView.setImageResource(R.drawable.dice6);
                break;
        }
    }

    private void loadNormalPossibilities(){
        for (int i=0; i < allDices.size(); i++){
            normalPossibilities.put(allDices.get(i),2.778);
        }
    }

    private void loadCheatedPossibilities(){
        LinkedList<String> evenDices = new LinkedList<>(Arrays.asList("1-1", "2-2", "3-3", "4-4", "5-5", "6-6"));

        for (int i=0; i < allDices.size(); i++){
            if(evenDices.contains(allDices.get(i))){
                cheatedPossibilities.put(allDices.get(i),8.0);
            }else{
                cheatedPossibilities.put(allDices.get(i),1.733);
            }
        }
    }

    private void loadDices(){
        for (int i=1; i <= 6; i++){
            for(int k=1; k <= 6; k++){
                allDices.add(Integer.toString(i).concat("-").concat(Integer.toString(k)));
            }
        }
    }
    public void rollDice(View view){

        Random rand = new Random();
        int randomNumber = rand.nextInt(100);
        double sumPossibilities = 0;
        String lastDiceNumbers = "";
        int index=0;

        if(cheatActivatedStatus){

            while(sumPossibilities < randomNumber){
                sumPossibilities = cheatedPossibilities.get(allDices.get(index)) + sumPossibilities;
                lastDiceNumbers = allDices.get(index);
                index++;
            }

            int firstDice = Integer.parseInt(lastDiceNumbers.substring(0,1));
            int secondDice = Integer.parseInt(lastDiceNumbers.substring(2,3));

            setImageViewByRandomNumber(imageViewFirstDice,firstDice);
            setImageViewByRandomNumber(imageViewSecondDice,secondDice);

            cheatActivatedStatus = false;
        }else{

            while(sumPossibilities < randomNumber){
                sumPossibilities = normalPossibilities.get(allDices.get(index)) + sumPossibilities;
                lastDiceNumbers = allDices.get(index);
                index++;
            }

            int firstDice = Integer.parseInt(lastDiceNumbers.substring(0,1));
            int secondDice = Integer.parseInt(lastDiceNumbers.substring(2,3));
            setImageViewByRandomNumber(imageViewFirstDice,firstDice);
            setImageViewByRandomNumber(imageViewSecondDice,secondDice);

        }



    }
}