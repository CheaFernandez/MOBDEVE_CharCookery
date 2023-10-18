package com.mobdeve.s17.charcookery;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.CategoryListAdapter;
import com.mobdeve.s17.charcookery.adapters.CollectionRecipesAdapter;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
    private TextView tvTitle;
    private RecyclerView rvRecipes;
    private RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        setupView();
    }

    private void setupView() {
        tvTitle = findViewById(R.id.collectionTitle);
        rvRecipes = findViewById(R.id.rvCollectionGrid);

        // Fetch data from intent
        RecipeCollection receivedRecipeCollection = (RecipeCollection) getIntent().getSerializableExtra("recipeCollection");

        if (receivedRecipeCollection != null) {
            String title = receivedRecipeCollection.getTitle();
            ArrayList<RecipeItem> recipes = receivedRecipeCollection.getRecipes();

            tvTitle.setText(title);
            rvAdapter = new CollectionRecipesAdapter(recipes);
            rvRecipes.setAdapter(rvAdapter);
        }

    }
}
