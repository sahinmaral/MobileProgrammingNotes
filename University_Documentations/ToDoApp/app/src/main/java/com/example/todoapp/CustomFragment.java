package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todoapp.Utility.BackpressedListener;

import java.lang.ref.WeakReference;

public class CustomFragment extends Fragment implements BackpressedListener {
    public static BackpressedListener backpressedListener;
    public WeakReference<Context> context;

    public CustomFragment(WeakReference<Context> context) {
        this.context = context;
    }

    @Override
    public void onPause() {
        backpressedListener = null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        backpressedListener=this;
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
        Fragment fragmentInFrame = fragmentManager.findFragmentById(R.id.frameLayoutMain);

        if (fragmentInFrame instanceof UpdateTaskFragment ||
                fragmentInFrame instanceof UpdateUserProfileFragment ||
                fragmentInFrame instanceof AddTaskFragment ||
                fragmentInFrame instanceof FilterFragment
        ) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
            fragmentTransaction.commit();
        } else if (fragmentInFrame instanceof HomeFragment) {
            ((FragmentActivity) context.get()).finishAffinity();
        }
    }
}
