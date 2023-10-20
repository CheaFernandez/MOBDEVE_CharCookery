package com.mobdeve.s17.charcookery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mobdeve.s17.charcookery.adapters.CollectionRecipesAdapter;
import com.mobdeve.s17.charcookery.models.Mocker;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        setupView();
    }

    private void setupView() {
        ArrayList<RecipeItem> recipes = Mocker.generateRecipeItems(3, 10);

        rvRecipes = findViewById(R.id.rvRecipesGrid);
        rvAdapter = new CollectionRecipesAdapter(recipes);
        rvRecipes.setAdapter(rvAdapter);
    }
}