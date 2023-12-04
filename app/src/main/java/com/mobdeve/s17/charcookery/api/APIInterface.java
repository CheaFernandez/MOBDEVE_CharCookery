package com.mobdeve.s17.charcookery.api;

import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipes();

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipesFromCategory(@Query("category") String category);

}
