package com.example.todoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HorizontalDividerFragment extends Fragment {

    Boolean showMiddleText;
    String middleText;
    TextView textViewHorizontalDividerMiddleText;

    public HorizontalDividerFragment(String middleText,Boolean showMiddleText) {
        this.middleText = middleText;
        this.showMiddleText = showMiddleText;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(showMiddleText){
            view = inflater.inflate(R.layout.horizontal_divider_text, container, false);
            textViewHorizontalDividerMiddleText = view.findViewById(R.id.textViewHorizontalDividerMiddleText);
            textViewHorizontalDividerMiddleText.setVisibility(View.VISIBLE);
            textViewHorizontalDividerMiddleText.setText(middleText);
        }else{
            view = inflater.inflate(R.layout.horizontal_divider, container, false);
        }


        return view;
    }
}