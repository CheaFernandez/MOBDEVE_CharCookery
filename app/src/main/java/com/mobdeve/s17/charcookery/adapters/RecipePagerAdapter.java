package com.mobdeve.s17.charcookery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mobdeve.s17.charcookery.fragments.TextListFragment;
import com.mobdeve.s17.charcookery.NotesFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter {

    private String[] ingredients;
    private String[] instructions;
    private String notes;

    public RecipePagerAdapter(FragmentManager fm, String[] ingredients,
                              String[] instructions, String notes) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.ingredients = ingredients;
        this.instructions = instructions;
        this.notes = notes;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TextListFragment(ingredients);
            case 1: return new TextListFragment(instructions, true);
            case 2: return new NotesFragment();
            default: return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3; // Number of tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Ingredients";
            case 1: return "Instructions";
            case 2: return "Notes";
            default: return null;
        }
    }
}
