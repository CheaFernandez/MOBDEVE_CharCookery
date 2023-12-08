package com.mobdeve.s17.charcookery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mobdeve.s17.charcookery.fragments.CollectionFragment;
import com.mobdeve.s17.charcookery.fragments.FavoritesFragment;
import com.mobdeve.s17.charcookery.fragments.MainFragment;
import com.mobdeve.s17.charcookery.fragments.RecipesFragment;
import com.mobdeve.s17.charcookery.fragments.UserProfileFragment;
import com.mobdeve.s17.charcookery.models.RecipeCollection;

public class MainActivity extends AppCompatActivity {
    private Fragment mainFragment;
    private Fragment recipesFragment;
    private Fragment favoritesFragment;
    private Fragment userProfileFragment;

    private MenuManager menuManager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuManager = new MenuManager();

        // Setup initial fragment
        switchToMainView();
    }

    private void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentViewMain, fragment);
        fragmentTransaction.addToBackStack(null); // Optional: add to back stack for fragment navigation
        fragmentTransaction.commit();
    }

    public void switchToMainView() {
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        setFragment(mainFragment);
    }

    public void switchToRecipesView() {
        if (recipesFragment == null) {
            recipesFragment = new RecipesFragment();
        }
        setFragment(recipesFragment);
    }

    public void switchToFavoritesView() {
        if (favoritesFragment == null) {
            favoritesFragment = new FavoritesFragment();
        }
        setFragment(favoritesFragment);
    }

    public void switchToCollectionView(RecipeCollection collection) {
        Fragment collectionFragment = new CollectionFragment(collection);
        setFragment(collectionFragment);
    }

    public void switchToUserProfileView() {
        if (userProfileFragment == null) {
            userProfileFragment = new UserProfileFragment();
        }
        setFragment(userProfileFragment);
    }

    public void gotoAddCategoryView(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }

    public void updateMenuBar() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentViewMain);

        if (currentFragment != null) {
            switch (currentFragment.getClass().getSimpleName()) {
                case "MainFragment":        menuManager.setActiveTab(Page.HOME); break;
                case "RecipesFragment":
                case "CollectionFragment":  menuManager.setActiveTab(Page.RECIPES); break;
                case "FavoritesFragment":   menuManager.setActiveTab(Page.FAVORITES); break;
                case "UserProfileFragment": menuManager.setActiveTab(Page.USER_PROFILE); break;
            }
        }
    }

    public enum Page {
        HOME,
        RECIPES,
        FAVORITES,
        USER_PROFILE
    }

    public class MenuManager {
        private final ImageView tabHome;
        private final ImageView tabRecipes;
        private final ImageView tabFavorites;
        private final ImageView tabUserProfile;

        public MenuManager() {
            tabHome = findViewById(R.id.menuHome);
            tabHome.setOnClickListener(getTabClickListener(Page.HOME));

            tabRecipes = findViewById(R.id.menuRecipes);
            tabRecipes.setOnClickListener(getTabClickListener(Page.RECIPES));

            tabFavorites = findViewById(R.id.menuFavorites);
            tabFavorites.setOnClickListener(getTabClickListener(Page.FAVORITES));

            tabUserProfile = findViewById(R.id.menuUser);
            tabUserProfile.setOnClickListener(getTabClickListener(Page.USER_PROFILE));
        }

        private View.OnClickListener getTabClickListener(Page page) {
            return v -> {
                switch (page) {
                    case HOME:          switchToMainView(); return;
                    case RECIPES:       switchToRecipesView(); return;
                    case FAVORITES:     switchToFavoritesView(); return;
                    case USER_PROFILE:  switchToUserProfileView(); return;
                    default:            return;
                }
            };
        }

        public void setActiveTab(Page page) {
            // Reset tab images
            tabHome.setImageResource(R.drawable.home_outline);
            tabRecipes.setImageResource(R.drawable.book_outline);
            tabFavorites.setImageResource(R.drawable.heart_outline);
            tabUserProfile.setImageResource(R.drawable.user_outline);

            switch (page) {
                case HOME:          tabHome.setImageResource(R.drawable.home_filled); break;
                case RECIPES:       tabRecipes.setImageResource(R.drawable.book_filled); break;
                case FAVORITES:     tabFavorites.setImageResource(R.drawable.heart_filled); break;
                case USER_PROFILE:  tabUserProfile.setImageResource(R.drawable.user_filled); break;
            }
        }
    }
}