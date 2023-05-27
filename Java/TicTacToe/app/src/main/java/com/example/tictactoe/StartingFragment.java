package com.example.tictactoe;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;


public class StartingFragment extends Fragment {

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;
    ImageButton button6;
    ImageButton button7;
    ImageButton button8;
    ImageButton button9;

    String player1States = "";
    String player2States = "";
    Players currentPlayer = Players.PLAYER1;
    Drawable drawable_x;
    Drawable drawable_o;

    final String[] WINNING_STATES = {"123","147","159","258","357","369","456","789"};

    public StartingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starting, container, false);

        drawable_x = ResourcesCompat.getDrawable(getResources(), R.drawable.x, null);
        drawable_o = ResourcesCompat.getDrawable(getResources(), R.drawable.o, null);
        
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);

        button1.setOnClickListener(v -> handleClickButton(button1));
        button2.setOnClickListener(v -> handleClickButton(button2));
        button3.setOnClickListener(v -> handleClickButton(button3));
        button4.setOnClickListener(v -> handleClickButton(button4));
        button5.setOnClickListener(v -> handleClickButton(button5));
        button6.setOnClickListener(v -> handleClickButton(button6));
        button7.setOnClickListener(v -> handleClickButton(button7));
        button8.setOnClickListener(v -> handleClickButton(button8));
        button9.setOnClickListener(v -> handleClickButton(button9));

        return view;
    }

    private void handleClickButton(ImageButton button){
        String idAsString = button.getResources().getResourceEntryName(button.getId());
        String state = idAsString.split("button")[1];

        if(!player1States.contains(state) && !player2States.contains(state)){
            if(currentPlayer == Players.PLAYER1){
                player1States = player1States + state;

                checkWinningState();

                currentPlayer = Players.PLAYER2;
                changeBackgroundOfClickedButton(button,drawable_x);
            }else{
                player2States = player2States + state;

                checkWinningState();

                currentPlayer = Players.PLAYER1;
                changeBackgroundOfClickedButton(button,drawable_o);
            }
        }
    }

    private void checkWinningState(){

        for(String allStates : WINNING_STATES){
            int player1WinStateCount = 0;
            int player2WinStateCount = 0;
            for(char state : allStates.toCharArray()){
                if(player1States.contains(String.valueOf(state))) player1WinStateCount++;
                else if(player2States.contains(String.valueOf(state))) player2WinStateCount++;

                if(player1WinStateCount == 3){
                    FragmentTransaction fragmentTransaction = this.getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutMain, new ScoreFragment(Players.PLAYER1));
                    fragmentTransaction.commit();
                } else if (player2WinStateCount == 3) {
                    FragmentTransaction fragmentTransaction = this.getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutMain, new ScoreFragment(Players.PLAYER2));
                    fragmentTransaction.commit();
                }

            }
        }



    }

    private String sortString(String inputString)
    {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private void changeBackgroundOfClickedButton(ImageButton button, Drawable drawable){
        button.setImageDrawable(drawable);
    }
}