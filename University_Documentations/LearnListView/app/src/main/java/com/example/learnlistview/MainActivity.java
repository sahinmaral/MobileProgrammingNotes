package com.example.learnlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewCountries);

        String[] countries = getResources().getStringArray(R.array.countries);


        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,countries);
        listView.setAdapter(countriesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),countries[position],Toast.LENGTH_SHORT).show();
            }
        });
    }
}