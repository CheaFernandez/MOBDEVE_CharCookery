package com.mobdeve.s17.charcookery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    AppCompatButton register_btn;
    AppCompatButton cancel_btn;
    EditText usernameEt;
    EditText passwordEt;
    EditText emailEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        register_btn = findViewById(R.id.register_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        usernameEt = findViewById(R.id.loginEmailEt);
        passwordEt = findViewById(R.id.loginPasswordEt);
        emailEt = findViewById(R.id.emailEt);

        register_btn.setOnClickListener(v -> {
            // TODO: Register user
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();
            String email = emailEt.getText().toString();

            //Default
            finish();
        });

        cancel_btn.setOnClickListener(v -> {
            finish();
        });

    }
}