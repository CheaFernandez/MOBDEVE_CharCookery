package com.mobdeve.s17.charcookery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.RecipesPreviewAdapter;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<RecipeItem> recipeItems = new ArrayList<>();
        recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category"));
        recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category"));
        recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category"));

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecipesPreviewAdapter(recipeItems);
        recyclerView.setAdapter(adapter);
    }
}