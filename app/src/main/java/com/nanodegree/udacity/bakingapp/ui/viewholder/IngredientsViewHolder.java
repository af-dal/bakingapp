package com.nanodegree.udacity.bakingapp.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Ingredients;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {
    private static final String NEW_LINE = "\n";

    @BindView(R.id.ingredients_text_view)
    TextView ingredientsTextView;

    private IngredientsViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static IngredientsViewHolder create(final ViewGroup parent) {
        return new IngredientsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_ingredients, parent, false));
    }

    public void bind(@NonNull final List<Ingredients> ingredientsList) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Ingredients ingredients : ingredientsList) {
            stringBuilder.append(itemView.getContext().getString(R.string.ingredient_text,
                    ingredients.getQuantity(), ingredients.getMeasure(), ingredients.getIngredient()));
            stringBuilder.append(NEW_LINE);
        }
        ingredientsTextView.setText(stringBuilder);
    }
}
