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

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mobdeve.s17.charcookery.adapters.RecipePagerAdapter;
import com.mobdeve.s17.charcookery.components.BaseRecipeActivity;
import com.mobdeve.s17.charcookery.models.RecipeItem;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends BaseRecipeActivity {

    ImageButton cookingModeButton;
    RecipeItem recipe;
    ImageButton btnEditNotes;
    TextView tvNotes;


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

        btnEditNotes = findViewById(R.id.btnEditNotes);
        tvNotes = findViewById(R.id.tvNotes);

        btnEditNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditNotesDialog();
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
    private void showEditNotesDialog() {
        // Create an AlertDialog with an EditText for editing notes
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Notes");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_notes, null);
        final EditText input = viewInflated.findViewById(R.id.etEditNotes);
        input.setText(recipe.getNotes());

        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedNotes = input.getText().toString();
                updateNotes(editedNotes);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateNotes(String editedNotes) {
        // Update the RecipeItem object and UI with the edited notes
        recipe.setNotes(editedNotes);
        tvNotes.setText(editedNotes);
    }
}

