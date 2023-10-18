package com.mobdeve.s17.charcookery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.CategoryListAdapter;
import com.mobdeve.s17.charcookery.adapters.RecipesPreviewAdapter;
import com.mobdeve.s17.charcookery.components.RecipeCollectionPreview;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initCategories();
    }

    private void initView() {
        RecipeCollectionPreview collectionMyRecipes = findViewById(R.id.collectionMyRecipes);
        collectionMyRecipes.setTitle("My Recipes");
        collectionMyRecipes.setRecipes(generateMockRecipeItems(4));

        RecipeCollectionPreview collectionCustomRecipes1 = findViewById(R.id.collectionCustom1);
        collectionCustomRecipes1.setTitle("All Time Recipes");
        collectionCustomRecipes1.setRecipes(generateMockRecipeItems(4));

        RecipeCollectionPreview collectionCustomRecipes2 = findViewById(R.id.collectionCustom2);
        collectionCustomRecipes2.setTitle("Weekly Meal Plans");
        collectionCustomRecipes2.setRecipes(generateMockRecipeItems(4));

        RecipeCollectionPreview collectionCustomRecipes3 = findViewById(R.id.collectionCustom3);
        collectionCustomRecipes3.setTitle("Dinner Date Ideas");
        collectionCustomRecipes3.setRecipes(generateMockRecipeItems(4));
    }

    private void initCategories() {
        rvCategories = findViewById(R.id.rvCategories);

        ArrayList<String> categories = generateMockCategories(5);
        rvAdapter = new CategoryListAdapter(categories);
        rvCategories.setAdapter(rvAdapter);
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

    private ArrayList<String> generateMockCategories(int count) {
        // NOTE: For MCO2 only
        ArrayList<String> categoryNames = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            categoryNames.add("Category " + i);
        }

        return categoryNames;
    }
}