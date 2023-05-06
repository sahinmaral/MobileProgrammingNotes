package com.example.tictactoe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FirebaseErrorFragment extends Fragment {
    private final String errorMessage;
    TextView textViewErrorMessage;

    public FirebaseErrorFragment(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firebase_error, container, false);

        textViewErrorMessage = view.findViewById(R.id.textViewErrorMessage);
        textViewErrorMessage.setText(errorMessage);
        return view;
    }
}