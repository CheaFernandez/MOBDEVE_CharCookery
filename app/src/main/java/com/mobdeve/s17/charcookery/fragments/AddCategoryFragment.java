package com.mobdeve.s17.charcookery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mobdeve.s17.charcookery.MainActivity;
import com.mobdeve.s17.charcookery.R;
import com.mobdeve.s17.charcookery.api.APIClient;
import com.mobdeve.s17.charcookery.api.APIInterface;
import com.mobdeve.s17.charcookery.api.models.EditableCategoryBody;
import com.mobdeve.s17.charcookery.models.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryFragment extends Fragment {
    private View view;
    private EditText titleField;
    private Button cancelBtn, addCategoryBtn;
    private TextView tvError;

    public AddCategoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_category, container, false);

        // Update menu bar
        ((MainActivity) getActivity()).updateMenuBar();

        setupView();

        return this.view;
    }

    private void setupView() {
        titleField = view.findViewById(R.id.edit_title_field);
        addCategoryBtn = view.findViewById(R.id.add_category_btn);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        tvError = view.findViewById(R.id.tvError);


        cancelBtn.setOnClickListener(v -> {
            titleField.setText("");
            ((MainActivity) getActivity()).switchToMainView();
        });

        addCategoryBtn.setOnClickListener(v -> {
            String title = titleField.getText().toString();
            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a title", Toast.LENGTH_SHORT).show();
            } else {
                createCategory(title);
            }
        });
    }

    private void createCategory(String title) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        EditableCategoryBody body = new EditableCategoryBody(title);
        Call<Category> call = apiInterface.createCategory(body);

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    tvError.setText("");
                    Toast.makeText(getContext(), "Successfully created category", Toast.LENGTH_SHORT).show();

                    titleField.setText("");
                    ((MainActivity) getActivity()).switchToMainView();
                } else {
                    tvError.setText("Failed to create category. Check if category \"" + title + "\" already exists.");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
                tvError.setText("Failed to create category");
            }
        });
    }
}
