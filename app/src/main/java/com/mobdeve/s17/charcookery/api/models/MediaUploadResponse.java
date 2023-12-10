package com.mobdeve.s17.charcookery.api.models;

import com.google.gson.annotations.SerializedName;

public class MediaUploadResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public MediaUploadResponse(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getFileName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }
}
