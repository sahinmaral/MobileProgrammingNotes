package com.example.tictactoe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tictactoe.Services.FirebaseService;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;


public class ScoreboardTableFragment extends Fragment {

    TextView textViewPlayer1Score;
    TextView textViewPlayer2Score;
    final Players wonPlayer;
    final Long player1Score;
    final Long player2Score;
    public ScoreboardTableFragment(Players wonPlayer,Long player1Score,Long player2Score) {
        this.wonPlayer = wonPlayer;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard_table, container, false);

        textViewPlayer1Score = view.findViewById(R.id.textViewPlayer1Score);
        textViewPlayer2Score = view.findViewById(R.id.textViewPlayer2Score);

        textViewPlayer1Score.setText(Long.toString(player1Score));
        textViewPlayer2Score.setText(Long.toString(player2Score));

        return view;
    }
}