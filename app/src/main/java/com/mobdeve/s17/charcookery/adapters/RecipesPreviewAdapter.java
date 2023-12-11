package com.mobdeve.s17.charcookery.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.mobdeve.s17.charcookery.RecipeActivity;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesPreviewAdapter extends RecyclerView.Adapter<RecipesPreviewAdapter.RecipePreviewHolder> {
    private ImageView recipePreviewImg;
    private TextView recipePreviewTitle;

    private final ArrayList<RecipeItem> recipeItems;
    private static final int MAX_ITEMS = 4;

    public static class RecipePreviewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvCategory;
        public Button btnFavorite;

        public RecipePreviewHolder(View v) {
            super(v);

            ivThumbnail = v.findViewById(R.id.recipePreviewImg);
            tvTitle = v.findViewById(R.id.recipePreviewTitle);
            tvCategory = v.findViewById(R.id.recipePreviewCategory);
            btnFavorite = v.findViewById(R.id.recipePreviewLikeBtn);
        }
    }

    public RecipesPreviewAdapter(ArrayList<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }

    @NonNull
    @Override
    public RecipePreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe, parent, false);

        recipePreviewImg = v.findViewById(R.id.recipePreviewImg);
        recipePreviewTitle = v.findViewById(R.id.recipePreviewTitle);

        return new RecipePreviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipePreviewHolder holder, int position) {
        RecipeItem currentItem = recipeItems.get(position);

        if (currentItem == null) return;

        Context context = holder.itemView.getContext();

        Picasso.get().load(currentItem.getCoverImage()).into(holder.ivThumbnail);
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvCategory.setText(currentItem.getCategory());

        View.OnClickListener recipePreviewListener = v -> {
            Intent intent = new Intent(v.getContext(), RecipeActivity.class);
            intent.putExtra("recipe", currentItem);
            v.getContext().startActivity(intent);
        };

        recipePreviewImg.setOnClickListener(recipePreviewListener);
        recipePreviewTitle.setOnClickListener(recipePreviewListener);


        Drawable dwHeartFilled = ContextCompat.getDrawable(context, R.drawable.heart_filled);
        Drawable dwHeartOutline = ContextCompat.getDrawable(context, R.drawable.heart_outline);

        if (currentItem.checkFavorite()) {
            holder.btnFavorite.setForeground(dwHeartFilled);
        } else {
            holder.btnFavorite.setForeground(dwHeartOutline);
        }

        holder.btnFavorite.setOnClickListener(v -> {
            currentItem.toggleFavorite();

            if (currentItem.checkFavorite()) {
                holder.btnFavorite.setForeground(dwHeartFilled);
            } else {
                holder.btnFavorite.setForeground(dwHeartOutline);
            }
        });
        return;
    }

    public void setRecipes(List<RecipeItem> newRecipes) {
        recipeItems.clear();
        recipeItems.addAll(newRecipes);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Math.min(recipeItems.size(), MAX_ITEMS);
    }
}
