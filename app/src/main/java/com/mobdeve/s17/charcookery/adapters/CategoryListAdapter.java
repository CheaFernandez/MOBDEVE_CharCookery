package com.mobdeve.s17.charcookery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.models.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {
    private ArrayList<Category> categories;

    // Interface to handle click events
    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class CategoryListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Button categoryDeleteButton;

        public CategoryListViewHolder(View v, final OnItemClickListener listener) {
            super(v);

            tvTitle = v.findViewById(R.id.categoryTitle);
            categoryDeleteButton = v.findViewById(R.id.categoryDeleteBtn);

            // Set up the click listener for categoryDeleteButton
            categoryDeleteButton.setOnClickListener(v1 -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public CategoryListAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        return new CategoryListViewHolder(v, listener);
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
