package com.mobdeve.s17.charcookery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.models.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {
    private ArrayList<Category> categories;

    public static class CategoryListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public CategoryListViewHolder(View v) {
            super(v);

            tvTitle = v.findViewById(R.id.categoryTitle);
        }
    }

    public CategoryListAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        return new CategoryListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.tvTitle.setText(currentCategory.getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
