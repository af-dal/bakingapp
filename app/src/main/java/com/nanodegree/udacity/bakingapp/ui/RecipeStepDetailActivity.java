package com.nanodegree.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.ui.fragments.RecipeStepDetailFragmentBuilder;

import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {
    private static final String BUNDLE_RECIPE = "bundle.recipe";
    private static final String BUNDLE_STEP_POSITION = "bundle.step";

    @NonNull
    public static Intent createIntent(@NonNull final Context context, @NonNull final Recipe recipe,
                                      final int position) {
        final Intent intent = new Intent(context, RecipeStepDetailActivity.class);
        intent.putExtra(BUNDLE_RECIPE, recipe);
        intent.putExtra(BUNDLE_STEP_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        showStepDetail();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    private void showStepDetail() {
        if (getIntent() != null && getIntent().getParcelableExtra(BUNDLE_RECIPE) instanceof Recipe) {
            final Recipe recipe = getIntent().getParcelableExtra(BUNDLE_RECIPE);
            final FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragement_container, new RecipeStepDetailFragmentBuilder(recipe,
                    getIntent().getIntExtra(BUNDLE_STEP_POSITION, 0)).build());
            ft.commit();
        }
    }
}
