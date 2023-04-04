package com.example.braintimer.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BrainTimerAppDb {
    SQLiteDatabase database;
    Cursor cursor;
    Context context;
    public BrainTimerAppDb(Context context){
        this.context = context;
        database = this.context.openOrCreateDatabase("brainTimerAppDb", Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS scores (ID INTEGER PRIMARY KEY NOT NULL,username VARCHAR NOT NULL,correctAnsweredQuestions INTEGER NOT NULL,totalQuestions INTEGER NOT NULL)");
    }

    public void closeDatabase(){
        database.close();
    }

    public void loadOrderedScoreboard(TableLayout tableLayout){
        cursor = database.rawQuery("SELECT * FROM scores ORDER BY totalQuestions DESC,correctAnsweredQuestions DESC LIMIT 50",null);
        int usernameIndex = cursor.getColumnIndex("username");
        int totalQuestionsIndex = cursor.getColumnIndex("totalQuestions");
        int correctAnsweredQuestionsIndex = cursor.getColumnIndex("correctAnsweredQuestions");

        while (cursor.moveToNext()){
            TableRow row = new TableRow(context);

            TextView textViewUsername = new TextView(context);
            textViewUsername.setText(cursor.getString(usernameIndex));
            textViewUsername.setTextSize(15);

            TextView textViewCorrectAnsweredQuestionCount = new TextView(context);
            textViewCorrectAnsweredQuestionCount.setText(Integer.toString(cursor.getInt(correctAnsweredQuestionsIndex)));
            textViewCorrectAnsweredQuestionCount.setWidth(150);
            textViewCorrectAnsweredQuestionCount.setTextSize(15);

            TextView textViewTotalQuestionCount = new TextView(context);
            textViewTotalQuestionCount.setText(Integer.toString(cursor.getInt(totalQuestionsIndex)));
            textViewTotalQuestionCount.setTextSize(15);

            row.addView(textViewUsername);
            row.addView(textViewCorrectAnsweredQuestionCount);
            row.addView(textViewTotalQuestionCount);

            tableLayout.addView(row);
        }
    }
    public String[] getUsernames(){
        cursor = database.rawQuery("SELECT username FROM scores",null);
        int usernameIndex = cursor.getColumnIndex("username");
        int rowCount = cursor.getCount();

        String[] usernameDataSet = new String[rowCount];

        int count = 0;

        while (cursor.moveToNext()){
            usernameDataSet[count] = cursor.getString(usernameIndex);
            count++;
        }

        return usernameDataSet;
    }

    public int[] getTotalQuestionCounts(){
        cursor = database.rawQuery("SELECT totalQuestions FROM scores",null);
        int totalQuestionsIndex = cursor.getColumnIndex("totalQuestions");
        int rowCount = cursor.getCount();

        int[] totalQuestionCountsDataSet = new int[rowCount];

        int count = 0;

        while (cursor.moveToNext()){
            totalQuestionCountsDataSet[count] = cursor.getInt(totalQuestionsIndex);
            count++;
        }

        return totalQuestionCountsDataSet;
    }

    public int[] getCorrectAnsweredQuestionCounts(){
        cursor = database.rawQuery("SELECT correctAnsweredQuestions FROM scores",null);
        int totalQuestionsIndex = cursor.getColumnIndex("correctAnsweredQuestions");
        int rowCount = cursor.getCount();

        int[] correctAnsweredQuestionCountsDataSet = new int[rowCount];

        int count = 0;

        while (cursor.moveToNext()){
            correctAnsweredQuestionCountsDataSet[count] = cursor.getInt(totalQuestionsIndex);
            count++;
        }

        return correctAnsweredQuestionCountsDataSet;
    }



    public void insertScore(String username,int correctAnsweredQuestions,int totalQuestions){
        database.execSQL(String.format("INSERT INTO scores (username,correctAnsweredQuestions,totalQuestions) VALUES ('%s','%s','%s')",username,correctAnsweredQuestions,totalQuestions));
    }

}
