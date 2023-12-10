package com.mobdeve.s17.charcookery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mobdeve.s17.charcookery.adapters.RecipePagerAdapter;
import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.UpdateRecipeNotesBody;
import com.mobdeve.s17.charcookery.components.BaseRecipeActivity;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends BaseRecipeActivity {

    ImageButton cookingModeButton;
    RecipeItem recipe;


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
        cookingModeButton.setOnClickListener(v -> { goToCookingModeActivity(); });
    }

    private void goToCookingModeActivity() {
        Intent intent = new Intent(RecipeActivity.this, CookingModeActivity.class);
        startActivity(intent);
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
        TextView notes_text = findViewById(R.id.notes_text);
        notes_text.setText(editedNotes);

        // Create UpdateRecipeNotesBody instance
        UpdateRecipeNotesBody updateRecipeNotesBody = new UpdateRecipeNotesBody(editedNotes);

        // Call the API to update notes
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RecipeItem> call = apiInterface.updateRecipeNotes(recipe.getId(), updateRecipeNotesBody);

        APICaller.enqueue(call, new APICaller.APICallback<RecipeItem>() {
            @Override
            public void onSuccess(RecipeItem result) {
                recipe = result;
                Toast.makeText(RecipeActivity.this, "Notes updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(RecipeActivity.this, "Failed to update notes", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }



}

