package com.nanodegree.udacity.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.ui.adapter.delegates.recipedetail.IngredientsDelegate;
import com.nanodegree.udacity.bakingapp.ui.adapter.delegates.recipedetail.RecipeStepDelegate;

import java.util.List;

public class RecipeDetailListAdapter extends ListDelegationAdapter<List<Object>> {

    public RecipeDetailListAdapter(final Context context, Recipe recipe, @NonNull final List<Object> objects) {
        delegatesManager.addDelegate(new IngredientsDelegate())
                .addDelegate(new RecipeStepDelegate(context, recipe));

        setItems(objects);
    }
}