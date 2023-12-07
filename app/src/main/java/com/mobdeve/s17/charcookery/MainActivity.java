package com.mobdeve.s17.charcookery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.CategoryListAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.components.RecipeCollectionPreview;
import com.mobdeve.s17.charcookery.models.Mocker;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private RecyclerView.Adapter rvAdapter;

    private ArrayList<RecipeCollection> recipeCollections;
    private ArrayList<String> categories;

    private APIInterface apiInterface;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);

        setupMockData();

        initCollectionPreviews();
        initCategories();
    }

    private void fetchCommunityRecipes(String category, int id) {
        Call<List<RecipeItem>> call = apiInterface.getListCommunityRecipesFromCategory(category);
        APICaller.enqueue(call, new APICaller.APICallback<List<RecipeItem>>() {
            @Override
            public void onSuccess(List<RecipeItem> recipes) {
                RecipeCollection collectionResults = new RecipeCollection(category, new ArrayList<>(recipes));
                recipeCollections.add(collectionResults);
                initCustomCollection(collectionResults, id);
            }
        });
    }

    private void initCustomCollection(RecipeCollection collection, int id) {
        RecipeCollectionPreview collectionPreview = findViewById(getResources().getIdentifier("collectionCustom" + id, "id", getPackageName()));

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

    private void initCollectionPreviews() {
        // Initialize community recipes preview
        recipeCollections = new ArrayList<RecipeCollection>();
        fetchCommunityRecipes("Weekly Meal Plans", 1);
        fetchCommunityRecipes("Dinner Date Ideas", 2);

        RecipeCollectionPreview collectionMyRecipes = findViewById(R.id.collectionMyRecipes);
        collectionMyRecipes.setTitle("My Recipes");

        // Get user id
        SharedPreferences prefs = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        // Initialize user recipes preview
        Call<List<RecipeItem>> call = apiInterface.getRecipesForUser(userId);
        APICaller.enqueue(call, new APICaller.APICallback<List<RecipeItem>>() {
            @Override
            public void onSuccess(List<RecipeItem> recipes) {
                // Display up to first 5 recipes
                int maxItems = Math.min(5, recipes.size());
                List<RecipeItem> itemsToPreview = recipes.subList(0, maxItems);
                collectionMyRecipes.setRecipes(new ArrayList<>(itemsToPreview));
            }
        });
    }

    private void initCategories() {
        rvCategories = findViewById(R.id.rvCategories);

        rvAdapter = new CategoryListAdapter(categories);
        rvCategories.setAdapter(rvAdapter);
    }

    private void setupMockData() {
        // NOTE: For MCO2 only
        // Generate categories (just names, no recipes yet)
        this.categories = Mocker.generateCategoryNames(5);
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