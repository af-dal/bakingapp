package com.nanodegree.udacity.bakingapp.ui.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.helper.ImageLoader;
import com.nanodegree.udacity.bakingapp.logic.VideoPlayerManager;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment {

    @BindView(R.id.exoplayer)
    PlayerView playerView;

    @BindView(R.id.step_instructions)
    TextView stepInstructions;

    @BindView(R.id.thumb_nail_image)
    ImageView thumbNailImage;

    @BindBool(R.bool.isLarge)
    boolean isLarge;

    int videoLandscapeHeight;

    @Arg
    Recipe recipe;

    @Arg
    int stepPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        final int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = 0;
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        videoLandscapeHeight = getResources().getDisplayMetrics().heightPixels - statusBarHeight;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_step_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        stepInstructions.setText(recipe.getSteps().get(stepPosition).getDescription());

        if (getActivity() != null) {
            getActivity().setTitle(recipe.getName());
        }

        if (!isLarge) {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hideActionBar();

            } else {
                showActionBar();
            }
        }
        initializePlayer();
    }

    private void hideActionBar() {
        if (!isLarge && getActivity() instanceof AppCompatActivity &&
                ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    private void showActionBar() {
        if (!isLarge && getActivity() instanceof AppCompatActivity &&
                ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }

    private void initializePlayer() {
        ImageLoader.setImage(recipe.getSteps().get(stepPosition).getThumbnailUrl(), thumbNailImage);

        if (!TextUtils.isEmpty(recipe.getSteps().get(stepPosition).getVideoUrl())) {
            VideoPlayerManager.getInstance().prepare(getActivity(), playerView, recipe.getSteps().get(stepPosition).getVideoUrl());
        } else {
            playerView.setVisibility(View.GONE);
            showActionBar();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        VideoPlayerManager.getInstance().releaseVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoPlayerManager.getInstance().goToBackground();
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoPlayerManager.getInstance().goToForeground();
    }
}
