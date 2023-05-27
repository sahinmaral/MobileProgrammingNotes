package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.todoapp.Services.DownloadImageTask;
import com.example.todoapp.Services.FirebaseService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends CustomAppCompatActivity implements FirebaseAuth.AuthStateListener {
    DrawerLayout drawerLayout;
    NavigationView navViewMenu;
    TextView textViewUserEmail;
    FirebaseUser currentUser;

    LinearLayout navHomeMenuItem;
    LinearLayout navUpdateUserProfileMenuItem;
    LinearLayout navLogoutMenuItem;
    TextView textViewUsername;
    ImageView imageViewUserThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = FirebaseService.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        navViewMenu = findViewById(R.id.navViewMenu);

        navHomeMenuItem = findViewById(R.id.nav_home);
        navUpdateUserProfileMenuItem = findViewById(R.id.nav_update_user_profile);
        navLogoutMenuItem = findViewById(R.id.nav_logout);

        View navViewMenuHeaderView = navViewMenu.getHeaderView(0);

        imageViewUserThumbnail = navViewMenuHeaderView.findViewById(R.id.imageViewUserThumbnail);
        textViewUserEmail = navViewMenuHeaderView.findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText(currentUser.getEmail());
        if(currentUser.getPhotoUrl() == null){
            imageViewUserThumbnail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.insert_photo));
        }else{
            new DownloadImageTask(imageViewUserThumbnail)
                    .execute(currentUser.getPhotoUrl().toString());
        }

        textViewUsername = navViewMenuHeaderView.findViewById(R.id.textViewUsername);
        if(currentUser.getDisplayName() != null){
            textViewUsername.setText(currentUser.getDisplayName());
        }


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(this)).commit();
        }

        navHomeMenuItem.setOnClickListener(l -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(this)).commit();
            drawerLayout.closeDrawers();
        });

        navUpdateUserProfileMenuItem.setOnClickListener(l -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new UpdateUserProfileFragment(this)).commit();
            drawerLayout.closeDrawers();
        });

        navLogoutMenuItem.setOnClickListener(l -> {
            FirebaseService.signOutUser();
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment(this));
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}