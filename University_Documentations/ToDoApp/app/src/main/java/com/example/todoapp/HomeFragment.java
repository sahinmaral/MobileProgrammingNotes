package com.example.todoapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.todoapp.Models.Assignment;
import com.example.todoapp.Services.FirebaseService;
import com.google.firebase.database.DataSnapshot;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class HomeFragment extends CustomFragment {

    RecyclerView recyclerViewAllTasks;
    ArrayList<Assignment> assignments = new ArrayList<>();

    public HomeFragment(Context context) {
        super(new WeakReference<>(context));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton imgBtnShowFilterDialog = view.findViewById(R.id.imgBtnShowFilterDialog);
        imgBtnShowFilterDialog.setOnClickListener(l -> {
            FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new FilterFragment(context));
            fragmentTransaction.commit();
        });

        ImageButton imgBtnClearFilter = view.findViewById(R.id.imgBtnClearFilter);
        imgBtnClearFilter.setOnClickListener(l -> {
            clearFilter();

            FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
            fragmentTransaction.commit();
        });

        ImageButton imgBtnShowAddTaskFragment = view.findViewById(R.id.imgBtnShowAddTaskFragment);
        imgBtnShowAddTaskFragment.setOnClickListener(l -> {
            FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new AddTaskFragment(context));
            fragmentTransaction.commit();
        });

        recyclerViewAllTasks = view.findViewById(R.id.recyclerViewAllTasks);
        loadRecyclerViewData(view);

        return view;
    }

    private void loadRecyclerViewData(View view) {
        FirebaseService.getTasks().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    String currentElementLevelImportance = keyNode.child("levelImportance").getValue(String.class);
                    Boolean currentElementIsFinished = keyNode.child("isFinished").getValue(Boolean.class);

                    if (isTaskIncludesFilter(currentElementLevelImportance, currentElementIsFinished)) {

                        Assignment assignment = new Assignment(
                                keyNode.getKey(),
                                keyNode.child("name").getValue(String.class),
                                keyNode.child("startTime").getValue(String.class),
                                keyNode.child("description").getValue(String.class),
                                currentElementLevelImportance,
                                keyNode.child("colorCode").getValue(String.class),
                                keyNode.child("endTime").getValue(String.class),
                                currentElementIsFinished
                        );

                        assignment.setKey(keyNode.getKey());
                        assignments.add(assignment);
                    }

                }

                TaskRecyclerAdapter itemAdapter = new TaskRecyclerAdapter(assignments, context);
                RecyclerView recyclerViewAllTasks
                        = view.findViewById(R.id.recyclerViewAllTasks);
                recyclerViewAllTasks.setAdapter(itemAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.get());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewAllTasks.setLayoutManager(linearLayoutManager);
            } else {
                new StyleableToast
                        .Builder(context.get())
                        .text(getString(R.string.failed_to_read_tasks))
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            }
        });
    }

    private boolean isTaskIncludesFilter(String levelImportance, Boolean isFinished) {
        SharedPreferences sharedPref = context.get().getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        String selectedStatusAsString = sharedPref.getString("filterTask_byStatus", "");
        String selectedLevelImportance = sharedPref.getString("filterTask_byLevelImportance", "");

        boolean selectedIsFinished = selectedStatusAsString.equals("Completed");

        boolean filterStatus =
                selectedLevelImportance.equals(levelImportance) && selectedIsFinished == isFinished ||
                        selectedLevelImportance.equals("") && selectedIsFinished == isFinished ||
                        selectedLevelImportance.equals(levelImportance) && selectedStatusAsString.equals("");

        return
                filterStatus ||
                        (selectedStatusAsString.equals("") && selectedLevelImportance.equals(""));
    }

    private void clearFilter() {
        SharedPreferences sharedPref = context.get().getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("filterTask_byStatus");
        editor.remove("filterTask_byLevelImportance");
        editor.apply();
    }

}