package com.mobdeve.s17.charcookery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton login_btn;
    AppCompatButton cancel_btn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        login_btn = findViewById(R.id.register_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        login_btn.setOnClickListener(v -> {
            // TODO: check for login credentials
            // start activity intent to main
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        cancel_btn.setOnClickListener(v -> {
            // go back to prev page
            finish();
        });
    }
}
