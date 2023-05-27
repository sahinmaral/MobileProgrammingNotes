package com.example.todoapp.Services;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkService {
    public static boolean isNetworkConnected(Context usedContext) {
        ConnectivityManager cm = (ConnectivityManager) usedContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
