package com.mobdeve.s17.charcookery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterRecipesActivity extends AppCompatActivity {

    Spinner categorySpinner;
    EditText edit_ingredients_contains_field;
    AppCompatButton filter_recipes_btn;
    AppCompatButton cancel_filter_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_filter);

        categorySpinner = findViewById(R.id.filter_category_spinner);

        String[] items = {"Breakfast", "Lunch", "Dinner", "Snack", "Dessert"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        edit_ingredients_contains_field = findViewById(R.id.edit_ingredients_contains_field);
        edit_ingredients_contains_field.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edit_ingredients_contains_field.setSingleLine(false);
        edit_ingredients_contains_field.setMaxLines(Integer.MAX_VALUE);

        filter_recipes_btn = findViewById(R.id.filter_recipes_btn);
        // TODO: Add filter functionality
        filter_recipes_btn.setOnClickListener(v -> {
            // Set by default to go back to previous page
            gotoPreviousPage(v);
        });

        cancel_filter_btn = findViewById(R.id.cancel_btn);
        cancel_filter_btn.setOnClickListener(v -> {
            gotoPreviousPage(v);
        });
    }

    public void gotoPreviousPage(android.view.View v) {
        finish();
    }

}