package com.example.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.todoapp.Services.FirebaseService;
import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends CustomAppCompatActivity {
    Button buttonLogin;
    EditText editTextUserEmailLogin;
    EditText editTextUserPasswordLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseService.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        buttonLogin = findViewById(R.id.buttonLogin);
        editTextUserEmailLogin = findViewById(R.id.editTextUserEmailLogin);
        editTextUserPasswordLogin = findViewById(R.id.editTextUserPasswordLogin);

        buttonLogin.setOnClickListener(l -> {
            FirebaseService.signInWithEmail(
                    editTextUserEmailLogin.getText().toString(),
                    editTextUserPasswordLogin.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    new StyleableToast
                            .Builder(this)
                            .text(getString(R.string.successfully_logged_in))
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GREEN)
                            .show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else{
                    new StyleableToast
                            .Builder(this)
                            .text(task.getException().getMessage())
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .show();
                }
            });
        });

    }

    public void redirectRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}