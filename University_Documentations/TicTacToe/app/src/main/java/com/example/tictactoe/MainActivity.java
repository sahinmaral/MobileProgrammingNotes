package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.tictactoe.Services.NetworkService;
public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to close the game");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            finishAffinity();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.cancel();
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!NetworkService.isNetworkConnected(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Warning");
            builder.setMessage("You must connect the internet to play this game. Please check your internet connection");
            builder.setNegativeButton("OK", (dialogInterface, i) -> {
                finishAffinity();
            });
            builder.show();
        }else{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new StartingFragment());
            fragmentTransaction.commit();
        }

    }

}