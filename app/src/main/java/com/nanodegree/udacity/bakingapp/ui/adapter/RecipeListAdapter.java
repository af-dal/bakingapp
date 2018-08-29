package com.nanodegree.udacity.bakingapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.Preferences;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.helper.ImageLoader;
import com.nanodegree.udacity.bakingapp.ui.RecipeDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private final List<Recipe> recipeList;

    public RecipeListAdapter(@NonNull final List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recipe_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.reset();
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_view_name)
        TextView recipeName;

        @BindView(R.id.recipe_image)
        ImageView imageView;

        @BindView(R.id.recipe_favorite)
        ImageView favoriteImage;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull final Recipe recipe) {
            recipeName.setText(recipe.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext().startActivity(
                            RecipeDetailActivity.createIntent(itemView.getContext(), recipe));
                }
            });

            ImageLoader.setImage(recipe.getImage(), R.drawable.recipe_placeholder, imageView);

            favoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Preferences.addOrRemoveRecipe(itemView.getContext(), recipe);
                    updateFavoriteDrawable(recipe);
                }
            });

            updateFavoriteDrawable(recipe);
        }

        private void updateFavoriteDrawable(final Recipe currentRecipe) {
            if (Preferences.containsRecipeInFav(itemView.getContext(), currentRecipe)) {
                favoriteImage.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                favoriteImage.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }

        void reset() {
            itemView.setOnClickListener(null);
            recipeName.setText(null);
        }
    }
}
