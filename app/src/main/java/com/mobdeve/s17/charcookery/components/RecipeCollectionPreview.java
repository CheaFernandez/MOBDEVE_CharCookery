package com.mobdeve.s17.charcookery.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.adapters.RecipesPreviewAdapter;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

public class RecipeCollectionPreview extends LinearLayout {
    private TextView tvTitle;
    private Button btnSeeAll;
    private RecyclerView rvRecipes;
    private RecyclerView.Adapter rvAdapter;

    public RecipeCollectionPreview(Context context) {
        super(context);
        init();
    }

    public RecipeCollectionPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_collection_preview, this);

        tvTitle = findViewById(R.id.collectionPreviewTitle);
        btnSeeAll = findViewById(R.id.btnSeeAll);
        rvRecipes = findViewById(R.id.recyclerViewCollectionPreview);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setRecipes(ArrayList<RecipeItem> recipeList) {
        rvAdapter = new RecipesPreviewAdapter(recipeList);
        rvRecipes.setAdapter(rvAdapter);
    }

    public void setSeeAllClickListener(View.OnClickListener listener) {
        btnSeeAll.setOnClickListener(listener);
    }
}

