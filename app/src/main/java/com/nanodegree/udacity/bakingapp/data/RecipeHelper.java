package com.nanodegree.udacity.bakingapp.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {

    @NonNull
    public static List<Recipe> getRecipeList(@Nullable final Context context) {
        if (context == null) return new ArrayList<>();

        final InputStream raw = context.getResources().openRawResource(R.raw.recipe);
        final Reader reader = new BufferedReader(new InputStreamReader(raw));
        return new Gson().fromJson(reader, new TypeToken<List<Recipe>>() {
        }.getType());
    }
}
