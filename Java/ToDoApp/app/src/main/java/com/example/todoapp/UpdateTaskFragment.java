package com.example.todoapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.todoapp.Models.Assignment;
import com.example.todoapp.Services.FirebaseService;
import com.example.todoapp.Utility.BackpressedListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import io.github.muddz.styleabletoast.StyleableToast;

public class UpdateTaskFragment extends FragmentWithCustomSpinner implements BackpressedListener{

    private final Assignment currentAssigment;
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextSetEndTime;
    private EditText editTextSetAlarm;
    private CustomSpinner spinnerLevelImportance;
    private RadioGroup radioGroupColors;
    private String selectedColorCode;
    public static BackpressedListener backpressedListener;

    public UpdateTaskFragment(Assignment currentAssigment, WeakReference<Context> context) {
        super(context);
        this.currentAssigment = currentAssigment;
        selectedColorCode = currentAssigment.getColorCode();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_task, container, false);
        radioGroupColors = view.findViewById(R.id.radioGroupColors);
        spinnerLevelImportance = view.findViewById(R.id.spinnerLevelImportance);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextSetAlarm = view.findViewById(R.id.editTextSetAlarm);
        editTextSetEndTime = view.findViewById(R.id.editTextSetEndTime);
        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(l -> {

            FirebaseService.updateTask(currentAssigment.getKey(),
                        new Assignment(
                                currentAssigment.getKey(),
                                editTextName.getText().toString(),
                                editTextSetAlarm.getText().toString(),
                                editTextDescription.getText().toString(),
                                spinnerLevelImportance.getSelectedItem().toString(),
                                selectedColorCode,
                                editTextSetEndTime.getText().toString(),
                                currentAssigment.getFinished()
                        )
                );

                new StyleableToast
                    .Builder(context.get())
                    .text(getString(R.string.successfully_updated_task))
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.GREEN)
                    .show();

                FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
                fragmentTransaction.commit();
        });

        editTextSetAlarm.setInputType(InputType.TYPE_NULL);
        editTextSetAlarm.setOnClickListener(l -> {
            showTimeDialog(editTextSetAlarm, currentAssigment.getStartTime());
        });

        editTextSetEndTime.setInputType(InputType.TYPE_NULL);
        editTextSetEndTime.setOnClickListener(l -> {
            showTimeDialog(editTextSetEndTime, currentAssigment.getEndTime());
        });

        editTextName.setText(currentAssigment.getName());
        editTextDescription.setText(currentAssigment.getDescription());
        editTextSetAlarm.setText(currentAssigment.getStartTime());
        editTextSetEndTime.setText(currentAssigment.getEndTime());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context.get(), R.array.level_importances_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevelImportance.setAdapter(adapter);
        spinnerLevelImportance.setSpinnerEventsListener(this);
        spinnerLevelImportance.setSelection(Integer.parseInt(currentAssigment.getLevelImportance().replace("Level ", "")) - 1);

        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(context.get(), R.color.dark_green));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_blue));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_green));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_orange));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_red));
        colors.add(ContextCompat.getColor(context.get(), R.color.light_yellow));

        AtomicInteger index = new AtomicInteger();

        colors.forEach(color -> {
            RadioButton radioButton = new RadioButton(context.get());
            radioButton.setId(View.generateViewId());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(20, LinearLayout.LayoutParams.MATCH_PARENT, 1));
            radioButton.setBackground(ContextCompat.getDrawable(context.get(), R.drawable.custom_radiobutton));
            radioButton.setButtonDrawable(ContextCompat.getDrawable(context.get(), android.R.color.transparent));
            radioButton.setBackgroundTintList(ColorStateList.valueOf(color));
            radioButton.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);

            if (color.toString().equals(currentAssigment.getColorCode())) {
                radioButton.setChecked(true);
            }

            radioButton.setOnClickListener(l -> {
                selectedColorCode = String.valueOf(color);
            });

            if (index.get() == 0) {
                layoutParams.rightMargin = 20;
            } else if (index.get() == colors.size() - 1) {
                layoutParams.leftMargin = 20;
            } else {
                layoutParams.leftMargin = 20;
                layoutParams.rightMargin = 20;
            }

            radioButton.setLayoutParams(layoutParams);
            radioGroupColors.addView(radioButton);

            index.getAndIncrement();
        });

        return view;
    }

    private void showTimeDialog(EditText editText, String time) {

        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            editText.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(context.get(), /*style,*/ onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }


}