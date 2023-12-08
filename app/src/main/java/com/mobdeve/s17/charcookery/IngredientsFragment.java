package com.mobdeve.s17.charcookery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    private final String[] ingredients;

    public IngredientsFragment(String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_ingredients, container, false);

        TextView tvIngredients = view.findViewById(R.id.tvRecipeIngredients);
        StringBuilder stringBuilder = new StringBuilder();

        for (String ingredient: ingredients) {
            stringBuilder.append("•  ").append(ingredient).append("\n");
        }

        tvIngredients.setText(stringBuilder);

        return view;
    }
}
