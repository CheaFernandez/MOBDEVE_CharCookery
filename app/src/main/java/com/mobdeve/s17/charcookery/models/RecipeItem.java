package com.mobdeve.s17.charcookery.models;

public class RecipeItem {
    private int imageResource;
    private String title;
    private String category;
    private Boolean isFavorite;

    public RecipeItem(int imageResource, String title, String category) {
        this.imageResource = imageResource;
        this.title = title;
        this.category = category;
        this.isFavorite = false;
    }

    public RecipeItem(int imageResource, String title, String category, Boolean isFavorite) {
        this.imageResource = imageResource;
        this.title = title;
        this.category = category;
        this.isFavorite = isFavorite;
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

    public Boolean checkFavorite() {
        return isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }
}
