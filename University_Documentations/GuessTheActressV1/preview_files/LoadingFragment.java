package com.example.guesstheactressv1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoadingFragment extends Fragment {

    Context mainContext;
    ImageView imageViewLoading;

    LoadingFragment(Context mainContext){
        this.mainContext = mainContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", mainContext.getPackageName());

        return drawableResourceId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_loading, container, false);

        imageViewLoading = myView.findViewById(R.id.imageViewLoading);

        Glide.with(this).load(getImage("loading")).into(imageViewLoading);

        return myView;
    }
}