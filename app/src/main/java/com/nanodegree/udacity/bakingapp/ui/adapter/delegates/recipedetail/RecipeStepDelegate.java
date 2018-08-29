package com.nanodegree.udacity.bakingapp.ui.adapter.delegates.recipedetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.data.model.Step;
import com.nanodegree.udacity.bakingapp.ui.viewholder.RecipeStepViewHolder;

import java.util.List;


public class RecipeStepDelegate extends AdapterDelegate<List<Object>> {
    private int stepSize;
    private Recipe recipe;

    public RecipeStepDelegate(final Context context, final Recipe recipe) {
        this.recipe = recipe;
        stepSize = (int) (context.getResources().getDisplayMetrics().widthPixels / 3f);

        if (context.getResources().getBoolean(R.bool.isLarge)) {
            stepSize = stepSize / 2;
        }
    }

    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof Step;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        final RecipeStepViewHolder recipeStepViewHolder = RecipeStepViewHolder.create(parent);
        final ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(stepSize, stepSize);
        layoutParams.setMargins(
                parent.getContext().getResources().getDimensionPixelSize(R.dimen.base_padding_half),
                parent.getContext().getResources().getDimensionPixelSize(R.dimen.base_padding_half),
                parent.getContext().getResources().getDimensionPixelSize(R.dimen.base_padding_half),
                parent.getContext().getResources().getDimensionPixelSize(R.dimen.base_padding_half));
        recipeStepViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(layoutParams));
        return recipeStepViewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((RecipeStepViewHolder) holder).bind(recipe, position);
    }
}
