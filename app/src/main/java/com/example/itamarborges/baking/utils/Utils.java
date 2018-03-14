package com.example.itamarborges.baking.utils;

import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by itamarborges on 14/03/18.
 */

public class Utils {

    public static int numberOfColumns(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 600;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        return nColumns;
    }
}
