package com.mobdeve.s17.charcookery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.Constants;
import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.adapters.CollectionRecipesAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.models.Mocker;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.api.models.CreateAccountBody;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RecipesFragment extends Fragment {
    private RecyclerView rvRecipes;
    private RecyclerView.Adapter rvAdapter;
    private View view;

    public RecipesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recipes, container, false);

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        setupView();

        EditText etSearch = view.findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    performSearch(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void setupView() {
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
            fetchRecipesFromApi(query);
        }
    }

    private void fetchRecipesFromApi(String query) {
        // For now, let's log the query and fetch mock data
        System.out.println("Search Query: " + query);

        // Assuming you have an APIInterface with a method for searching recipes
        Call<List<RecipeItem>> call = apiInterface.getRecipesForUserWithFilters(
                userId, // Replace with the actual user ID
                query,
                null, // Set category to null, or provide a category if needed
                false, // Assuming isFavorite is false, update as needed
                0
        );
        call.enqueue(new Callback<List<RecipeItem>>() {
            @Override
            public void onResponse(Call<List<RecipeItem>> call, Response<List<RecipeItem>> response) {
                if (response.isSuccessful()) {
                    List<RecipeItem> searchedRecipes = response.body();

                    // Update the RecyclerView with the searched recipes
                    updateRecipesBasedOnSearch(searchedRecipes);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<List<RecipeItem>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void updateRecipesBasedOnSearch(List<RecipeItem> searchedRecipes) {
        // Replace this with your actual implementation to update the RecyclerView
        // with the searched recipes
        // For now, let's log the recipes
        System.out.println("Searched Recipes: " + searchedRecipes);

        // Assuming you have a method to update the RecyclerView adapter
        // with the new list of recipes
        ((CollectionRecipesAdapter) rvAdapter).updateData(searchedRecipes);
    }

    private void updateRecipesBasedOnSearch(String query) {
        // Replace this with your actual implementation to update the RecyclerView
        ArrayList<RecipeItem> filteredRecipes = filterRecipes(query);
        ((CollectionRecipesAdapter) rvAdapter).updateData(filteredRecipes);
    }
    private ArrayList<RecipeItem> filterRecipes(String query) {
        // Replace this with your actual implementation to filter the data source
        return Mocker.generateRecipeItems(3, 10);
    }
}