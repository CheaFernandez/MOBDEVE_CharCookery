package com.mobdeve.s17.charcookery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mobdeve.s17.charcookery.adapters.RecipePagerAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.UpdateRecipeFavoriteBody;
import com.mobdeve.s17.charcookery.api.models.UpdateRecipeNotesBody;
import com.mobdeve.s17.charcookery.components.BaseRecipeActivity;
import com.mobdeve.s17.charcookery.fragments.CookingModeTimerFragment;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends BaseRecipeActivity {

    private ImageButton cookingModeButton;
    private RecipeItem recipe;
    private Button recipeDeleteBtn;
    private boolean isCookingModeFragmentAdded = false;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_recipe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        fetchRecipeFromIntent();
        inflatePageWithRecipe();
        setupTabs();
        setupDelete();
    }

    private void setupDelete(){
        recipeDeleteBtn = findViewById(R.id.recipeDeleteBtn);
        recipeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeActivity.this);
                builder.setTitle("Delete Recipe");
                builder.setMessage("Are you sure you want to delete this recipe?");

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click, if needed
                        deleteRecipe();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void deleteRecipe(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Void> call = apiInterface.deleteRecipeById(recipe.getId());

        APICaller.enqueue(call, new APICaller.APICallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(RecipeActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(RecipeActivity.this, "Failed to delete recipe", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    private void fetchRecipeFromIntent() {
        Intent intent = getIntent();
        recipe = (RecipeItem) intent.getSerializableExtra("recipe");
    }

    private void inflatePageWithRecipe() {
        ImageView ivImage = findViewById(R.id.ivRecipe);
        Picasso.get().load(recipe.getCoverImage()).into(ivImage);

        TextView tvTitle = findViewById(R.id.tvRecipeTitle);
        tvTitle.setText(recipe.getTitle());

        TextView tvCategory = findViewById(R.id.recipeCategory);
        tvCategory.setText(recipe.getCategory());

        TextView tvDuration = findViewById(R.id.recipeDuration);
        String durationString = getString(R.string.duration_format, recipe.getDurationMinutes());
        tvDuration.setText(durationString);

        Drawable dwHeartFilled = ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_filled);
        Drawable dwHeartOutline = ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_outline);

        Button btnFavorite = findViewById(R.id.recipeLikeBtn);
        btnFavorite.setForeground(recipe.checkFavorite() ? dwHeartFilled : dwHeartOutline);

        btnFavorite.setOnClickListener(v -> {
            // Update frontend
            recipe.toggleFavorite();
            btnFavorite.setForeground(recipe.checkFavorite() ? dwHeartFilled : dwHeartOutline);

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            UpdateRecipeFavoriteBody updateBody = new UpdateRecipeFavoriteBody(recipe.checkFavorite());
            Call<RecipeItem> call = apiInterface.updateRecipeFavoriteStatus(recipe.getId(), updateBody);

            call.enqueue(new Callback<RecipeItem>() {
                @Override
                public void onResponse(Call<RecipeItem> call, Response<RecipeItem> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Updated recipe favorite status", Toast.LENGTH_SHORT).show();
                    } else {
                        // Reset favorite status
                        recipe.toggleFavorite();
                        btnFavorite.setForeground(recipe.checkFavorite() ? dwHeartFilled : dwHeartOutline);
                        Toast.makeText(getApplicationContext(), "Unable to update recipe favorite status", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RecipeItem> call, Throwable t) {
                    // Reset favorite status
                    recipe.toggleFavorite();
                    btnFavorite.setForeground(recipe.checkFavorite() ? dwHeartFilled : dwHeartOutline);
                    Toast.makeText(getApplicationContext(), "Unable to update recipe favorite status", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setupTabs() {
        TabLayout recipeTabLayout = findViewById(R.id.recipeTabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Set up the ViewPager with the adapter
        RecipePagerAdapter recipePagerAdapter = new RecipePagerAdapter(getSupportFragmentManager(), recipe.getIngredients(), recipe.getInstructions(), recipe.getNotes());
        viewPager.setAdapter(recipePagerAdapter);

        // Link the TabLayout to the ViewPager
        recipeTabLayout.setupWithViewPager(viewPager);

        cookingModeButton = findViewById(R.id.cookingModeButton);
        cookingModeButton.setOnClickListener(v -> { goToCookingModeFragment(); });
    }

    private void goToCookingModeFragment() {
        if (!isCookingModeFragmentAdded) {
            CookingModeTimerFragment cookingModeTimerFragment = new CookingModeTimerFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, cookingModeTimerFragment)
                    .addToBackStack(null)
                    .commit();
            isCookingModeFragmentAdded = true;
            cookingModeButton.setImageResource(R.drawable.stop_cm_btn_txt);
        } else {
            // If the fragment is already added, remove it
            getSupportFragmentManager().popBackStack();
            isCookingModeFragmentAdded = false;
            cookingModeButton.setImageResource(R.drawable.start_cm_btn_txt);
        }
    }
    public void showEditNotesDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Notes");

        // Inflate the dialog layout
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_notes, null);

        // Find the EditText within the AlertDialog view
        final EditText input = viewInflated.findViewById(R.id.etEditNotes);
        input.setText(recipe.getNotes());

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the OK button click, if needed
                String editedNotes = input.getText().toString();
                updateNotes(editedNotes);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Set the inflated view to the builder
        builder.setView(viewInflated);

        // Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        // Show the AlertDialog
        alertDialog.show();
    }

    private void updateNotes(String editedNotes) {
        // Update the RecipeItem object and UI with the edited notes
        recipe.setNotes(editedNotes);

        // Update the displayed notes in the notes tab
        TextView notesText = findViewById(R.id.notes_text);
        notesText.setText(editedNotes);

        // Create UpdateRecipeNotesBody instance
        UpdateRecipeNotesBody updateRecipeNotesBody = new UpdateRecipeNotesBody(editedNotes);

        // Call the API to update notes
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RecipeItem> call = apiInterface.updateRecipeNotes(recipe.getId(), updateRecipeNotesBody);

        // Update the recipe object with the new notes on API success
        APICaller.enqueue(call, new APICaller.APICallback<RecipeItem>() {
            @Override
            public void onSuccess(RecipeItem result) {
                recipe = result;
                Toast.makeText(RecipeActivity.this, "Notes updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                // Revert the local changes if API call fails
                Toast.makeText(RecipeActivity.this, "Failed to update notes", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

                // Revert the RecipeItem object and UI to the original state
                notesText.setText(recipe.getNotes());
            }
        });
    }




}

