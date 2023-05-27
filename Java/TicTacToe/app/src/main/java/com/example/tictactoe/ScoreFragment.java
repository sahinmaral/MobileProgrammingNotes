package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.Services.FirebaseService;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ScoreFragment extends Fragment {

    Button buttonStartGame;
    TextView textViewWonPlayer;
    Players wonPlayer;
    public ScoreFragment(Players wonPlayer) {
       this.wonPlayer = wonPlayer;
    }

    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        buttonStartGame = view.findViewById(R.id.buttonStartGame);
        textViewWonPlayer = view.findViewById(R.id.textViewWonPlayer);

        textViewWonPlayer.setText(wonPlayer + " WON");
        buttonStartGame.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Warning");
            builder.setMessage("Do you want to start to play game again or close the game");
            builder.setPositiveButton("Play", (dialogInterface, i) -> {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain, new StartingFragment());
                fragmentTransaction.commit();
            });
            builder.setNegativeButton("Close", (dialogInterface, i) -> {
                getActivity().finishAffinity();
            });
            builder.show();
        });

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutScoreboard, new LoadingFragment(wonPlayer));
        fragmentTransaction.commit();

        return view;
    }
}