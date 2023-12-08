package com.mobdeve.s17.charcookery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.adapters.CollectionRecipesAdapter;
import com.mobdeve.s17.charcookery.models.Mocker;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

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
        ArrayList<RecipeItem> recipes = Mocker.generateRecipeItems(3, 7, 1.0f);

        RecyclerView rvRecipes = view.findViewById(R.id.rvFavoritesGrid);
        RecyclerView.Adapter<CollectionRecipesAdapter.CollectionViewHolder> rvAdapter = new CollectionRecipesAdapter(recipes);
        rvRecipes.setAdapter(rvAdapter);
    }

}
