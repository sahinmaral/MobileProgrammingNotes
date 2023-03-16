package com.example.loadeddiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loadeddiceapp.db.LoadedDiceAppDb;

import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsActivity extends AppCompatActivity {
    ArrayList<String> countFacesList;
    ArrayList<String> possibilityFacesList;
    LoadedDiceAppDb database;
    ListView listViewStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        database = new LoadedDiceAppDb(getApplicationContext());
        HashMap<Integer,String[]> facesHashMap = database.getCountOfFacesOfDice();

        listViewStatistics = findViewById(R.id.listViewStatistics);

        countFacesList = new ArrayList<>();
        possibilityFacesList = new ArrayList<>();

        for(String[] faceValue : facesHashMap.values()){
            countFacesList.add(faceValue[0]);
            possibilityFacesList.add(faceValue[1]);
        }

        ArrayAdapter<String> statisticsAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,
                countFacesList.toArray(new String[0]));
        listViewStatistics.setAdapter(statisticsAdapter);

        listViewStatistics.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),possibilityFacesList.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        database.closeDatabase();
        super.onStop();
    }
}