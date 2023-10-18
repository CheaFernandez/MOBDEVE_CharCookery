package com.mobdeve.s17.charcookery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.CategoryListAdapter;
import com.mobdeve.s17.charcookery.components.RecipeCollectionPreview;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private RecyclerView.Adapter rvAdapter;

    private ArrayList<RecipeCollection> recipeCollections;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMockData();

        initCollectionPreviews();
        initCategories();
    }

    private void initCollectionPreviews() {
        RecipeCollectionPreview collectionMyRecipes = findViewById(R.id.collectionMyRecipes);
        collectionMyRecipes.setTitle("My Recipes");
        collectionMyRecipes.setRecipes(generateMockRecipeItems(4));

        for (int i = 0; i < recipeCollections.size(); i++) {
            RecipeCollectionPreview collectionPreview = findViewById(getResources().getIdentifier("collectionCustom" + (i+1), "id", getPackageName()));
            RecipeCollection collection = recipeCollections.get(i);

            collectionPreview.setTitle(collection.getTitle());
            collectionPreview.setRecipes(collection.getRecipes());
            collectionPreview.setSeeAllClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                    intent.putExtra("recipeCollection", collection);
                    startActivity(intent);
                }
            });
        }
    }

    private void initCategories() {
        rvCategories = findViewById(R.id.rvCategories);

        rvAdapter = new CategoryListAdapter(categories);
        rvCategories.setAdapter(rvAdapter);
    }

    private void setupMockData() {
        // NOTE: For MCO2 only
        this.categories = generateMockCategories(5);

        // Setup custom recipe collections (not user-generated; provided by server)
        // Each collection has 3-10 recipes (randomized mock data)
        recipeCollections = new ArrayList<>();

        recipeCollections.add(new RecipeCollection("All Time Recipes", generateMockRecipeItems(3, 10)));
        recipeCollections.add(new RecipeCollection("Weekly Meal Plans", generateMockRecipeItems(3, 10)));
        recipeCollections.add(new RecipeCollection("Dinner Date Ideas", generateMockRecipeItems(3, 10)));
    }

    private ArrayList<RecipeItem> generateMockRecipeItems(int count) {
        // NOTE: For MCO2 only
        ArrayList<RecipeItem> recipeItems = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            Boolean isFavorite = rand.nextFloat() < 0.4f; // 40% chance of being a favorite recipe
            recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category", isFavorite));
        }

        return recipeItems;
    }

    private ArrayList<RecipeItem> generateMockRecipeItems(int minCount, int maxCount) {
        // NOTE: For MCO2 only
        ArrayList<RecipeItem> recipeItems = new ArrayList<>();
        Random rand = new Random();

        int count = rand.nextInt(maxCount - minCount + 1) + minCount;

        for (int i = 0; i < count; i++) {
            Boolean isFavorite = rand.nextFloat() < 0.4f; // 40% chance of being a favorite recipe
            recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category", isFavorite));
        }

        return recipeItems;
    }

    private ArrayList<String> generateMockCategories(int count) {
        // NOTE: For MCO2 only
        ArrayList<String> categoryNames = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            categoryNames.add("Category " + i);
        }

        return categoryNames;
    }
}