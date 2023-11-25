package com.mobdeve.s17.charcookery.components;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s17.charcookery.CookingModeActivity;
import com.mobdeve.s17.charcookery.R;

public abstract class BaseRecipeActivity extends AppCompatActivity {

    Button recipeLikeBtn;
    ImageButton cookingModeButton;
    boolean isLiked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        this.recipeLikeBtn = findViewById(R.id.recipeLikeBtn);
        this.cookingModeButton = findViewById(R.id.cookingModeButton);

        recipeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLiked = !isLiked;

                // Update the foreground drawable based on the like state
                if (isLiked) {
                    recipeLikeBtn.setForeground(getDrawable(R.drawable.heart_filled));
                } else {
                    recipeLikeBtn.setForeground(getDrawable(R.drawable.heart_outline));
                }
                // TODO: Update the database to reflect the favorite state
            }
        });

        this.cookingModeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CookingModeActivity.class);
            startActivity(intent);
        });


    }

    public void gotoPreviousPage(View v) {
        super.onBackPressed();
        finish();
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();
}
