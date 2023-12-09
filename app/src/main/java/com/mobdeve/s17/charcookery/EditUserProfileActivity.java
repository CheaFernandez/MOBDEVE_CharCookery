package com.mobdeve.s17.charcookery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText editTextDiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        editTextDiet = findViewById(R.id.editTextDiet);

        // Retrieve existing user data and populate the EditText fields (you need to implement this)

        Button confirmBtn = findViewById(R.id.Confirm);
        Button discardBtn = findViewById(R.id.Discard);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save changes (you need to implement this)
                saveChanges();

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

    private void saveChanges() {
        // Implement the logic to save changes to the user's profile
        // For example, update the user's information in a database
    }

    // Override onBackPressed to handle the back button
    @Override
    public void onBackPressed() {
        // Additional logic can be added here before finishing the activity
        super.onBackPressed();
    }
}

