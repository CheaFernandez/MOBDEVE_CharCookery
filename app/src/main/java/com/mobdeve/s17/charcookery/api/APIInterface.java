package com.mobdeve.s17.charcookery.api;

import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.api.models.CreateAccountBody;
import com.mobdeve.s17.charcookery.api.models.EditableCategoryBody;
import com.mobdeve.s17.charcookery.api.models.MediaUploadResponse;
import com.mobdeve.s17.charcookery.api.models.UpdateRecipeFavoriteBody;
import com.mobdeve.s17.charcookery.api.models.UpdateRecipeNotesBody;
import com.mobdeve.s17.charcookery.models.UserProfile;
import com.mobdeve.s17.charcookery.models.Category;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    // tag: MEDIA
    @Multipart
    @POST("/api/upload")
    Call<MediaUploadResponse> uploadRecipeImage(
            @Header("Authorization") String authorizationHeader,
            @Part MultipartBody.Part media
    );


    // tag: AUTH

    @FormUrlEncoded
    @POST("/api/token")
    Call<AccessTokenResponse> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );


    // tag: USER

    @GET("/api/user")
    Call<UserProfile> getUserProfile();

    @POST("/api/user")
    Call<AccessTokenResponse> createAccount(@Body CreateAccountBody requestBody);

    @PUT("/api/user/{userId}")
    Call<AccessTokenResponse> updateProfile(
            @Path("userId") String userId,
            @Body CreateAccountBody UserProfileUpdateRequest
    );


    // tag: RECIPE

    @POST("/api/recipe")
    Call<RecipeItem> createRecipe(@Body RecipeItem recipe);

    @GET("/api/recipe/{recipeId}")
    Call<RecipeItem> getRecipeById(@Path("recipeId") String recipeId);

    @PATCH("/api/recipe/{recipeId}")
    Call<RecipeItem> updateRecipeNotes(
            @Path("recipeId") String recipeId,
            @Body UpdateRecipeNotesBody notes
    );

    @PATCH("/api/recipe/{recipeId}")
    Call<RecipeItem> updateRecipeFavoriteStatus(
            @Path("recipeId") String recipeId,
            @Body UpdateRecipeFavoriteBody isFavorite
    );

    @DELETE("/api/recipe/{recipeId}")
    Call<Void> deleteRecipeById(@Path("recipeId") String recipeId);


    // tag: RECIPES

    @GET("/api/recipes")
    Call<List<RecipeItem>> getListCommunityRecipesFromCategory(@Query("category") String category);

    @GET("/api/recipes/{userId}")
    Call<List<RecipeItem>> getRecipesForUser(@Path("userId") String userId);

    @GET("/api/recipes/{userId}")
    Call<List<RecipeItem>> getRecipesForUserWithFilters(
            @Path("userId") String userId,
            @Query("q") String q,
            @Query("category") String category,
            @Query("is_favorite") boolean isFavorite,
            @Query("max_time") int maxTime
    );

    @GET("/api/recipes/{userId}")
    Call<List<RecipeItem>> getRecipesForUserWithFilters(
            @Path("userId") String userId,
            @QueryMap Map<String, Object> queryParameters
    );


    // tag: CATEGORY

    @POST("/api/category")
    Call<Category> createCategory(@Body EditableCategoryBody category);

    @GET("/api/category/{categoryId}")
    Call<Category> getCategoryById(@Path("categoryId") String categoryId);

    @DELETE("/api/category/{categoryId}")
    Call<Void> deleteCategoryById(@Path("categoryId") String categoryId);


    // tag: CATEGORIES

    @GET("/api/categories/{userId}")
    Call<List<Category>> getCategoriesForUser(@Path("userId") String userId);










}

