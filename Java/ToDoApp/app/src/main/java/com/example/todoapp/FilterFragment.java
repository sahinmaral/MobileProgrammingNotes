package com.example.todoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.util.Arrays;


public class FilterFragment extends FragmentWithCustomSpinner {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    Spinner spinnerByStatus;
    Spinner spinnerByLevelImportance;

    public FilterFragment(WeakReference<Context> context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        spinnerByLevelImportance = view.findViewById(R.id.spinnerByLevelImportance);
        ArrayAdapter<CharSequence> adapterForSpinnerByLevelImportance = ArrayAdapter.createFromResource(context.get(), R.array.level_importances_filter_array, android.R.layout.simple_spinner_item);
        adapterForSpinnerByLevelImportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerByLevelImportance.setAdapter(adapterForSpinnerByLevelImportance);

        spinnerByStatus = view.findViewById(R.id.spinnerByStatus);
        ArrayAdapter<CharSequence> adapterForSpinnerByStatus = ArrayAdapter.createFromResource(context.get(), R.array.task_status_filter_array, android.R.layout.simple_spinner_item);
        adapterForSpinnerByStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerByStatus.setAdapter(adapterForSpinnerByStatus);

        String[] level_importances_filter_array = getResources().getStringArray(R.array.level_importances_filter_array);
        int selectedLevelImportanceIndex = Arrays.asList(level_importances_filter_array).indexOf(sharedPreferences.getString("filterTask_byLevelImportance", "Any"));
        spinnerByLevelImportance.setSelection(selectedLevelImportanceIndex);

        String[] task_status_filter_array = getResources().getStringArray(R.array.task_status_filter_array);
        int selectedTaskStatusIndex = Arrays.asList(task_status_filter_array).indexOf(sharedPreferences.getString("filterTask_byStatus", "Any"));
        spinnerByStatus.setSelection(selectedTaskStatusIndex);

        Button buttonFilterTasks = view.findViewById(R.id.buttonFilterTasks);
        buttonFilterTasks.setOnClickListener(l -> {
            filterTasks();
            FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
            fragmentTransaction.commit();
        });


        return view;
    }

    private void filterTasks() {

        String byStatus = spinnerByStatus.getSelectedItem().toString();
        String byLevelImportance = spinnerByLevelImportance.getSelectedItem().toString();

        if(!byStatus.equals("Any")){
            sharedPreferencesEditor.putString("filterTask_byStatus", byStatus);
        }
        if(!byLevelImportance.equals("Any")){
            sharedPreferencesEditor.putString("filterTask_byLevelImportance", byLevelImportance);
        }

        sharedPreferencesEditor.apply();
    }


}