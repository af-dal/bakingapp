package com.nanodegree.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.logic.VideoPlayerManager;
import com.nanodegree.udacity.bakingapp.ui.fragments.RecipeDetailFragment;
import com.nanodegree.udacity.bakingapp.ui.fragments.RecipeStepDetailFragmentBuilder;
import com.nanodegree.udacity.bakingapp.ui.interfaces.IRecipeDetailCallback;
import com.nanodegree.udacity.bakingapp.ui.interfaces.IRecipeStepDetailOpener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements IRecipeDetailCallback, IRecipeStepDetailOpener {

    private static final String BUNDLE_RECIPE = "bundle.recipe";

    @Nullable
    @BindView(R.id.step_detail)
    View fragmentStepDetail;

    @NonNull
    public static Intent createIntent(@NonNull final Context context, @NonNull final Recipe recipe) {
        final Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(BUNDLE_RECIPE, recipe);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if (getRecipe() != null) {
            setTitle(getRecipe().getName());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoPlayerManager.getInstance().resetVideoPlayer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    public Recipe getRecipe() {
        if (getIntent() != null && getIntent().getParcelableExtra(BUNDLE_RECIPE) instanceof Recipe) {
            return getIntent().getParcelableExtra(BUNDLE_RECIPE);
        }
        return null;
    }

    @Override
    public void openRecipeStepDetail(Recipe recipe, int position) {
        if (fragmentStepDetail == null) {
            startActivity(new Intent(RecipeStepDetailActivity.createIntent(this, recipe, position)));
        } else {
            final FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.step_detail, new RecipeStepDetailFragmentBuilder(recipe, position).build());
            ft.commit();

            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
            if (fragment instanceof RecipeDetailFragment) {
                ((RecipeDetailFragment) fragment).setSelectedItem(position);
            }

        }
    }
}
