package com.example.todoapp.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.todoapp.R;

public class NetworkChangeListener extends BroadcastReceiver {

    public AlertDialog.Builder builder;
    public AlertDialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet(context)) {
            //TODO FIX: When harmful user decides to push retry and any button at page , it will be trigger after many tries
            builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);

            Button btnRetryCheckConnection = layout_dialog.findViewById(R.id.btnRetryCheckConnection);

            dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnRetryCheckConnection.setOnClickListener(l -> {
                dialog.dismiss();
                onReceive(context, intent);
            });
        }
    }
}
