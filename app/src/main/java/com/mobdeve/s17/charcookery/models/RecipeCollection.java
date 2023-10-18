package com.mobdeve.s17.charcookery.models;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeCollection implements Serializable {
    private String title;
    private ArrayList<RecipeItem> recipes;

    public RecipeCollection(String title) {
        this.title = title;
        this.recipes = new ArrayList<>();
    }

    public RecipeCollection(String title, ArrayList<RecipeItem> recipes) {
        this.title = title;
        this.recipes = recipes;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<RecipeItem> getRecipes() {
        return recipes;
    }
}
