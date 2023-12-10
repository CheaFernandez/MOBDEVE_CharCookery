package com.mobdeve.s17.charcookery.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("user_id")
    private String userId;

    public Category(String id, String title, String userId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
    }

    public Category(String title, String userId) {
        this.title = title;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }
}
