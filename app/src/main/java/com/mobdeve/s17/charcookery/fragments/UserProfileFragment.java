package com.mobdeve.s17.charcookery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;

import com.mobdeve.s17.charcookery.EditUserProfileActivity;
import com.mobdeve.s17.charcookery.HomeActivity;
import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;

public class UserProfileFragment extends Fragment {

    private AppCompatButton btnSignOut;
    private AppCompatButton btnEditProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        btnSignOut.setOnClickListener(v -> {
            Log.d("UserProfileFragment", "Sign out button clicked");
            signOut();
        });

        btnEditProfile.setOnClickListener(v -> {
            Log.d("UserProfileFragment", "Edit profile button clicked");
            editProfile();
        });

        return view;
    }

    private void signOut() {
        // TODO: Perform sign-out actions, such as clearing user session, preferences, etc.
        // Navigate to the home page
        Intent intent = new Intent(requireContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void editProfile() {
        // TODO: Implement logic to navigate to the edit profile page
        Intent intent = new Intent(requireContext(), EditUserProfileActivity.class);
        startActivity(intent);
    }
}

