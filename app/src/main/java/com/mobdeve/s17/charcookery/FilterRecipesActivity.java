package com.mobdeve.s17.charcookery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.adapters.RecipesPreviewAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.mobdeve.s17.charcookery.adapters.RecipePagerAdapter;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

// ... (import statements)

public class FilterRecipesActivity extends AppCompatActivity {

    Spinner categorySpinner;
    EditText editIngredientsContainsField;
    EditText max_time_editText;
    AppCompatButton filterRecipesBtn;
    AppCompatButton cancelFilterBtn;

    private RecipesPreviewAdapter recipesPreviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_filter);

        categorySpinner = findViewById(R.id.filter_category_spinner);
        editIngredientsContainsField = findViewById(R.id.edit_ingredients_contains_field);

        String[] items = {"Weekly Meal Plans", "Dinner Date Ideas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        editIngredientsContainsField.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editIngredientsContainsField.setSingleLine(false);
        editIngredientsContainsField.setMaxLines(Integer.MAX_VALUE);

        max_time_editText = findViewById(R.id.max_time_editText);

        // Instantiate the RecipesPreviewAdapter
        recipesPreviewAdapter = new RecipesPreviewAdapter(new ArrayList<>());

        filterRecipesBtn = findViewById(R.id.filter_recipes_btn);
        // Add filter functionality
        filterRecipesBtn.setOnClickListener(v -> {
            applyFilters();
        });

        cancelFilterBtn = findViewById(R.id.cancel_btn);
        cancelFilterBtn.setOnClickListener(v -> {
            gotoPreviousPage();
        });
    }

    private void applyFilters() {
        // Gather filter options from UI
        String userId = getUserId();
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        String ingredientsContains = editIngredientsContainsField.getText().toString();
        Integer maxTime = Integer.parseInt(max_time_editText.getText().toString());

        // Make API call with filters
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<RecipeItem>> call = apiInterface.getRecipesForUserWithFilters(
                userId,
                ingredientsContains,
                selectedCategory,
                false,
                maxTime
        );

        APICaller.enqueue(call, recipes -> {
            updateRecipesList(recipes);
            // Assuming rvRecipesGrid is your correct RecyclerView ID
            RecyclerView rvRecipes = findViewById(R.id.rvRecipesGrid);

            // Convert List to ArrayList
            ArrayList<RecipeItem> recipesArrayList = new ArrayList<>(recipes);

            // Set up the RecyclerView and adapter
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvRecipes.setLayoutManager(layoutManager);

            // Instantiate the RecipesPreviewAdapter
            recipesPreviewAdapter = new RecipesPreviewAdapter(recipesArrayList);

            // Set the adapter to the RecyclerView
            rvRecipes.setAdapter(recipesPreviewAdapter);

            // Notify the adapter that the data has changed
            recipesPreviewAdapter.notifyDataSetChanged();
        });

    }


    private void updateRecipesList(List<RecipeItem> recipes) {
        recipesPreviewAdapter.setRecipes(recipes);
        recipesPreviewAdapter.notifyDataSetChanged();
    }

    private String getUserId() {
        SharedPreferences prefs = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        return prefs.getString(Constants.SP_USER_ID, null);
    }

    private void gotoPreviousPage() {
        finish();
    }
}

