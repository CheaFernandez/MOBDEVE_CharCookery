package com.mobdeve.s17.charcookery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.mobdeve.s17.charcookery.components.BaseRecipeActivity;

public class RecipeInstructionsActivity extends BaseRecipeActivity {

    TabLayout recipeTabLayout;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_recipe_instructions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.recipeTabLayout = findViewById(R.id.recipeTabLayout);

        TabLayout.Tab ingredientsTab = recipeTabLayout.getTabAt(1);
        if (ingredientsTab != null) {
            ingredientsTab.select();
        }
    }


}