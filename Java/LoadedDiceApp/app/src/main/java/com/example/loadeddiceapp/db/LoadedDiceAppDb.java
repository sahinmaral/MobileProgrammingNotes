package com.example.loadeddiceapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadedDiceAppDb {
    SQLiteDatabase database;
    Cursor cursor;
    public LoadedDiceAppDb(Context context){
        database = context.openOrCreateDatabase("loadedDiceAppDb", Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS dices (ID INTEGER PRIMARY KEY NOT NULL,face INTEGER NOT NULL,rolledCount INTEGER NOT NULL)");
        setAllFacesOfDiceToDatabase();
    }

    private void setAllFacesOfDiceToDatabase(){
        String[] faces = {"1","2","3","4","5","6"};

        for(String face : faces){
            String sqlQuery = String.format("SELECT COUNT(*) AS count FROM dices WHERE face='%s'",face);
            cursor = database.rawQuery(sqlQuery,null);
            int existedDiceCountIndex = cursor.getColumnIndex("count");
            while (cursor.moveToNext()){
                if(cursor.getInt(existedDiceCountIndex) == 0){
                    database.execSQL(String.format("INSERT INTO dices (face,rolledCount) VALUES ('%s',0)",face));
                }
            }
        }
    }

    public void closeDatabase(){
        database.close();
    }

    public void insertDices(int firstDice,int secondDice){
        int rolledCount = 0;

        String sqlQueryFirstDice = String.format("SELECT rolledCount FROM dices WHERE face = '%s'",firstDice);
        cursor = database.rawQuery(sqlQueryFirstDice,null);
        int rolledCountIndexFirstDice = cursor.getColumnIndex("rolledCount");

        while (cursor.moveToNext()){
            rolledCount = cursor.getInt(rolledCountIndexFirstDice);
        }

        sqlQueryFirstDice = String.format("UPDATE dices SET rolledCount = %s WHERE face = %s",rolledCount+1,firstDice);
        database.execSQL(sqlQueryFirstDice);

        String sqlQuerySecondDice = String.format("SELECT rolledCount FROM dices WHERE face = '%s'",secondDice);
        cursor = database.rawQuery(sqlQuerySecondDice,null);
        int rolledCountIndexSecondDice = cursor.getColumnIndex("rolledCount");

        while (cursor.moveToNext()){
            rolledCount = cursor.getInt(rolledCountIndexSecondDice);
        }

        sqlQuerySecondDice = String.format("UPDATE dices SET rolledCount = %s WHERE face = %s",rolledCount+1,secondDice);
        database.execSQL(sqlQuerySecondDice);
    }

    public HashMap<Integer,String[]> getCountOfFacesOfDice(){
        int[] facesOfDice = {1,2,3,4,5,6};
        HashMap<Integer,String[]> facesHashMap = new HashMap<>();
        int allFacesSum = 0;

        cursor = database.rawQuery("SELECT SUM(face) as sum FROM dices",null);
        int allFacesSumIndex = cursor.getColumnIndex("sum");

        while (cursor.moveToNext()){
            allFacesSum = cursor.getInt(allFacesSumIndex);
        }

        for(int face : facesOfDice){
            cursor = database.rawQuery(String.format("SELECT rolledCount FROM dices WHERE face = '%s'",face),null);
            int rolledCountIndex = cursor.getColumnIndex("rolledCount");
            while (cursor.moveToNext()){
                double possibility = (new Double(cursor.getInt(rolledCountIndex)) / new Double(allFacesSum)) * 100;
                System.out.println(possibility);
                String[] faceHashMapValue = {String.format("Count of %s = %s",face,cursor.getInt(rolledCountIndex)),String.format("Possibility of %s = %s",face,possibility)};
                facesHashMap.put(face,faceHashMapValue);
            }
        }

        return facesHashMap;
    }
}
