package com.example.todoapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todoapp.Models.Assignment;
import com.example.todoapp.Services.FirebaseService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {
    private final ArrayList<Assignment> assignments;
    private WeakReference<Context> context;
    public TaskRecyclerAdapter(ArrayList<Assignment> assignments,WeakReference<Context> context) {
        this.assignments = assignments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycler_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Assignment currentTask = assignments.get(position);
        holder.textViewTaskName.setText(currentTask.getName());
        holder.textViewTaskLevelImportance.setText(currentTask.getLevelImportance());
        holder.textViewTaskStartTime.setText(currentTask.getStartTime());
        holder.checkBoxTaskCompleted.setChecked(currentTask.getFinished());

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
        gradientDrawable.setColor(Integer.parseInt(currentTask.getColorCode()));
        holder.linearLayoutTaskBackground.setBackground(gradientDrawable);

        holder.textViewOptions.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context.get(), holder.textViewOptions);
            popup.inflate(R.menu.options_menu);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_updateTask:
                        showDetailedTask(currentTask);
                        break;
                    case R.id.menu_deleteTask:
                        deleteTask(currentTask);
                        break;
                }
                return false;
            });
            popup.show();
        });

        holder.checkBoxTaskCompleted.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(currentTask.getColorCode())));
        holder.checkBoxTaskCompleted.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);

        holder.checkBoxTaskCompleted.setOnClickListener(l -> {
            currentTask.setFinished(holder.checkBoxTaskCompleted.isChecked());
            FirebaseService.updateTask(currentTask.getKey(),currentTask);

            FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
            fragmentTransaction.commit();
        });
    }

    private void deleteTask(Assignment currentTask) {
        FirebaseService.deleteTask(currentTask).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                new StyleableToast
                        .Builder(context.get())
                        .text("Successfully deleted task")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.GREEN)
                        .show();

                FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(context.get()));
                fragmentTransaction.commit();
            }
        });


    }

    private void showDetailedTask(Assignment currentTask) {
        FragmentManager fragmentManager = ((FragmentActivity) context.get()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, new UpdateTaskFragment(currentTask, context));
        fragmentTransaction.commit();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTaskName;
        private final TextView textViewTaskLevelImportance;
        private final TextView textViewTaskStartTime;
        private final LinearLayout linearLayoutTaskBackground;
        private final CheckBox checkBoxTaskCompleted;

        private final TextView textViewOptions;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            textViewTaskLevelImportance = itemView.findViewById(R.id.textViewTaskLevelImportance);
            textViewTaskStartTime = itemView.findViewById(R.id.textViewTaskStartTime);
            checkBoxTaskCompleted = itemView.findViewById(R.id.checkBoxTaskCompleted);
            linearLayoutTaskBackground = itemView.findViewById(R.id.linearLayoutTaskBackground);
            textViewOptions = itemView.findViewById(R.id.textViewOptions);
        }
    }
}

