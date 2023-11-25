package com.mobdeve.s17.charcookery.components;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s17.charcookery.R;

public abstract class BaseRecipeActivity extends AppCompatActivity {

    Button recipeLikeBtn;
    boolean isLiked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        this.recipeLikeBtn = findViewById(R.id.recipeLikeBtn);

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
                // TODO: Update the database to reflect the like state
            }
        });
    }

    public void gotoPreviousPage(View v) {
        super.onBackPressed();
        finish();
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();
}
