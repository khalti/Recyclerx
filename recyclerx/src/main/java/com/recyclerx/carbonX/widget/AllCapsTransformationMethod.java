package com.recyclerx.carbonX.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;

import java.util.Locale;

/**
 * Transformation method used for making all characters capitalized. Can be used by all classes
 * derived from TextView.
 *
 * @see(android.widget.TextView)
 */
public class AllCapsTransformationMethod implements TransformationMethod {
    private Locale mLocale;

    /**
     * Uses current locale.
     *
     * @param context Context to get locale from
     */
    public AllCapsTransformationMethod(Context context) {
        mLocale = context.getResources().getConfiguration().locale;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return source != null ? source.toString().toUpperCase(mLocale) : null;
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction,
                               Rect previouslyFocusedRect) {
    }
}
