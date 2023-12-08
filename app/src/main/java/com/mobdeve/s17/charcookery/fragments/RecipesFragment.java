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

        return view;
    }

    private void setupView() {
        ArrayList<RecipeItem> recipes = Mocker.generateRecipeItems(3, 10); // TODO: Replace with API call

        rvRecipes = view.findViewById(R.id.rvRecipesGrid);
        rvAdapter = new CollectionRecipesAdapter(recipes);
        rvRecipes.setAdapter(rvAdapter);
    }
}