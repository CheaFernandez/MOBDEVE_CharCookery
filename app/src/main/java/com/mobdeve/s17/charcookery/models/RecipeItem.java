package com.mobdeve.s17.charcookery.models;

public class RecipeItem {
    private int imageResource;
    private String title;
    private String category;

    public RecipeItem(int imageResource, String title, String category) {
        this.imageResource = imageResource;
        this.title = title;
        this.category = category;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }
}
