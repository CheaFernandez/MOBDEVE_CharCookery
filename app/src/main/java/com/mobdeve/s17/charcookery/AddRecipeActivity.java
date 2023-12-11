package com.mobdeve.s17.charcookery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.models.RecipeItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecipeActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;
    private static final int CAMERA_REQ_CODE = 1002;
    private static final int IMAGE_CAPTURE_REQ = 1003;
    ImageView recipe_image_view;
    EditText edit_recipe_name_field;
    EditText edit_estimated_time_field;
    EditText edit_ingredients_field;
    EditText edit_instructions_field;
    EditText edit_notes_field;
    AppCompatButton uploadImageBtn;
    AppCompatButton recipe_addrecipe_btn;
    AppCompatButton recipe_cancel_btn;
    Spinner categorySpinner;

    View view;

    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipe_image_view = findViewById(R.id.recipe_image_view);
        uploadImageBtn = findViewById(R.id.upload_image_btn);

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipeActivity.this);
                builder.setTitle("Choose an option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (ContextCompat.checkSelfPermission(AddRecipeActivity.this, android.Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQ_CODE);
                        } else {
                            if (options[item].equals("Take Photo")) {
                                // Choose the camera option
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQ); // Use IMAGE_CAPTURE_REQ here

                            } else if (options[item].equals("Choose from Gallery")) {
                                // Choose the gallery option
                                Intent iGallery = new Intent(Intent.ACTION_PICK);
                                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(iGallery, GALLERY_REQ_CODE);
                            } else if (options[item].equals("Cancel")) {
                                // Cancel the dialog
                                dialog.dismiss();
                            }
                        }
                    }
                });
                builder.show();
            }
        });

        // TODO: Update category spinner
        categorySpinner = findViewById(R.id.spinner_category_field);

        String[] items = {"Weekly Meal Plans", "Dinner Date Ideas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Add intents for the text fields and button for add recipe
        edit_recipe_name_field = findViewById(R.id.edit_recipe_name_field);
        edit_estimated_time_field = findViewById(R.id.max_time_editText);
        edit_ingredients_field = findViewById(R.id.edit_ingredients_contains_field);
        edit_instructions_field = findViewById(R.id.edit_instructions_field);
        edit_notes_field = findViewById(R.id.edit_notes_field);

        edit_ingredients_field.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edit_ingredients_field.setSingleLine(false);
        edit_ingredients_field.setMaxLines(Integer.MAX_VALUE);

        edit_instructions_field.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edit_instructions_field.setSingleLine(false);
        edit_instructions_field.setMaxLines(Integer.MAX_VALUE);

        edit_notes_field.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edit_notes_field.setSingleLine(false);
        edit_notes_field.setMaxLines(Integer.MAX_VALUE);

        // TODO: Fix EDIT TEXT FIELDS for DB update


        // Buttons
        recipe_addrecipe_btn = findViewById(R.id.filter_recipes_btn);
        recipe_addrecipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe(v);
            }
        });
        recipe_cancel_btn = findViewById(R.id.cancel_btn);
        recipe_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPreviousPage(v);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO: Fix Image scaling
        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null) {
            recipe_image_view.setImageURI(data.getData());
            recipe_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            recipe_image_view.requestLayout(); // Request a layout update
        } else if (requestCode == IMAGE_CAPTURE_REQ && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            recipe_image_view.setImageBitmap(imageBitmap);
            recipe_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            recipe_image_view.requestLayout(); // Request a layout update
        }
    }

    public void gotoPreviousPage(View v) {
        super.onBackPressed();
        finish();
    }

    private void uploadImage() {

    }

    private void uploadRecipe(View view) {
        String category = categorySpinner.getSelectedItem().toString();;
        String name = edit_recipe_name_field.getText().toString();
        String notes = edit_notes_field.getText().toString();

        String durationText = edit_estimated_time_field.getText().toString();
        int durationMinutes;
        try {
            durationMinutes = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            durationMinutes = 0; // Default value
        }

        String coverImage = "https://developers.elementor.com/docs/assets/img/elementor-placeholder-image.png";
        String[] instructions = edit_instructions_field.getText().toString().split("\\n");;
        String[] ingredients = edit_ingredients_field.getText().toString().split("\\n");;
        boolean isFavorite = false;

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.SP_USER_ID, null);

        RecipeItem recipeItem = new RecipeItem(category, name, notes, durationMinutes, coverImage, instructions, ingredients, isFavorite, userId);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RecipeItem> call = apiInterface.createRecipe(recipeItem);

        call.enqueue(new Callback<RecipeItem>() {
            @Override
            public void onResponse(Call<RecipeItem> call, Response<RecipeItem> response) {
                if (response.isSuccessful()) {
                    // Go back to recipes page
                    gotoPreviousPage(view);
                    Toast.makeText(AddRecipeActivity.this, "Added recipe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddRecipeActivity.this, "Unable to create recipe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecipeItem> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AddRecipeActivity.this, "Unable to create recipe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
