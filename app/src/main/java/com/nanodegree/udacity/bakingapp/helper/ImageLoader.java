package com.nanodegree.udacity.bakingapp.helper;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nanodegree.udacity.bakingapp.R;
import com.squareup.picasso.Picasso;

public class ImageLoader {


    public static void setImage(@Nullable final String urlPath, @DrawableRes final int placeholder,
                                final ImageView target) {

        if (!TextUtils.isEmpty(urlPath)) {
            Picasso.get().load(urlPath).placeholder(R.drawable.recipe_placeholder).into(target);
        } else {
            target.setImageResource(placeholder);
        }
    }

    public static void setImage(@Nullable final String urlPath, final ImageView target) {
        if (!TextUtils.isEmpty(urlPath)) {
            Picasso.get().load(urlPath).into(target);
        } else {
            target.setImageDrawable(null);
            target.setVisibility(View.GONE);
        }
    }
}
