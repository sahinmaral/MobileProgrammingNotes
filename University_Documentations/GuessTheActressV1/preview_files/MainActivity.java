package com.example.guesstheactressv1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    CelebrityFetch fetch;

    @Override
    public void onBackPressed() {
        fetch.cancel(true);
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, new LoadingFragment(this));
        fragmentTransaction.commit();

        fetch = new CelebrityFetch(this);
        fetch.execute();
    }
}