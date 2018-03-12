package com.recyclerx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;

public class ResourceUtil {
    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return AppCompatResources.getDrawable(context, id);
    }

    public static TypedArray getIntegerArray(Context context, int id) {
        return context.getResources().obtainTypedArray(id);
    }

    public static int getColor(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    public static float getDimen(Context context, int id) {
        return context.getResources().getDimension(id);
    }
}
