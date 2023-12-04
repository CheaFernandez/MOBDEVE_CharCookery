package com.mobdeve.s17.charcookery.api;

import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipes();

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipesFromCategory(@Query("category") String category);

    /* USER / AUTH ROUTES */
    @FormUrlEncoded
    @POST("/api/token")  // Replace with your OAuth token endpoint
    Call<AccessTokenResponse> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );

}
