package com.example.multiplytable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarNumber;
    ListView listViewMultiplyTable;
    TextView textViewChosenNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarNumber = findViewById(R.id.seekBarNumber);
        listViewMultiplyTable = findViewById(R.id.listViewMultiplyTable);
        textViewChosenNumber = findViewById(R.id.textViewChosenNumber);

        textViewChosenNumber.setText(String.format("Chosen number : %d",seekBarNumber.getProgress()));

        listViewInflate(seekBarNumber.getProgress());

        seekBarNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listViewInflate(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void listViewInflate(int progress) {
        textViewChosenNumber.setText(String.format("Chosen number : %d", progress));

        String[] multiplyTableRow = new String[13];
        for(int i=0; i<= 12; i++){
            int result = progress *(i+1);
            multiplyTableRow[i] = String.format("%d * %d = %d", progress,i+1,result);
        }

        ArrayAdapter<String> multiplyTableAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,multiplyTableRow);
        listViewMultiplyTable.setAdapter(multiplyTableAdapter);
    }

}