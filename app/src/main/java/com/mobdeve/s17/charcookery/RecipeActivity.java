package com.mobdeve.s17.charcookery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    public void gotoPreviousPage(View v) {
        super.onBackPressed();
        finish();
    }
}