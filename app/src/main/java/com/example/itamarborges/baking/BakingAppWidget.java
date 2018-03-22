package com.example.itamarborges.baking;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Ingredient;
import com.example.itamarborges.baking.pojo.Recipe;
import com.example.itamarborges.baking.utils.JsonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        if (recipe != null) {
            views.setTextViewText(R.id.appwidget_ingredients_label, recipe.getName());
            Intent intent = new Intent(context, BakingWidgetService.class);
            intent.setData(Uri.fromParts("content", String.valueOf(appWidgetId), recipe.getName()));

            ArrayList<String> ingredients = new ArrayList<>();

            for(Ingredient i : recipe.getIngredients()) {
                String ingr = String.valueOf(i.getQuantity()).concat(" ").concat(i.getMeasure().concat(" ").concat(i.getIngredient()));
                ingredients.add(ingr);
            }

            intent.putExtra(BakingWidgetService.INTENT_KEY_INGREDIENTS, ingredients);

            views.setRemoteAdapter(R.id.recipes_list, intent);

        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    public static void updateFromActivity(Context mContext, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(mContext, appWidgetManager, recipe, appWidgetId);
        }

    }
}

