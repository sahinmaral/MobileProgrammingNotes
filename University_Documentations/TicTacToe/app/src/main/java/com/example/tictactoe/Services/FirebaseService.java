package com.example.tictactoe.Services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseService {
    public static Task<QuerySnapshot> getScores(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("scores");
        return collection.get();
    }

    public static Task<Void> setScores(Object player1Score,Object player2Score){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("scores");

        Map<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("player1Score", player1Score);
        scoreMap.put("player2Score", player2Score);
        return collection.document("IoSGNbkkol5LLkpdhiOT").set(scoreMap);
    }
}
