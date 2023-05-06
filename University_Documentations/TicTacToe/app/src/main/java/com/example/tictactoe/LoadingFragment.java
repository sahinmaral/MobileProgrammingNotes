package com.example.tictactoe;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tictactoe.Services.FirebaseService;
import com.example.tictactoe.Services.NetworkService;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.ref.WeakReference;

public class LoadingFragment extends Fragment {

    Long player1Score = 0L;
    Long player2Score= 0L;
    private final Players wonPlayer;
    ImageView imageViewLoading;
    private int getImage() {
        return this.getResources().getIdentifier("loading", "drawable", getContext().getPackageName());
    }

    public LoadingFragment(Players wonPlayer){
        this.wonPlayer = wonPlayer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        imageViewLoading = view.findViewById(R.id.imageViewLoading);
        Glide.with(this).load(getImage()).into(imageViewLoading);

        if(!NetworkService.isNetworkConnected(getContext())){
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutScoreboard, new FirebaseErrorFragment("Scoreboard hasn't loaded due to the loss internet connection"));
            fragmentTransaction.commit();
        }else{

        }

        FirebaseService.getScores().addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                for (QueryDocumentSnapshot document : task.getResult()) {
                    player1Score = (Long) document.get("player1Score");
                    player2Score = (Long) document.get("player2Score");

                    if(wonPlayer == Players.PLAYER1){
                        player1Score++;
                    }else if(wonPlayer == Players.PLAYER2){
                        player2Score++;
                    }

                    FirebaseService.setScores(player1Score,player2Score).addOnCompleteListener(task2 -> {
                        if(task2.isSuccessful()){
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutScoreboard, new ScoreboardTableFragment(wonPlayer,player1Score,player2Score));
                            fragmentTransaction.commit();
                        }else{
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutScoreboard, new FirebaseErrorFragment("Score hasn't loaded due to the Firebase error"));
                            fragmentTransaction.commit();
                        }
                    });
                }

            }else{
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutScoreboard, new FirebaseErrorFragment("Score hasn't loaded due to the Firebase error"));
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}