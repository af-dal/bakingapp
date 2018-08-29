package com.nanodegree.udacity.bakingapp.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Ingredients;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.ui.adapter.RecipeDetailListAdapter;
import com.nanodegree.udacity.bakingapp.ui.interfaces.IRecipeDetailCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {
    private static final String NEW_LINE = "\n";

    @BindView(R.id.ingredients_text_view)
    TextView ingredientsText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    private IRecipeDetailCallback iRecipeDetailCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecipeDetailListAdapter(getActivity(), getRecipe(), createListFromRecipe(-1)));
    }

    @Nullable
    private Recipe getRecipe() {
        if (iRecipeDetailCallback != null) {
            return iRecipeDetailCallback.getRecipe();
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IRecipeDetailCallback) {
            iRecipeDetailCallback = (IRecipeDetailCallback) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iRecipeDetailCallback = null;
    }

    private void setIngredients(@NonNull final List<Ingredients> ingredientList) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Ingredients ingredients : ingredientList) {
            stringBuilder.append(getString(R.string.ingredient_text,
                    String.format(Locale.getDefault(), "%.2f", ingredients.getQuantity()), ingredients.getMeasure(), ingredients.getIngredient()));
            stringBuilder.append(NEW_LINE);
        }
        ingredientsText.setText(stringBuilder);
    }

    @NonNull
    private List<Object> createListFromRecipe(final int selectedPosition) {
        final List<Object> objectList = new ArrayList<>();
        final Recipe recipe = getRecipe();

        if (recipe != null) {
            for (int i = 0; i < recipe.getSteps().size(); i++) {
                recipe.getSteps().get(i).setSelected(false);
                if (i == selectedPosition) {
                    recipe.getSteps().get(i).setSelected(true);
                }
            }

            objectList.addAll(recipe.getSteps());
            setIngredients(recipe.getIngredients());
        }

        return objectList;
    }

    public void setSelectedItem(final int position) {
        if (recyclerView.getAdapter() instanceof RecipeDetailListAdapter) {
            ((RecipeDetailListAdapter) recyclerView.getAdapter()).setItems(createListFromRecipe(position));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
