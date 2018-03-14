package com.example.itamarborges.baking.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by itamarborges on 13/03/18.
 */

public class JsonUtils {

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("baking.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
