package com.mobdeve.s17.charcookery.models;

import com.mobdeve.s17.charcookery.R;

import java.util.ArrayList;
import java.util.Random;

public class Mocker {
    /*
        Generates mock data for MCO2 only (while backend has yet to be developed).
        TODO: Remove this class once backend is implemented.
     */
    public static ArrayList<String> generateCategoryNames(int count) {
        ArrayList<String> categoryNames = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            categoryNames.add("Category " + i);
        }

        return categoryNames;
    }

    public static ArrayList<RecipeItem> generateRecipeItems(int minCount, int maxCount) {
        ArrayList<RecipeItem> recipeItems = new ArrayList<>();
        Random rand = new Random();

        int count = rand.nextInt(maxCount - minCount) + minCount;

        for (int i = 0; i < count; i++) {
            Boolean isFavorite = rand.nextFloat() < 0.4f; // 40% chance of being a favorite recipe
            recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category", isFavorite));
        }

        return recipeItems;
    }

    public static ArrayList<RecipeItem> generateRecipeItems(int minCount, int maxCount, float chanceFavorite) {
        if (chanceFavorite < 0.0f || chanceFavorite > 1.0f) {
            throw new IllegalArgumentException("chanceFavorite must be between 0.0 and 1.0");
        }

        ArrayList<RecipeItem> recipeItems = new ArrayList<>();
        Random rand = new Random();

        int count = rand.nextInt(maxCount - minCount) + minCount;

        for (int i = 0; i < count; i++) {
            Boolean isFavorite = rand.nextFloat() < chanceFavorite;
            recipeItems.add(new RecipeItem(R.drawable.demo_food, "Recipe Name Here", "Category", isFavorite));
        }

        return recipeItems;
    }
}
