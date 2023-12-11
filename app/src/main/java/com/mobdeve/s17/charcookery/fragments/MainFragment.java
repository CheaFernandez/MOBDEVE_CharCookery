package com.mobdeve.s17.charcookery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.Constants;
import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.adapters.CategoryListAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.components.RecipeCollectionPreview;
import com.mobdeve.s17.charcookery.models.Category;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainFragment extends Fragment {

    private Context context;
    private View view;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        this.context = getContext();

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        // Setup RecyclerView for Categories
        inflateCategoryList();

        // Setup User Recipes - "My Recipes"
        inflateUserRecipesCollection();

        // Setup Community Recipes - "Weekly Meal Plans" and "Dinner Date Ideas"
        // inflateCommunityRecipesCollections();
        inflateCommunityRecipeCollection(R.id.collectionCustom1, "Weekly Meal Plans");
        inflateCommunityRecipeCollection(R.id.collectionCustom2, "Dinner Date Ideas");

        // Setup category add button
        view.findViewById(R.id.addCategoryLayout).setOnClickListener(v -> ((MainActivity) getActivity()).switchToAddCategoryView());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Update user recipes
        inflateCategoryList();
        inflateUserRecipesCollection();
    }

    /**
     * Gets the recipe item previews for a given list of recipe items
     * @param recipeItems
     * @return the first 5 recipe items as an ArrayList
     */
    private ArrayList<RecipeItem> getRecipeItemPreviews(List<RecipeItem> recipeItems) {
        int maxItems = Math.min(5, recipeItems.size());
        List<RecipeItem> itemsToPreview = recipeItems.subList(0, maxItems);

        return new ArrayList<>(itemsToPreview);
    }

    /**
     * Sets up the Recipe Collection Preview for the User Recipes section.
     */
    private void inflateUserRecipesCollection() {
        RecipeCollectionPreview collectionMyRecipes = view.findViewById(R.id.collectionMyRecipes);
        collectionMyRecipes.setTitle("My Recipes");

        // Get user id
        SharedPreferences prefs = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        // Fetch user recipes from API
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<RecipeItem>> call = apiInterface.getRecipesForUser(userId);
        APICaller.enqueue(call, recipes -> {
            // On successful fetch result, display up to the first 5 recipes
            ArrayList<RecipeItem> recipeItemsToPreview = getRecipeItemPreviews(recipes);
            collectionMyRecipes.setRecipes(recipeItemsToPreview);

            collectionMyRecipes.setSeeAllClickListener(v -> ((MainActivity) getActivity()).switchToRecipesView());
        });
    }

    /**
     * Sets up the Recipe Collection Previews for a Community Recipes section.
     */
    private void inflateCommunityRecipeCollection(int viewId, String title) {
        RecipeCollectionPreview collectionCommunityRecipes = view.findViewById(viewId);
        collectionCommunityRecipes.setTitle(title);

        // Fetch community recipes from API
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<RecipeItem>> call = apiInterface.getListCommunityRecipesFromCategory(title);
        APICaller.enqueue(call, recipes -> {
            // Fix data (collection and preview items)
            RecipeCollection collection = new RecipeCollection(title, new ArrayList<>(recipes));
            ArrayList<RecipeItem> previewItems = getRecipeItemPreviews(recipes);

            // Fill collection preview with data
            collectionCommunityRecipes.setRecipes(previewItems);
            collectionCommunityRecipes.setSeeAllClickListener(v -> ((MainActivity) getActivity()).switchToCollectionView(collection));
        });
    }

    private void inflateCategoryList() {
        RecyclerView rvCategories = view.findViewById(R.id.rvCategories);

        // Get user id
        SharedPreferences prefs = getContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        // Fetch categories from API
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Category>> call = apiInterface.getCategoriesForUser(userId);

        APICaller.enqueue(call, categories -> {
            RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> rvAdapter = new CategoryListAdapter(
                    new ArrayList<>(categories));
            rvCategories.setAdapter(rvAdapter);
        });
    }
}
