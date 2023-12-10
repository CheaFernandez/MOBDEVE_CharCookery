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

public class FavoritesFragment extends Fragment {
    private View view;

    public FavoritesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        setupView();

        return view;
    }

    private void setupView() {
        // Get user id
        SharedPreferences prefs = getContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        // Fetch favorite recipes from API
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Map<String, Object> queryParameters = new HashMap<>();
        queryParameters.put("is_favorite", true);

        Call<List<RecipeItem>> call = apiInterface.getRecipesForUserWithFilters(userId, queryParameters);
        APICaller.enqueue(call, recipes -> {
            // On successful fetch result, display up to the first 5 recipes
            RecyclerView rvRecipes = view.findViewById(R.id.rvFavoritesGrid);
            RecyclerView.Adapter<CollectionRecipesAdapter.CollectionViewHolder> rvAdapter = new CollectionRecipesAdapter(new ArrayList<>(recipes));
            rvRecipes.setAdapter(rvAdapter);
        });
    }

}
