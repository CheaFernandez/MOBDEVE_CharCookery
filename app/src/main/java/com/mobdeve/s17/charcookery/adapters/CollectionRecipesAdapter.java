package com.mobdeve.s17.charcookery.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CollectionRecipesAdapter extends RecyclerView.Adapter<CollectionRecipesAdapter.CollectionViewHolder> {
    private ArrayList<RecipeItem> recipeItems;
    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvCategory;
        public Button btnFavorite;
        public CollectionViewHolder(View v) {
            super(v);

            ivThumbnail = v.findViewById(R.id.recipePreviewImg);
            tvTitle = v.findViewById(R.id.recipePreviewTitle);
            tvCategory = v.findViewById(R.id.recipePreviewCategory);
            btnFavorite = v.findViewById(R.id.recipePreviewLikeBtn);
        }
    }

    public CollectionRecipesAdapter(ArrayList<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe_lg, parent, false);
        return new CollectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        RecipeItem currentItem = recipeItems.get(position);

        Picasso.get().load(currentItem.getCoverImage()).into(holder.ivThumbnail);
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvCategory.setText(currentItem.getCategory());

        Context context = holder.itemView.getContext();
        Drawable dwHeartFilled = ContextCompat.getDrawable(context, R.drawable.heart_filled);
        Drawable dwHeartOutline = ContextCompat.getDrawable(context, R.drawable.heart_outline);

        if (currentItem.checkFavorite()) {
            holder.btnFavorite.setForeground(dwHeartFilled);
        } else {
            holder.btnFavorite.setForeground(dwHeartOutline);
        }

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem.toggleFavorite();

                if (currentItem.checkFavorite()) {
                    holder.btnFavorite.setForeground(dwHeartFilled);
                } else {
                    holder.btnFavorite.setForeground(dwHeartOutline);
                }
            }
        });
    }

    public void updateData(List<RecipeItem> newRecipeItems) {
        this.recipeItems.clear();
        this.recipeItems.addAll(newRecipeItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }
}
