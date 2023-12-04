package com.mobdeve.s17.charcookery;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InstructionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instructions_fragment, container, false);

        LinearLayout instructionsLayout = view.findViewById(R.id.instructions_fragment_layout);

        // Get the array of instructions from resources
        Resources resources = getResources();
        String[] instructionsArray = resources.getStringArray(R.array.instructions_default);

        for (String instruction : instructionsArray) {
            // Split the instruction into step and subitem
            String[] parts = instruction.split("\\|");
            String step = parts[0];
            String subitem = parts[1];

            // Create TextViews for step and subitem
            TextView stepTextView = new TextView(requireContext());
            stepTextView.setText(step);
            stepTextView.setTextSize(16);
            stepTextView.setTypeface(null, Typeface.BOLD);
            stepTextView.setPadding(0, 0, 0, 5);

            TextView subitemTextView = new TextView(requireContext());
            subitemTextView.setText(subitem);
            subitemTextView.setTextSize(16);
            subitemTextView.setPadding(0, 0, 0, 15);

            // Add TextViews to the instructionsLayout
            instructionsLayout.addView(stepTextView);
            instructionsLayout.addView(subitemTextView);

            // Add a line break
            instructionsLayout.addView(new View(requireContext()));
        }

        return view;
    }
}
