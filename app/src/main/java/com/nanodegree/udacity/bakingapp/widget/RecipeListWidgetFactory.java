package com.nanodegree.udacity.bakingapp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nanodegree.udacity.bakingapp.R;
import com.nanodegree.udacity.bakingapp.data.Preferences;
import com.nanodegree.udacity.bakingapp.data.model.Ingredients;
import com.nanodegree.udacity.bakingapp.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeListWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String NEW_LINE = "\n";
    @NonNull
    private final List<Recipe> items = new ArrayList<>();

    @NonNull
    private final Context context;


    public RecipeListWidgetFactory(@NonNull final Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        //nothing because we are loading data in onDataSetChanged
        //not allowed to load the data here because it can cause a ANR
    }

    @Override
    public void onDataSetChanged() {
        items.clear();
        items.addAll(Preferences.getRecipeList(context));
    }

    @Override
    public void onDestroy() {
        //nothing
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //return null == we use the default loading view
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list_view);
        remoteView.setTextViewText(R.id.widget_recipe_name, items.get(position).getName());
        remoteView.setTextViewText(R.id.widget_ingredients, getIngredientsText(context, items.get(position).getIngredients()));
        return remoteView;
    }

    private String getIngredientsText(final Context context, @NonNull final List<Ingredients> ingredientList) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Ingredients ingredients : ingredientList) {
            stringBuilder.append(context.getString(R.string.ingredient_text,
                    String.format(Locale.getDefault(), "%.2f", ingredients.getQuantity()), ingredients.getMeasure(), ingredients.getIngredient()));
            stringBuilder.append(NEW_LINE);
        }
        return stringBuilder.toString();
    }
}
