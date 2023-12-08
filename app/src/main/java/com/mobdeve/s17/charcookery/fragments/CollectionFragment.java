package com.mobdeve.s17.charcookery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.adapters.CollectionRecipesAdapter;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

public class CollectionFragment extends Fragment {

    private View view;
    private final RecipeCollection recipeCollection;

    public CollectionFragment(RecipeCollection recipeCollection) {
        this.recipeCollection = recipeCollection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_collection, container, false);

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        setupView();

        return this.view;
    }

    private void setupView() {
        TextView tvTitle = view.findViewById(R.id.collectionTitle);
        RecyclerView rvRecipes = view.findViewById(R.id.rvCollectionGrid);

        if (recipeCollection != null) {
            String title = recipeCollection.getTitle();
            ArrayList<RecipeItem> recipes = recipeCollection.getRecipes();

            tvTitle.setText(title);
            RecyclerView.Adapter<CollectionRecipesAdapter.CollectionViewHolder> rvAdapter = new CollectionRecipesAdapter(recipes);
            rvRecipes.setAdapter(rvAdapter);
        }
    }
}
