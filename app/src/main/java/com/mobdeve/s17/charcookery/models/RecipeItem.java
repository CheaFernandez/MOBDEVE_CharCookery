package com.mobdeve.s17.charcookery.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecipeItem implements Serializable {
    private int imageResource;

    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("is_favorite")
    private boolean isFavorite;

    @SerializedName("notes")
    private String notes;

    @SerializedName("duration_mins")
    private int durationMinutes;

    @SerializedName("cover_image")
    private String coverImage;

    @SerializedName("instructions")
    private String[] instructions;

    @SerializedName("ingredients")
    private String[] ingredients;

    @SerializedName("source_url")
    private String sourceUrl;

    private String userId;

    public RecipeItem(int imageResource, String title, String category) {
        this.imageResource = imageResource;
        this.name = title;
        this.category = category;
        this.isFavorite = false;
    }

    public RecipeItem(int imageResource, String title, String category, Boolean isFavorite) {
        this.imageResource = imageResource;
        this.name = title;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    public RecipeItem(String id, String category, String name, String notes, int durationMinutes,
                  String coverImage, String[] instructions, String[] ingredients, String sourceUrl) {
        // For community recipes
        this.id = id;
        this.category = category;
        this.name = name;
        this.notes = notes;
        this.durationMinutes = durationMinutes;
        this.coverImage = coverImage;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.sourceUrl = sourceUrl;
        this.isFavorite = false;
    }

    public RecipeItem(String id, String category, String name, String notes, int durationMinutes,
                      String coverImage, String[] instructions, String[] ingredients, boolean isFavorite, String userId) {
        // For user recipes
        this.id = id;
        this.category = category;
        this.name = name;
        this.notes = notes;
        this.durationMinutes = durationMinutes;
        this.coverImage = coverImage;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.isFavorite = isFavorite;
        this.userId = userId;
    }

    public RecipeItem(String category, String name, String notes, int durationMinutes,
                      String coverImage, String[] instructions, String[] ingredients, boolean isFavorite, String userId) {
        // For created user recipes (no id yet)
        this.category = category;
        this.name = name;
        this.notes = notes;
        this.durationMinutes = durationMinutes;
        this.coverImage = coverImage;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.isFavorite = isFavorite;
        this.userId = userId;
    }

    public Bitmap getImageResource() {
        if (coverImage == null) {
            // Use placeholder
            return getBitmapFromURL("https://placehold.co/600x400");
        }
        return getBitmapFromURL(coverImage);
    }

    public String getTitle() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Boolean checkFavorite() {
        return this.isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    public String getCoverImage() {
        final String placeholderImage = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NXx8fGVufDB8fHx8fA%3D%3D";

        if (coverImage == null)
            return placeholderImage;
        return coverImage;
    }

    public String getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public void setNotes(String editedNotes) {
        this.notes = editedNotes;
        // Update the displayed notes in the notes tab

    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
