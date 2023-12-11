package com.mobdeve.s17.charcookery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mobdeve.s17.charcookery.api.APICaller;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.AccessTokenResponse;
import com.mobdeve.s17.charcookery.api.models.CreateAccountBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    AppCompatButton register_btn;
    AppCompatButton cancel_btn;

    EditText passwordEt;
    EditText emailEt;
    TextView tvError;

    APIInterface apiInterface;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        emailEt = findViewById(R.id.registerEmailEt);
        passwordEt = findViewById(R.id.registerPasswordEt);
        tvError = findViewById(R.id.tvError);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        context = this;

        register_btn.setOnClickListener(v -> {
            String email = emailEt.getText().toString();
            String password =  passwordEt.getText().toString();

            CreateAccountBody credentials = new CreateAccountBody(email, password);

            Call<AccessTokenResponse> call = apiInterface.createAccount(credentials);

            call.enqueue(new Callback<AccessTokenResponse>() {
                @Override
                public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                    if (response.isSuccessful()) {
                        tvError.setText("");

                        AccessTokenResponse firebaseUser = response.body();
                        String uid = firebaseUser.getUid();
                        saveUserId(context, uid);

                        // start activity intent to Main
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        tvError.setText("Unable to create account.");
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                    tvError.setText("Unable to create account.");
                }
            });
        });

        cancel_btn.setOnClickListener(v -> {
            finish();
        });

    }

    private static void saveUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SP_USER_ID, userId);
        editor.apply();
    }
}