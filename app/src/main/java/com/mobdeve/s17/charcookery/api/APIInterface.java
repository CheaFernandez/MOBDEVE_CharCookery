package com.mobdeve.s17.charcookery.api;

import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.api.models.CreateAccountBody;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipes();

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipesFromCategory(@Query("category") String category);

    @GET("/api/recipes/{userId}")
    Call<List<RecipeItem>> getRecipesForUser(@Path("userId") String userId);

    @GET("/api/recipes/{userId}")
    Call<List<RecipeItem>> getRecipesForUserWithFilters(
            @Path("userid") String userId,
            @Query("q") String q,
            @Query("category") String category,
            @Query("is_favorite") boolean isFavorite,
            @Query("max_time") int maxTime
    );

    /* USER / AUTH ROUTES */
    @FormUrlEncoded
    @POST("/api/token")
    Call<AccessTokenResponse> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("/api/user")
    Call<AccessTokenResponse> createAccount(@Body CreateAccountBody requestBody);

    @PUT("/api/user/{userId}") // Add this line
    Call<AccessTokenResponse> updateProfile(
            @Path("userId") String userId,
            @Body CreateAccountBody UserProfileUpdateRequest
    );

    @PUT("/api/recipes/{recipeId}/notes")
    Call<Void> updateRecipeNotes(
            @Path("recipeId") String recipeId,
            @Body Map<String, String> notes
    );
}

