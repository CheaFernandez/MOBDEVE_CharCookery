package com.mobdeve.s17.charcookery;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class IngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_fragment, container, false);

        LinearLayout ingredientsLayout = view.findViewById(R.id.ingredients_fragment_layout);

        // Get the array of ingredients from resources
        Resources resources = getResources();
        String[] ingredientsArray = resources.getStringArray(R.array.ingredients_list_default);

        for (String ingredient : ingredientsArray) {
            // Create a TextView for each ingredient
            TextView textView = new TextView(requireContext());
            textView.setText(ingredient);
            textView.setTextSize(16);
            textView.setText(Html.fromHtml("&#8226; " + ingredient, Html.FROM_HTML_MODE_COMPACT));
            textView.setPadding(0, 0, 0, 15);

            // Set layout parameters for the TextView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(layoutParams);

            // Add the TextView to the ingredientsLayout
            ingredientsLayout.addView(textView);
        }

        return view;
    }
}
