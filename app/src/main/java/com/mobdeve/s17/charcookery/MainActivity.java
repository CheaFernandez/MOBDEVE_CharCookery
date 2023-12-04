package com.mobdeve.s17.charcookery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.CategoryListAdapter;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.components.RecipeCollectionPreview;
import com.mobdeve.s17.charcookery.models.Mocker;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private RecyclerView.Adapter rvAdapter;
    private ArrayList<RecipeItem> myRecipes;

    private ArrayList<RecipeCollection> recipeCollections;
    private ArrayList<String> categories;

    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        getCommunityRecipes();

        setupMockData();

        initCollectionPreviews();
        initCategories();
    }

    private void getCommunityRecipes() {
        Call<List<RecipeItem>> call = apiInterface.getListCommunityRecipes();
        call.enqueue(new Callback<List<RecipeItem>>() {
            @Override
            public void onResponse(Call<List<RecipeItem>> call, Response<List<RecipeItem>> response) {
                Log.d("TAG",response.code()+"");

                if (response.isSuccessful()) {
                    List<RecipeItem> recipes = response.body();
                    if (recipes != null) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RecipeItem>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void initCollectionPreviews() {
        RecipeCollectionPreview collectionMyRecipes = findViewById(R.id.collectionMyRecipes);
        collectionMyRecipes.setTitle("My Recipes");
        collectionMyRecipes.setRecipes(myRecipes);

        for (int i = 0; i < recipeCollections.size(); i++) {
            RecipeCollectionPreview collectionPreview = findViewById(getResources().getIdentifier("collectionCustom" + (i+1), "id", getPackageName()));
            RecipeCollection collection = recipeCollections.get(i);

            collectionPreview.setTitle(collection.getTitle());
            collectionPreview.setRecipes(collection.getRecipes());
            collectionPreview.setSeeAllClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                    intent.putExtra("recipeCollection", collection);
                    startActivity(intent);
                }
            });
        }
    }

    private void initCategories() {
        rvCategories = findViewById(R.id.rvCategories);

        rvAdapter = new CategoryListAdapter(categories);
        rvCategories.setAdapter(rvAdapter);
    }

    private void setupMockData() {
        // NOTE: For MCO2 only
        // Setup user recipes
        myRecipes = Mocker.generateRecipeItems(5, 12);

        // Generate categories (just names, no recipes yet)
        this.categories = Mocker.generateCategoryNames(5);

        // Setup custom recipe collections (not user-generated; provided by server)
        // Each collection has 3-10 recipes (randomized mock data)
        recipeCollections = new ArrayList<RecipeCollection>(){
            {
                add(new RecipeCollection("All Time Recipes", Mocker.generateRecipeItems(3, 10)));
                add(new RecipeCollection("Weekly Meal Plans", Mocker.generateRecipeItems(3, 10)));
                add(new RecipeCollection("Dinner Date Ideas", Mocker.generateRecipeItems(3, 10)));
            }
        };

    }

    public void gotoAddCategoryView(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }

    public void gotoRecipeView(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }
}