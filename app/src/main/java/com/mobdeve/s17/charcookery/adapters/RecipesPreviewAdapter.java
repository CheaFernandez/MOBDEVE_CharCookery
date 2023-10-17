package com.mobdeve.s17.charcookery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;

public class RecipesPreviewAdapter extends RecyclerView.Adapter<RecipesPreviewAdapter.RecipePreviewHolder> {
    private ArrayList<RecipeItem> recipeItems;
    public static class RecipePreviewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvCategory;
        public RecipePreviewHolder(View v) {
            super(v);

            ivThumbnail = v.findViewById(R.id.recipePreviewImg);
            tvTitle = v.findViewById(R.id.recipePreviewTitle);
            tvCategory = v.findViewById(R.id.recipePreviewCategory);
        }
    }

    public RecipesPreviewAdapter(ArrayList<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }

    @NonNull
    @Override
    public RecipePreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe, parent, false);
        RecipePreviewHolder viewHolder = new RecipePreviewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipePreviewHolder holder, int position) {
        RecipeItem currentItem = recipeItems.get(position);

        holder.ivThumbnail.setImageResource(currentItem.getImageResource());
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvCategory.setText(currentItem.getCategory());
    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }
}
