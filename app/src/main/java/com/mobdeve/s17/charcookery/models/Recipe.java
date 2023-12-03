package com.mobdeve.s17.charcookery.models;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

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

    public Recipe(String id, String category, String name, String notes, int durationMinutes,
                  String coverImage, String[] instructions, String[] ingredients, String sourceUrl) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.notes = notes;
        this.durationMinutes = durationMinutes;
        this.coverImage = coverImage;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.sourceUrl = sourceUrl;
    }

    // Add getters and setters as needed
}
