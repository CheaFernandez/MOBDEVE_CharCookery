package com.mobdeve.s17.charcookery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.api.models.CreateAccountBody;
import com.mobdeve.s17.charcookery.models.UserProfileUpdateRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText editNameEditText;
    private EditText editTextDiet;
    private ImageView profileImageView;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private APIInterface apiInterface;
    private CreateAccountBody updateUserProfileRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        editNameEditText = findViewById(R.id.loginEmailEt);
        editTextDiet = findViewById(R.id.editTextDiet);
        profileImageView = findViewById(R.id.profile_pic);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        editTextDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on editTextDiet
                openImagePicker();
            }
        });

        editNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on editNameEditText
                editName();
            }
        });

        Button confirmBtn = findViewById(R.id.Confirm);
        Button discardBtn = findViewById(R.id.Discard);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save changes (you need to implement this)
                // Get the updated name, photo URL, and dietary restrictions
                String newName = editNameEditText.getText().toString();
                String newPhotoUrl = ""; // Add your logic to get the updated photo URL
                String newDietaryRestrictions = ""; // Add your logic to get the updated dietary restrictions

                // Call the appropriate updateUserProfile method with the gathered information
                updateUserProfile(newName, newPhotoUrl, newDietaryRestrictions);

                // Show a toast message indicating that changes are saved
                Toast.makeText(EditUserProfileActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();

                // Navigate back to UserProfileActivity
                onBackPressed();
            }
        });

        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Discard changes and navigate back to UserProfileActivity
                onBackPressed();
            }
        });
    }

    private void openImagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");

        CharSequence[] options = {"Take Photo", "Choose from Gallery"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Take Photo
                        openCamera();
                        break;
                    case 1:
                        // Choose from Gallery
                        openGallery();
                        break;
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        // Intent to open the camera for capturing an image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private void openGallery() {
        // Intent to open the gallery for selecting an image
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void editName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Name");

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(editNameEditText.getText().toString()); // Set the current name as the default text
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                editNameEditText.setText(newName);

                // Now, you can make the API call to update the user's name
                updateUserProfile(newName, null, null);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateUserProfile(String newName, String newPhotoUrl, String newDietaryRestrictions) {
        // Get the user ID from SharedPreferences or wherever it's stored
        String userId = getUserId();

        // Gather the updated information from your UI components
        String updatedName = editNameEditText.getText().toString();
        String updatedPhotoUrl = ""; // Add your logic to get the updated photo URL
        String updatedDietaryRestrictions = ""; // Add your logic to get the updated dietary restrictions

        // Create a UserProfileUpdateRequest with the updated information
        UserProfileUpdateRequest updateRequest = new UserProfileUpdateRequest(updatedName, updatedPhotoUrl, updatedDietaryRestrictions);

        // Make the API call to update the user's profile
        Call<AccessTokenResponse> call = apiInterface.updateProfile(userId, updateRequest);

        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.isSuccessful()) {
                    // Handle a successful response
                    Toast.makeText(EditUserProfileActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle an unsuccessful response
                    Toast.makeText(EditUserProfileActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(EditUserProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                // Handle the result from the gallery
                Uri selectedImageUri = data.getData();
                // Update the profile picture ImageView with the selected image
                profileImageView.setImageURI(selectedImageUri);
            }
        }
    }

    // Override onBackPressed to handle the back button
    @Override
    public void onBackPressed() {
        // Additional logic can be added here before finishing the activity
        super.onBackPressed();
    }

    private String getUserId() {
        SharedPreferences prefs = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        return prefs.getString(Constants.SP_USER_ID, null);
    }


}
