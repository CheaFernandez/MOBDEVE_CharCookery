package com.mobdeve.s17.charcookery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipe_image_view = findViewById(R.id.recipe_image_view);
        uploadImageBtn = findViewById(R.id.upload_image_btn);

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an array to store the choices
                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipeActivity.this);
                builder.setTitle("Choose an option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (ContextCompat.checkSelfPermission(AddRecipeActivity.this, android.Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Permission is not granted
                            // Ask for permission
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

        categorySpinner = findViewById(R.id.spinner_category_field);

        String[] items = {"Breakfast", "Lunch", "Dinner", "Snack", "Dessert"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Add intents for the text fields and button for add recipe
        edit_recipe_name_field = findViewById(R.id.edit_recipe_name_field);
        edit_estimated_time_field = findViewById(R.id.edit_estimated_time_field);
        edit_ingredients_field = findViewById(R.id.edit_ingredients_field);
        edit_instructions_field = findViewById(R.id.edit_instructions_field);
        edit_notes_field = findViewById(R.id.edit_notes_field);

        // TODO: Fix EDIT TEXT FIELDS for DB update


        // Buttons
        recipe_addrecipe_btn = findViewById(R.id.recipe_addrecipe_btn);
        recipe_addrecipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add Recipe to Database
            }
        });
        recipe_cancel_btn = findViewById(R.id.recipe_cancel_btn);
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
            recipe_image_view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            recipe_image_view.requestLayout(); // Request a layout update
        } else if (requestCode == IMAGE_CAPTURE_REQ && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            recipe_image_view.setImageBitmap(imageBitmap);
            recipe_image_view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            recipe_image_view.requestLayout(); // Request a layout update
        }
    }

    public void gotoPreviousPage(View v) {
        super.onBackPressed();
        finish();
    }
}
