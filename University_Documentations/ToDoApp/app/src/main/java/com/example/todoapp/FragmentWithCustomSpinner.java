package com.example.todoapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

public class FragmentWithCustomSpinner extends CustomFragment implements CustomSpinner.OnSpinnerEventsListener {

    public FragmentWithCustomSpinner(WeakReference<Context> context){
        super(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        findSpinners(view);
        return view;
    }

    private void findSpinners(View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    // recursively call this method
                    findSpinners(child);
                }
            } else if (v instanceof CustomSpinner) {
                CustomSpinner spinner = (CustomSpinner) v;
                spinner.setSpinnerEventsListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spinner.setBackground(ContextCompat.getDrawable(context.get(), R.drawable.rounded_spinner_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spinner.setBackground(ContextCompat.getDrawable(context.get(), R.drawable.rounded_spinner));
    }

}
