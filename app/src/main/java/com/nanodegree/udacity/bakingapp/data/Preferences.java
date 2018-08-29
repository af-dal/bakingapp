package com.nanodegree.udacity.bakingapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;
import com.nanodegree.udacity.bakingapp.widget.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Preferences {

    private static final String PREF_WIDGET_RECIPE = "PREF_WIDGET_RECIPE";

    @NonNull
    private static SharedPreferences getSharedPrefs(@NonNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static boolean containsRecipeInFav(final Context context, final Recipe recipe) {
        if (context != null && recipe != null) {
            final List<Recipe> recipeList = getRecipeList(context);
            if (recipeList.contains(recipe)) {
                return true;
            }
        }
        return false;
    }

    public static void addOrRemoveRecipe(final Context context, final Recipe recipe) {
        if (containsRecipeInFav(context, recipe)) {
            removeRecipe(context, recipe);
        } else {
            addRecipe(context, recipe);
        }
    }


    private static void addRecipe(final Context context, final Recipe recipe) {
        if (context != null && recipe != null) {
            final List<Recipe> recipeList = getRecipeList(context);
            if (!recipeList.contains(recipe)) {
                recipeList.add(recipe);
            }

            saveRecipes(context, recipeList);
        }
    }

    private static void removeRecipe(final Context context, final Recipe recipe) {
        if (context != null && recipe != null) {
            final List<Recipe> recipeList = getRecipeList(context);
            recipeList.remove(recipe);
            saveRecipes(context, recipeList);
        }
    }

    private static void saveRecipes(Context context, List<Recipe> recipeList) {
        final Set<String> recipeSet = new HashSet<>();
        for (Recipe recipe : recipeList) {
            recipeSet.add(new Gson().toJson(recipe, Recipe.class));
        }
        getSharedPrefs(context)
                .edit()
                .putStringSet(PREF_WIDGET_RECIPE, recipeSet)
                .apply();

        RecipeWidgetProvider.updateWidgetIfNeeded(context);
    }

    @NonNull
    public static List<Recipe> getRecipeList(final Context context) {
        final List<Recipe> recipeList = new ArrayList<>();

        final Set<String> set = getSharedPrefs(context).getStringSet(PREF_WIDGET_RECIPE, new HashSet<String>());

        for (String team : set) {
            recipeList.add(new Gson().fromJson(team, Recipe.class));
        }
        return recipeList;
    }
}
