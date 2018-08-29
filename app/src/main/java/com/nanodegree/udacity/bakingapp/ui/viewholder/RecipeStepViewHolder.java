package com.nanodegree.udacity.bakingapp.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.ui.interfaces.IRecipeStepDetailOpener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_step_short_description_text)
    TextView shortDescriptionText;

    @BindView(R.id.recipe_step_title)
    TextView stepTitle;

    @BindView(R.id.recipe_step_video_image)
    ImageView videoImage;

    @BindView(R.id.step_view)
    View view;

    private RecipeStepViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static RecipeStepViewHolder create(final ViewGroup parent) {
        return new RecipeStepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_recipe_step, parent, false));
    }

    public void bind(@NonNull final Recipe recipe, final int position) {
        shortDescriptionText.setText(recipe.getSteps().get(position).getShortDescription());
        stepTitle.setText(itemView.getContext().getString(R.string.step_title,
                String.valueOf(position + 1)));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemView.getContext() instanceof IRecipeStepDetailOpener) {
                    ((IRecipeStepDetailOpener) itemView.getContext()).openRecipeStepDetail(recipe, position);
                }
            }
        });

        videoImage.setVisibility(
                !TextUtils.isEmpty(recipe.getSteps().get(position).getVideoUrl()) ? View.VISIBLE : View.GONE);

        if (recipe.getSteps().get(position).isSelected() && itemView.getContext().getResources().getBoolean(R.bool.isLarge)) {
            view.setBackgroundResource(R.color.colorPrimary);
        } else {
            view.setBackground(null);
        }
    }
}
