package com.mobdeve.s17.charcookery.api.models;

public class UpdateRecipeFavoriteBody {
    public boolean is_favorite;

    public UpdateRecipeFavoriteBody(boolean isFavorite) {
        this.is_favorite = isFavorite;
    }
}
