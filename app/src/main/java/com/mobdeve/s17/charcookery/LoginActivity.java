package com.mobdeve.s17.charcookery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton login_btn;
    AppCompatButton cancel_btn;
    AppCompatButton logout_btn;
    AppCompatButton editProfileBtn;

    EditText emailEditText;
    EditText passwordEditText;

    Context context;

    private APIInterface apiInterface;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.register_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        emailEditText = findViewById(R.id.loginEmailEt);
        passwordEditText = findViewById(R.id.loginPasswordEt);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        context = this;

        logout_btn = findViewById(R.id.sign_out);
        editProfileBtn = findViewById(R.id.edit_profile);

        logout_btn.setOnClickListener(v -> {
            // Call the signOut method to log out the user
            signOut();
        });
        // uncomment once done with edit profile
        editProfileBtn.setOnClickListener(v -> {
            // Call the editProfile method to navigate to the Edit User Profile activity
            editProfile();
        });

        login_btn.setOnClickListener(v -> {
            // TODO: check for login credentials
            String email = emailEditText.getText().toString();
            String password =  passwordEditText.getText().toString();

            Call<AccessTokenResponse> call = apiInterface.getAccessToken(
                    "password",
                    email,
                    password
            );

            call.enqueue(new Callback<AccessTokenResponse>() {
                @Override
                public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                    if (response.isSuccessful()) {
                        AccessTokenResponse firebaseUser = response.body();

                        String uid = firebaseUser.getUid();
                        saveUserId(context, uid);

                        // start activity intent to main
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Handle error
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                    // Handle failure
                }
            });
        });

        cancel_btn.setOnClickListener(v -> {
            // go back to prev page
            finish();
        });
    }
    private void signOut() {
        // Clear the stored user ID from SharedPreferences
        clearUserId(context);

        // Redirect to the login page or perform any other necessary actions
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back to the main activity using the back button
    }
    private void editProfile() {
        // Redirect to the Edit User Profile activity or perform any other necessary actions
        //Edit User Profile Activity is not created yet uncomment this when it is created
        Intent intent = new Intent(LoginActivity.this, EditUserProfileActivity.class);
        startActivity(intent);
    }

    private static void clearUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.SP_USER_ID);
        editor.apply();
    }
    private static void saveUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SP_USER_ID, userId);
        editor.apply();
    }
}
