package com.example.todoapp.Utility;

import com.example.todoapp.AddTaskFragment;
import com.example.todoapp.FilterFragment;
import com.example.todoapp.HomeFragment;
import com.example.todoapp.UpdateTaskFragment;

public class BackpressedEvents {
    public static void loadEvents(){

        if (UpdateTaskFragment.backpressedListener != null) {
            UpdateTaskFragment.backpressedListener.onBackPressed();
        }

        if (HomeFragment.backpressedListener != null) {
            HomeFragment.backpressedListener.onBackPressed();
        }

        if (AddTaskFragment.backpressedListener != null) {
            AddTaskFragment.backpressedListener.onBackPressed();
        }

        if (FilterFragment.backpressedListener != null) {
            FilterFragment.backpressedListener.onBackPressed();
        }


    }
}
