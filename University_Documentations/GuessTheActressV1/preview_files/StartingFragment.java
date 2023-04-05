package com.example.guesstheactressv1;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class StartingFragment extends Fragment {

    Context mainContext;
    ImageView imageViewCelebrityImage;
    TextView textViewScore;
    Button buttonFirstAnswer;
    Button buttonSecondAnswer;
    Button buttonThirdAnswer;
    Button buttonForthAnswer;
    Button[] buttonGroup;
    ArrayList<Celebrity> selectedCelebrities = new ArrayList<>();
    ArrayList<Celebrity> celebrityList;
    int score = 0;
    StartingFragment(ArrayList<Celebrity> celebrityList,Context mainContext){
        this.celebrityList = celebrityList;
        this.mainContext = mainContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_starting, container, false);

        imageViewCelebrityImage = view.findViewById(R.id.imageViewCelebrityImage);
        buttonFirstAnswer = view.findViewById(R.id.buttonFirstAnswer);
        buttonSecondAnswer = view.findViewById(R.id.buttonSecondAnswer);
        buttonThirdAnswer = view.findViewById(R.id.buttonThirdAnswer);
        buttonForthAnswer = view.findViewById(R.id.buttonForthAnswer);
        textViewScore = view.findViewById(R.id.textViewScore);

        buttonGroup = new Button[]{buttonFirstAnswer,buttonSecondAnswer,buttonThirdAnswer,buttonForthAnswer};

        rollCelebrity();

        return view;
    }

    private void rollCelebrity(){
        if(selectedCelebrities.size() == celebrityList.size()){
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new StatusFragment(celebrityList.size(),score,mainContext));
            fragmentTransaction.commit();
        }else{
            Random random = new Random();
            int randomNumber = random.nextInt(celebrityList.size() - selectedCelebrities.size());

            Celebrity selectedCelebrity = celebrityList.stream().filter(x -> !selectedCelebrities.contains(x)).collect(Collectors.toList()).get(randomNumber);
            selectedCelebrities.add(selectedCelebrity);

            loadCelebrity(selectedCelebrity,celebrityList);
        }

    }

    private void checkAnswer(String clickedCelebrityName,String selectedCelebrityName){
        if (clickedCelebrityName.equals(selectedCelebrityName)){
            score++;
            textViewScore.setText(String.format("%d/%d",score,celebrityList.size()));
        }

        rollCelebrity();
    }

    private void loadCelebrity(Celebrity selectedCelebrity,ArrayList<Celebrity> celebrityList){
        imageViewCelebrityImage.setImageBitmap(selectedCelebrity.getImage());

        Celebrity[] gatheredCelebrities = new Celebrity[4];

        Random rand = new Random();
        int randomButtonIndex = rand.nextInt(4);
        buttonGroup[randomButtonIndex].setText(selectedCelebrity.getName());
        buttonGroup[randomButtonIndex].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(selectedCelebrity.getName(),selectedCelebrity.getName());
            }
        });

        List<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        indexes.add(1);
        indexes.add(2);
        indexes.add(3);
        indexes.remove(randomButtonIndex);

        int count = indexes.size();

        while(count != 0){

            int filledCelebrityIndex = rand.nextInt(49);
            Celebrity filledCelebrity = celebrityList.stream().filter(x->!x.getName().equals(selectedCelebrity.getName())).collect(Collectors.toList()).get(filledCelebrityIndex);

            while (Arrays.asList(gatheredCelebrities).contains(filledCelebrity)) {
                filledCelebrityIndex = rand.nextInt(49);
                filledCelebrity = celebrityList.get(filledCelebrityIndex);
            }

            Celebrity finalFilledCelebrity = filledCelebrity;
            buttonGroup[indexes.get(indexes.size() - count)].setText(filledCelebrity.getName());
            buttonGroup[indexes.get(indexes.size() - count)].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(finalFilledCelebrity.getName(),selectedCelebrity.getName());
                }
            });

            count--;
        }


    }
}