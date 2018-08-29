package com.nanodegree.udacity.bakingapp.ui.adapter.delegates.recipedetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.nanodegree.udacity.bakingapp.data.model.Ingredients;
import com.nanodegree.udacity.bakingapp.ui.viewholder.IngredientsViewHolder;

import java.util.List;

public class IngredientsDelegate extends AdapterDelegate<List<Object>> {
    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof IngredientsModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return IngredientsViewHolder.create(parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull final List<Object> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((IngredientsViewHolder) holder).bind(((IngredientsModel) items.get(position)).getIngredientsList());
    }

    public static class IngredientsModel {
        private final List<Ingredients> ingredientsList;

        public IngredientsModel(@NonNull final List<Ingredients> ingredientsList) {
            this.ingredientsList = ingredientsList;
        }

        @NonNull
        List<Ingredients> getIngredientsList() {
            return ingredientsList;
        }
    }
}
