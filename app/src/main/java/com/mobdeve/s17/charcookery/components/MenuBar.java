package com.mobdeve.s17.charcookery.components;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.mobdeve.s17.charcookery.CollectionActivity;
import com.mobdeve.s17.charcookery.FavoritesActivity;
import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.RecipesActivity;

public class MenuBar extends LinearLayout {

    public enum Page {
        HOME,
        RECIPES,
        FAVORITES,
        USER_PROFILE
    }

    private Page activePage;

    private ImageView tabHome;
    private ImageView tabRecipes;
    private ImageView tabFavorites;
    private ImageView tabUserProfile;

    public MenuBar(Context context) {
        super(context);
        init(context);
    }

    public MenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        setActiveTab(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_menu, this, true);

        tabHome = findViewById(R.id.menuHome);
        tabRecipes = findViewById(R.id.menuRecipes);
        tabFavorites = findViewById(R.id.menuFavorites);
        tabUserProfile = findViewById(R.id.menuUser);

        // Setup onclick activity for all tabs
        tabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        tabRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(context, RecipesActivity.class);
                 context.startActivity(intent);
            }
        });
        tabFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(context, FavoritesActivity.class);
                 context.startActivity(intent);
            }
        });
        tabUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add intent to move to User Profile page
                // Intent intent = new Intent(context, ADD_ACTIVITY_HERE.class);
                // context.startActivity(intent);
            }
        });
    }


    private void setActiveTab(Context context, AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MenuBar);
        try {
            int menuTypeValue = a.getInt(R.styleable.MenuBar_activePage, 0);
            activePage = Page.values()[menuTypeValue];
        } finally {
            a.recycle();
        }


        switch (activePage) {
            case HOME:
                tabHome.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_filled));
                tabHome.setOnClickListener(null);
                break;
            case RECIPES:
                tabRecipes.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.book_filled));
                tabRecipes.setOnClickListener(null);
                break;
            case FAVORITES:
                tabFavorites.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_filled_dark));
                tabFavorites.setOnClickListener(null);
                break;
            case USER_PROFILE:
                tabUserProfile.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_filled));
                tabUserProfile.setOnClickListener(null);
                break;
        }
    }
}
