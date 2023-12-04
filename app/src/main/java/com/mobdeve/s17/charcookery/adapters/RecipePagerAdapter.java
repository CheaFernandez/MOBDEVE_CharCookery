package com.mobdeve.s17.charcookery.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mobdeve.s17.charcookery.IngredientsFragment;
import com.mobdeve.s17.charcookery.InstructionsFragment;
import com.mobdeve.s17.charcookery.NotesFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter {

    public RecipePagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IngredientsFragment();
            case 1:
                return new InstructionsFragment();
            case 2:
                return new NotesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3; // Number of tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ingredients";
            case 1:
                return "Instructions";
            case 2:
                return "Notes";
            default:
                return null;
        }
    }
}
