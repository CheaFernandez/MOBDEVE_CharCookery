package com.mobdeve.s17.charcookery;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.models.UserProfileUpdateRequest;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.Constants;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfileActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;
    private static final int CAMERA_REQ_CODE = 1002;
    private static final int IMAGE_CAPTURE_REQ = 1003;
    private ImageView profileImageView;
    private AppCompatButton btnUploadProfilePic;
    private Button btnBack;
    private EditText usernameEditTextView;
    private EditText editTextDiet;
    private AppCompatButton btnConfirmChanges;
    private AppCompatButton btnDiscardChanges;
    private APIInterface apiInterface;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        btnBack = findViewById(R.id.btnBack);

        profileImageView = findViewById(R.id.profile_pic);
        btnUploadProfilePic = findViewById(R.id.btnUploadProfilePic);

        usernameEditTextView = findViewById(R.id.usernameEditTextView);

        editTextDiet = findViewById(R.id.editTextDiet);

        btnConfirmChanges = findViewById(R.id.btnConfirmChanges);
        btnDiscardChanges = findViewById(R.id.btnDiscardChanges);

        btnUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });

        btnConfirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        btnDiscardChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discardChanges();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showImageSourceDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    requestCameraPermission();
                } else if (item == 1) {
                    openGallery();
                } else if (item == 2) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQ_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQ_CODE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQ);
    }

    private void showEditUsernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Username");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUsername = input.getText().toString();
                usernameEditTextView.setText(newUsername);
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

    private void saveChanges() {
        // Get the user ID from SharedPreferences or wherever it's stored
        String userId = getUserId();

        // Gather the updated information from your UI components
        String updatedName = usernameEditTextView.getText().toString();
        String updatedPhotoUrl = imageUri.toString();
        String updatedDietaryRestrictions = editTextDiet.getText().toString();

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

    private void discardChanges() {
        // Reset the UI components to their original values
        usernameEditTextView.setText("Username");
        profileImageView.setImageResource(R.drawable.user_filled);
        editTextDiet.setText("Dietary Restrictions");
        Toast.makeText(this, "Changes discarded", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null) {
            // Handle gallery result
            handleGalleryResult(data);
        } else if (requestCode == IMAGE_CAPTURE_REQ && resultCode == RESULT_OK && data != null) {
            // Handle camera result
            handleCameraResult(data);
        }
    }

    private void handleGalleryResult(Intent data) {
        imageUri = data.getData();
        profileImageView.setImageURI(imageUri);
        profileImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        profileImageView.requestLayout();
    }

    private void handleCameraResult(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // If you want to convert the Bitmap to a Uri, you can use a helper method
            imageUri = getImageUri(this, imageBitmap);
            profileImageView.setImageBitmap(imageBitmap);
            profileImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            profileImageView.requestLayout();
        }
    }

    // Helper method to convert Bitmap to Uri
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private String getUserId() {
        SharedPreferences prefs = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        return prefs.getString(Constants.SP_USER_ID, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
