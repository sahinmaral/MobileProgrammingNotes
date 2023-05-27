package com.example.todoapp.Services;


import android.net.Uri;

import com.example.todoapp.Models.Assignment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseService {

    public static Task<DataSnapshot> getTasks(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tasks");
        return reference.child(getCurrentUser().getUid()).get();
    }
    public static Task<AuthResult> signInWithEmail(String email, String password){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.signInWithEmailAndPassword(email, password);
    }
    public static Task<AuthResult> registerWithEmail(String email,String password){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.createUserWithEmailAndPassword(email, password);
    }
    public static FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public static void signOutUser(){
        FirebaseAuth.getInstance().signOut();
    }
    public static void addTask(Assignment assignment){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tasks");
        DatabaseReference currentTaskReference = reference.child(getCurrentUser().getUid());


        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("name",assignment.getName());
        taskMap.put("description",assignment.getDescription());
        taskMap.put("levelImportance",assignment.getLevelImportance());
        taskMap.put("colorCode",assignment.getColorCode());
        taskMap.put("startTime",assignment.getStartTime());
        taskMap.put("endTime",assignment.getEndTime());
        taskMap.put("isFinished",assignment.getFinished());

        currentTaskReference.child(assignment.getKey()).updateChildren(taskMap);
    }
    public static void updateTask(String key,Assignment updatedTask){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tasks");
        DatabaseReference currentTaskReference = reference.child(getCurrentUser().getUid()).child(key);
        currentTaskReference.child("name").setValue(updatedTask.getName());
        currentTaskReference.child("description").setValue(updatedTask.getDescription());
        currentTaskReference.child("colorCode").setValue(updatedTask.getColorCode());
        currentTaskReference.child("endTime").setValue(updatedTask.getEndTime());
        currentTaskReference.child("startTime").setValue(updatedTask.getStartTime());
        currentTaskReference.child("description").setValue(updatedTask.getDescription());
        currentTaskReference.child("levelImportance").setValue(updatedTask.getLevelImportance());
        currentTaskReference.child("isFinished").setValue(updatedTask.getFinished());
    }

    public static Task<Void> clearProfilePhotoOfUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(null)
                .build();

        return user.updateProfile(profileUpdates);
    }

    public static Task<Void> deleteProfilePhotoFromStorage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference selectedImageRef = storageRef.child("images/"+getCurrentUser().getUid());
        return selectedImageRef.delete();
    }

    public static Task<Void> updateUsernameOfUser(String username) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        return user.updateProfile(profileUpdates);
    }

    public static UploadTask uploadImageFromStorage(Uri selectedImage,String id){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference selectedImageRef = storageRef.child("images/"+id);
        return selectedImageRef.putFile(selectedImage);
    }

    public static Task<Uri> getUploadedProfilePhotoOfUser(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference selectedImageRef = storageRef.child("images/"+getCurrentUser().getUid());
        return selectedImageRef.getDownloadUrl();
    }

    public static Task<Void> updateProfilePhotoOfUser(Uri uploadedProfilePhoto){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uploadedProfilePhoto)
                .build();

        return user.updateProfile(profileUpdates);
    }

    public static Task<Void> deleteTask(Assignment currentTask){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tasks");
        DatabaseReference currentTaskReference = reference.child(getCurrentUser().getUid()).child(currentTask.getKey());
        return currentTaskReference.removeValue();
    }
}
