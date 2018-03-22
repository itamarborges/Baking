package com.example.itamarborges.baking;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by itamarborges on 20/03/18.
 */


public class BakingWidgetService extends RemoteViewsService {

    public static final String INTENT_KEY_INGREDIENTS = "ingredients";

    ArrayList<String> mListIngredients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mListIngredients = intent.getStringArrayListExtra(INTENT_KEY_INGREDIENTS);
        return new BakingRemoteViewsFactory(this.getApplicationContext(), mListIngredients);
    }
}

class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    ArrayList<String> mList = new ArrayList<>();

    public BakingRemoteViewsFactory(Context applicationContext, ArrayList<String> list) {
        mContext = applicationContext;
        mList = list;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (mList == null || mList.size() == 0) return null;

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget_item);
        rv.setTextViewText(R.id.appwidget_ingredients_item, mList.get(i));

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
