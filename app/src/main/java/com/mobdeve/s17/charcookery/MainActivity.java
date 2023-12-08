package com.mobdeve.s17.charcookery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mobdeve.s17.charcookery.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // Setup initial fragment
        setFragment(new MainFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentViewMain, fragment);
        fragmentTransaction.addToBackStack(null); // Optional: add to back stack for fragment navigation
        fragmentTransaction.commit();
    }

    public void gotoAddCategoryView(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }
}