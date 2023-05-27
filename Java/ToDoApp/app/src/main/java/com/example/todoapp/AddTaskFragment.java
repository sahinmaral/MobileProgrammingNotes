package com.example.todoapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.todoapp.Models.Assignment;
import com.example.todoapp.Services.FirebaseService;
import com.example.todoapp.Utility.BackpressedListener;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class AddTaskFragment extends FragmentWithCustomSpinner {

    private EditText editTextSetEndTime;
    private EditText editTextSetAlarm;
    private RadioGroup radioGroupColors;
    private CustomSpinner spinnerLevelImportance;
    private int hour, minute;
    private EditText editTextName;
    private EditText editTextDescription;
    private String selectedColorCode;
    public AddTaskFragment(WeakReference<Context> context) {
        super(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        radioGroupColors = view.findViewById(R.id.radioGroupColors);
        spinnerLevelImportance = view.findViewById(R.id.spinnerLevelImportance);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextSetAlarm = view.findViewById(R.id.editTextSetAlarm);
        editTextSetEndTime = view.findViewById(R.id.editTextSetEndTime);
        Button buttonAdd = view.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(l -> {
            FirebaseService.addTask(new Assignment(
                    UUID.randomUUID().toString(),
                    editTextName.getText().toString(),
                    editTextSetAlarm.getText().toString(),
                    editTextDescription.getText().toString(),
                    spinnerLevelImportance.getSelectedItem().toString(),
                    selectedColorCode,
                    editTextSetEndTime.getText().toString(),
                    false
            ));

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
            fragmentTransaction.commit();
        });

        editTextSetAlarm.setInputType(InputType.TYPE_NULL);
        editTextSetAlarm.setOnClickListener(l -> {
            showTimeDialog(editTextSetAlarm);
        });

        editTextSetEndTime.setInputType(InputType.TYPE_NULL);
        editTextSetEndTime.setOnClickListener(l -> {
            showTimeDialog(editTextSetEndTime);
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context.get(),R.array.level_importances_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevelImportance.setAdapter(adapter);

        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(context.get(), R.color.dark_green));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_blue));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_green));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_orange));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_red));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_yellow));

        for(int color : colors){
            Log.d("color", String.valueOf(color));
        }

        AtomicInteger index = new AtomicInteger();

        colors.forEach(color -> {
            RadioButton radioButton = new RadioButton(context.get());
            radioButton.setId(View.generateViewId());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(20,LinearLayout.LayoutParams.MATCH_PARENT,1));
            radioButton.setBackground(ContextCompat.getDrawable(context.get(), R.drawable.custom_radiobutton));
            radioButton.setButtonDrawable(ContextCompat.getDrawable(context.get(), android.R.color.transparent));
            radioButton.setBackgroundTintList(ColorStateList.valueOf(color));
            radioButton.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);

            radioButton.setOnClickListener(l -> {
                selectedColorCode = String.valueOf(color);
            });

            if(index.get() == 0){
                radioButton.setChecked(true);
                selectedColorCode = String.valueOf(color);
                layoutParams.rightMargin = 20;
            }else if(index.get() == colors.size() - 1){
                layoutParams.leftMargin = 20;
            }else{
                layoutParams.leftMargin = 20;
                layoutParams.rightMargin = 20;
            }

            radioButton.setLayoutParams(layoutParams);
            radioGroupColors.addView(radioButton);

            index.getAndIncrement();
        });


        return view;
    }

    private void showTimeDialog(EditText editText){

        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            editText.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(context.get(), onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

}