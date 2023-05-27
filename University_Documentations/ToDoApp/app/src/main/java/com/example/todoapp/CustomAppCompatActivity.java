package com.example.todoapp;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.Utility.BackpressedEvents;
import com.example.todoapp.Utility.BackpressedListener;
import com.example.todoapp.Utility.NetworkChangeListener;

public class CustomAppCompatActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener;
    public static BackpressedListener backpressedListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkChangeListener = new NetworkChangeListener();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (networkChangeListener.dialog != null && networkChangeListener.dialog.isShowing()) {
            networkChangeListener.dialog.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        BackpressedEvents.loadEvents();

    }
}
