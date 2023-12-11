package com.mobdeve.s17.charcookery.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.AddRecipeActivity;
import com.mobdeve.s17.charcookery.Constants;
import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.adapters.CollectionRecipesAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.models.Mocker;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class RecipesFragment extends Fragment {
    private RecyclerView rvRecipes;
    private RecyclerView.Adapter rvAdapter;
    private View view;
    private EditText etSearch;
    private ImageButton addRecipeBtn, filterRecipesBtn;

    AppCompatButton btnApplyFilters;
    private APIInterface apiInterface;


    public RecipesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create APIInterface instance
        apiInterface = APIClient.getClient().create(APIInterface.class);

        view = inflater.inflate(R.layout.fragment_recipes, container, false);

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        setupView();

        etSearch = view.findViewById(R.id.etSearch);

        btnApplyFilters = view.findViewById(R.id.btnApplyFilters);
        btnApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(etSearch.getText().toString());
            }
        });

        return view;
    }
    private void setupView() {
        addRecipeBtn = view.findViewById(R.id.addRecipeBtn);
        addRecipeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), AddRecipeActivity.class);
            startActivity(intent);
        });

        // Setup recipes
        rvRecipes = view.findViewById(R.id.rvRecipesGrid);

        // Get user id
        SharedPreferences prefs = getContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        // Fetch user recipes from API
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<RecipeItem>> call = apiInterface.getRecipesForUser(userId);
        APICaller.enqueue(call, recipes -> {
            // On successful fetch result, display up to the first 5 recipes
            rvAdapter = new CollectionRecipesAdapter(new ArrayList<>(recipes));
            rvRecipes.setAdapter(rvAdapter);
        });
    }
    private void performSearch(String query) {
        if (!query.isEmpty()) {
            // Prepare filter
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("q", query);
            fetchRecipesFromApi(query, queryMap);
        } else {
            // If query is empty, display all recipes
            fetchRecipesFromApi(query, null);
        }
    }

    private void fetchRecipesFromApi(String query, Map<String, Object> filters) {
        // Get user id
        SharedPreferences prefs = getContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        // For now, let's log the query and fetch mock data
        System.out.println("Search Query: " + query);

        // Assuming you have an APIInterface with a method for searching recipes
        Call<List<RecipeItem>> call = apiInterface.getRecipesForUserWithFilters(userId, filters);
        call.enqueue(new Callback<List<RecipeItem>>() {
            @Override
            public void onResponse(Call<List<RecipeItem>> call, Response<List<RecipeItem>> response) {
                if (response.isSuccessful()) {
                    List<RecipeItem> searchedRecipes = response.body();

                    // Update the RecyclerView with the searched recipes
                    updateRecipesBasedOnSearch(searchedRecipes);
                } else {
                    // Handle unsuccessful response
                    Log.e("RecipesFragment", "API request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RecipeItem>> call, Throwable t) {
                // Handle failure
                Log.e("RecipesFragment", "API request failed: " + t.getMessage());
            }
        });
    }

    private void updateRecipesBasedOnSearch(List<RecipeItem> searchedRecipes) {
        System.out.println("Searched Recipes: " + searchedRecipes);
        ((CollectionRecipesAdapter) rvAdapter).updateData(searchedRecipes);
    }

//    private void updateRecipesBasedOnSearch(String query) {
//        ArrayList<RecipeItem> filteredRecipes = filterRecipes(query);
//        ((CollectionRecipesAdapter) rvAdapter).updateData(filteredRecipes);
//    }
//    private ArrayList<RecipeItem> filterRecipes(String query) {
//        ArrayList<RecipeItem> filteredRecipes = new ArrayList<>();
//
//        if (query == null || query.trim().isEmpty()) {
//            // If the query is empty or null, return all recipes
//            return Mocker.generateRecipeItems(3, 5); // Adjust parameters as needed
//        }
//
//        // Replace this line with your actual data source or method to get recipes
//        List<RecipeItem> allRecipes =
//
//        for (RecipeItem recipe : allRecipes) {
//            // Check if the recipe name or category contains the query (case-insensitive)
//            if ((recipe.getTitle() != null && recipe.getTitle().toLowerCase().contains(query.toLowerCase())) ||
//                    (recipe.getCategory() != null && recipe.getCategory().toLowerCase().contains(query.toLowerCase()))) {
//                filteredRecipes.add(recipe);
//            }
//        }
//        return filteredRecipes;
//    }

}