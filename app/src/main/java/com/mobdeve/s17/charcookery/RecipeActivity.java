package com.mobdeve.s17.charcookery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import com.mobdeve.s17.charcookery.adapters.RecipePagerAdapter;
import com.mobdeve.s17.charcookery.components.BaseRecipeActivity;

public class RecipeActivity extends BaseRecipeActivity {

    private TabLayout recipeTabLayout;
    private ViewPager viewPager;
    private RecipePagerAdapter recipePagerAdapter;
    ImageButton cookingModeButton;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_recipe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        // Initialize the TabLayout
        recipeTabLayout = findViewById(R.id.recipeTabLayout);

        // Initialize the ViewPager
        viewPager = findViewById(R.id.viewPager);

        // Create an instance of the adapter that holds the fragments
        recipePagerAdapter = new RecipePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the adapter
        viewPager.setAdapter(recipePagerAdapter);

        // Link the TabLayout to the ViewPager
        recipeTabLayout.setupWithViewPager(viewPager);

        cookingModeButton = findViewById(R.id.cookingModeButton);
        cookingModeButton.setOnClickListener(v -> {
            // Go to Cooking Mode Activity
            goToCookingModeActivity();
        });
    }

    private void goToCookingModeActivity() {
        Intent intent = new Intent(RecipeActivity.this, CookingModeActivity.class);
        startActivity(intent);
    }
}
