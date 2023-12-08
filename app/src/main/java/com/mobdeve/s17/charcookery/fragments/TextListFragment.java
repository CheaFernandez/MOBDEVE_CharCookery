package com.mobdeve.s17.charcookery.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mobdeve.s17.charcookery.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class TextListFragment extends Fragment {

    private List<String> items;
    private boolean isOrdered;

    public TextListFragment(String[] items) {
        this.items = Arrays.asList(items);
        this.isOrdered = false;
    }

    public TextListFragment(String[] items, boolean isOrdered) {
        this.items = Arrays.asList(items);
        this.isOrdered = isOrdered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablist, container, false);

        TextView tvIngredients = view.findViewById(R.id.tvListItems);
        tvIngredients.setText(getItemsAsHTML());

        return view;
    }

    private CharSequence getItemsAsHTML() {
        StringBuilder htmlStringBuilder = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);

            String listItem = this.isOrdered
                    ? (i + 1) + ". &nbsp;" + item + "<br /><br />"
                    : "<li>  &nbsp;" + item + "</li>";

            htmlStringBuilder.append(listItem);
        }

        // Convert the HTML string to CharSequence for display in TextView
        return Html.fromHtml(this.isOrdered
                ? htmlStringBuilder.toString()
                : "<ul>" + htmlStringBuilder.toString()+"</ul>");
    }
}
