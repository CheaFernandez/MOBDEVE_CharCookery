package com.mobdeve.s17.charcookery.models;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("photo_url")
    private String photoURL;

    @SerializedName("dietary_restrictions")
    private String dietaryRestrictions;

    public UserProfile(String id, String name, String email, String photoURL, String dietaryRestrictions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photoURL = photoURL;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }
}
