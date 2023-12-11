package com.mobdeve.s17.charcookery.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.charcookery.Constants;
import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.models.Category;
import com.mobdeve.s17.charcookery.models.RecipeCollection;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        holder.tvTitle.setOnClickListener(v -> {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

            // Prepare filter
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("category", currentCategory.getTitle());

            // Get user id
            SharedPreferences prefs = v.getContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
            String userId = prefs.getString(Constants.SP_USER_ID, null);

            Call<List<RecipeItem>> call = apiInterface.getRecipesForUserWithFilters(userId, queryMap);

            call.enqueue(new Callback<List<RecipeItem>>() {
                @Override
                public void onResponse(Call<List<RecipeItem>> call, Response<List<RecipeItem>> response) {
                    if (response.isSuccessful()) {
                        List<RecipeItem> recipes = response.body();
                        RecipeCollection categoryCollection = new RecipeCollection(currentCategory.getTitle(), new ArrayList<>(recipes));
                        ((MainActivity) v.getContext()).switchToCollectionView(categoryCollection);
                    }
                }

                @Override
                public void onFailure(Call<List<RecipeItem>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }
}
