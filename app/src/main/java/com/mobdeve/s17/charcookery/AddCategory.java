package com.mobdeve.s17.charcookery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class AddCategory extends AppCompatActivity {

    EditText edit_category_title;
    Button cancel_btn;
    Button addcategory_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        edit_category_title = findViewById(R.id.edit_title_field);
        cancel_btn = findViewById(R.id.cancel_btn);
        addcategory_btn = findViewById(R.id.register_btn);
    }
    public void cancel_add(View view) {
        edit_category_title.setText("");
        Intent homeIntent = new Intent(AddCategory.this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void add_category(View view) {
        // TODO: Add category to db and display in categories
    }
}