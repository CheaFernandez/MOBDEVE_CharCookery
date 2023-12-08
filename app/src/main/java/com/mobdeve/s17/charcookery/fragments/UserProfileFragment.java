package com.mobdeve.s17.charcookery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;

public class UserProfileFragment extends Fragment {
    private View view;

    public UserProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        return view;
    }
}
