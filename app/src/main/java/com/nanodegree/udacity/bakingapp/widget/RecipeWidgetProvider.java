package com.nanodegree.udacity.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.nanodegree.udacity.bakingapp.R;


public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(@NonNull final Context context, @NonNull final AppWidgetManager appWidgetManager, @NonNull final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (final int appWidgetId : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, options);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, final Bundle newOptions) {

        //Update RemoteView with size
        final RemoteViews remoteViews = updateWidgetList(context, appWidgetId);

        //Update widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        //update list
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_list);

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }


    private RemoteViews updateWidgetList(@NonNull final Context context, int widgetID) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_view);

        final Intent listWidgedServiceIntent = new Intent(context, WidgetService.class);
        listWidgedServiceIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        listWidgedServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(R.id.widget_recipe_list, listWidgedServiceIntent);

        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.widget_recipe_list, R.id.empty_view);

        return remoteViews;
    }

    public static void updateWidgetIfNeeded(final Context context) {
        if (context != null && isWidgetRunning(context)) {
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            final ComponentName componentName = new ComponentName(context, RecipeWidgetProvider.class);
            final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_list);
        }
    }

    /**
     * Return true if at least one widget is running
     */
    private static boolean isWidgetRunning(@Nullable final Context context) {

        if (context != null) {
            final ComponentName myWidget = new ComponentName(context, RecipeWidgetProvider.class);
            final int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(myWidget);
            return ids.length > 0;
        }

        return false;
    }
}