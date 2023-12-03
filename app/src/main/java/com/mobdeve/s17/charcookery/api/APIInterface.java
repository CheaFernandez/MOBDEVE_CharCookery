package com.mobdeve.s17.charcookery.api;

import com.mobdeve.s17.charcookery.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/api/recipes")
    Call<List<Recipe>> getListCommunityRecipes();

}
