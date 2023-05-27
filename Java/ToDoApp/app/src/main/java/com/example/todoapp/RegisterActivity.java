package com.example.todoapp;

import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.todoapp.Services.FirebaseService;
import io.github.muddz.styleabletoast.StyleableToast;

public class RegisterActivity extends CustomAppCompatActivity {

    TextView editTextUserEmailRegister;
    TextView editTextUserPasswordRegister;
    TextView editTextUserPasswordRepeatRegister;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUserEmailRegister = findViewById(R.id.editTextUserEmailRegister);
        editTextUserPasswordRegister = findViewById(R.id.editTextUserPasswordRegister);
        editTextUserPasswordRepeatRegister = findViewById(R.id.editTextUserPasswordRepeatRegister);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(l -> {
            String email = editTextUserEmailRegister.getText().toString();
            String password = editTextUserPasswordRegister.getText().toString();
            String passwordRepeat = editTextUserPasswordRepeatRegister.getText().toString();

            if (email.equals("") || password.equals("") || passwordRepeat.equals("")) {
                new StyleableToast
                        .Builder(this)
                        .text(getString(R.string.register_fill_blanks))
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else if (!password.equals(passwordRepeat)) {
                new StyleableToast
                        .Builder(this)
                        .text(getString(R.string.password_and_password_repeat_should_same))
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else if (password.length() < 6) {
                new StyleableToast
                        .Builder(this)
                        .text(getString(R.string.password_min_length_error))
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else {
                FirebaseService.registerWithEmail(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        new StyleableToast
                                .Builder(this)
                                .text(getString(R.string.successfully_signed_up))
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.GREEN)
                                .show();
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        HorizontalDividerFragment newFragment = new HorizontalDividerFragment(null, false);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frameLayoutHorizontalDividerRegister, newFragment).commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void redirectLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}