package com.example.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.todoapp.Services.DownloadImageTask;
import com.example.todoapp.Services.FirebaseService;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.WeakReference;

import io.github.muddz.styleabletoast.StyleableToast;


public class UpdateUserProfileFragment extends Fragment {

    private ActivityResultLauncher<Intent> selectImageFromGalleryActivityResult;
    private final WeakReference<Context> context;

    public UpdateUserProfileFragment(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectImageFromGalleryActivityResult = loadSelectImageFromGalleryIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user_profile, container, false);


        HorizontalDividerFragment newFragment = new HorizontalDividerFragment(null, false);
        FragmentTransaction transaction = ((FragmentActivity) context.get()).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayoutHorizontalDividerLogin, newFragment).commit();

        EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        if (FirebaseService.getCurrentUser().getDisplayName() != null) {
            editTextUsername.setText(FirebaseService.getCurrentUser().getDisplayName());
        }

        Button buttonUpdateUsername = view.findViewById(R.id.buttonUpdateUsername);
        Button buttonClearProfilePhoto = view.findViewById(R.id.buttonClearProfilePhoto);
        Button buttonUpdatePhoto = view.findViewById(R.id.buttonUpdatePhoto);

        buttonUpdatePhoto.setOnClickListener(l -> chooseImageFromGallery());

        buttonClearProfilePhoto.setOnClickListener(l -> {
            FirebaseService.deleteProfilePhotoFromStorage().addOnCompleteListener(deletePhotoTask -> {
                if(deletePhotoTask.isSuccessful()){
                    FirebaseService.clearProfilePhotoOfUser().addOnCompleteListener(authDeleteTask -> {
                        if(authDeleteTask.isSuccessful()){
                            ImageView imageViewUserThumbnail = ((Activity) context.get()).findViewById(R.id.imageViewUserThumbnail);
                            imageViewUserThumbnail.setImageDrawable(ContextCompat.getDrawable(context.get(),R.drawable.insert_photo));

                            new StyleableToast
                                    .Builder(context.get())
                                    .text(getString(R.string.successfully_cleared_profile_photo))
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.GREEN)
                                    .show();
                        }
                        else{
                            new StyleableToast
                                    .Builder(context.get())
                                    .text(getString(R.string.failed_to_delete_profile_photo))
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.GREEN)
                                    .show();
                        }
                    });
                }else{
                    new StyleableToast
                            .Builder(context.get())
                            .text(getString(R.string.failed_to_delete_profile_photo))
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GREEN)
                            .show();
                }
            });
        });



        buttonUpdateUsername.setOnClickListener(l -> {
            FirebaseService.updateUsernameOfUser(editTextUsername.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    new StyleableToast
                            .Builder(context.get())
                            .text(getString(R.string.successfully_updated_username))
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GREEN)
                            .show();

                    TextView textViewUsername = ((Activity) context.get()).findViewById(R.id.textViewUsername);
                    textViewUsername.setText(editTextUsername.getText().toString());
                }
            });
        });

        return view;
    }


    private void chooseImageFromGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        selectImageFromGalleryActivityResult.launch(intent);
    }

    private ActivityResultLauncher<Intent> loadSelectImageFromGalleryIntent() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intentForData = result.getData();
                        Uri uri = intentForData.getData();
                        if (uri != null) {
                            UploadTask mainTask = FirebaseService.uploadImageFromStorage(uri, FirebaseService.getCurrentUser().getUid());
                            mainTask.addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseService.getUploadedProfilePhotoOfUser().addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            Uri profilePhoto = task2.getResult();
                                            FirebaseService.updateProfilePhotoOfUser(profilePhoto).addOnCompleteListener(task3 -> {
                                                if (task3.isSuccessful()) {
                                                    ImageView imageViewUserThumbnail = ((Activity) context.get()).findViewById(R.id.imageViewUserThumbnail);
                                                    new DownloadImageTask(imageViewUserThumbnail)
                                                            .execute(profilePhoto.toString());
                                                    new StyleableToast
                                                            .Builder(context.get())
                                                            .text(getString(R.string.successfully_uploaded_profile_photo))
                                                            .textColor(Color.WHITE)
                                                            .backgroundColor(Color.GREEN)
                                                            .show();
                                                } else {
                                                    new StyleableToast
                                                            .Builder(context.get())
                                                            .text(getString(R.string.failed_to_upload_profile_photo))
                                                            .textColor(Color.WHITE)
                                                            .backgroundColor(Color.GREEN)
                                                            .show();
                                                }
                                            });
                                        }
                                    });


                                } else {
                                    new StyleableToast
                                            .Builder(context.get())
                                            .text(getString(R.string.failed_to_upload_profile_photo))
                                            .textColor(Color.WHITE)
                                            .backgroundColor(Color.GREEN)
                                            .show();
                                }
                            });

                        }

                    }
                });
    }
}