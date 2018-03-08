package com.recyclerx.carbonX;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.recyclerx.carbonX.animation.AnimUtils;
import com.recyclerx.carbonX.animation.AnimatedView;
import com.recyclerx.carbonX.drawable.ripple.RippleDrawable;
import com.recyclerx.carbonX.drawable.ripple.RippleDrawableFroyo;
import com.recyclerx.carbonX.drawable.ripple.RippleDrawableLollipop;
import com.recyclerx.carbonX.drawable.ripple.RippleDrawableMarshmallow;
import com.recyclerx.carbonX.drawable.ripple.RippleView;
import com.recyclerx.carbonX.shadow.ShadowView;
import com.recyclerx.carbonX.widget.InsetView;
import com.recyclerx.carbonX.widget.MaxSizeView;
import com.recyclerx.carbonX.widget.StateAnimatorView;
import com.recyclerx.carbonX.widget.TintedView;
import com.recyclerx.carbonX.widget.TouchMarginView;

public class CarbonX {
    private CarbonX() {
    }

    public static float getDip(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
    }

    public static float getSp(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, context.getResources().getDisplayMetrics());
    }

    public static void initRippleDrawable(RippleView rippleView, TypedArray a, int[] ids) {
        int carbon_rippleColor = ids[0];
        int carbon_rippleStyle = ids[1];
        int carbon_rippleHotspot = ids[2];
        int carbon_rippleRadius = ids[3];

        View view = (View) rippleView;
        if (view.isInEditMode())
            return;

        ColorStateList color = a.getColorStateList(carbon_rippleColor);

        if (color != null) {
            RippleDrawable.Style style = RippleDrawable.Style.values()[a.getInt(carbon_rippleStyle, RippleDrawable.Style.Background.ordinal())];
            boolean useHotspot = a.getBoolean(carbon_rippleHotspot, true);
            int radius = (int) a.getDimension(carbon_rippleRadius, -1);

            rippleView.setRippleDrawable(createRippleDrawable(color, style, view, useHotspot, radius));
        }
    }

    public static RippleDrawable createRippleDrawable(ColorStateList color, RippleDrawable.Style style, View view, boolean useHotspot, int radius) {
        RippleDrawable rippleDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rippleDrawable = new RippleDrawableMarshmallow(color, style == RippleDrawable.Style.Background ? view.getBackground() : null, style);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rippleDrawable = new RippleDrawableLollipop(color, style == RippleDrawable.Style.Background ? view.getBackground() : null, style);
        } else {
            rippleDrawable = new RippleDrawableFroyo(color, style == RippleDrawable.Style.Background ? view.getBackground() : null, style);
        }
        rippleDrawable.setCallback(view);
        rippleDrawable.setHotspotEnabled(useHotspot);
        rippleDrawable.setRadius(radius);
        return rippleDrawable;
    }

    public static RippleDrawable createRippleDrawable(ColorStateList color, RippleDrawable.Style style, View view, Drawable background, boolean useHotspot, int radius) {
        RippleDrawable rippleDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rippleDrawable = new RippleDrawableMarshmallow(color, background, style);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rippleDrawable = new RippleDrawableLollipop(color, background, style);
        } else {
            rippleDrawable = new RippleDrawableFroyo(color, background, style);
        }
        rippleDrawable.setCallback(view);
        rippleDrawable.setHotspotEnabled(useHotspot);
        rippleDrawable.setRadius(radius);
        return rippleDrawable;
    }

    public static void initTouchMargin(TouchMarginView view, TypedArray a, int[] ids) {
        int carbon_touchMargin = ids[0];
        int carbon_touchMarginLeft = ids[1];
        int carbon_touchMarginTop = ids[2];
        int carbon_touchMarginRight = ids[3];
        int carbon_touchMarginBottom = ids[4];

        int touchMarginAll = (int) a.getDimension(carbon_touchMargin, 0);
        int left = (int) a.getDimension(carbon_touchMarginLeft, touchMarginAll);
        int top = (int) a.getDimension(carbon_touchMarginTop, touchMarginAll);
        int right = (int) a.getDimension(carbon_touchMarginRight, touchMarginAll);
        int bottom = (int) a.getDimension(carbon_touchMarginBottom, touchMarginAll);
        view.setTouchMargin(left, top, right, bottom);
    }

    public static void initInset(InsetView view, TypedArray a, int[] ids) {
        int carbon_inset = ids[0];
        int carbon_insetLeft = ids[1];
        int carbon_insetTop = ids[2];
        int carbon_insetRight = ids[3];
        int carbon_insetBottom = ids[4];
        int carbon_insetColor = ids[5];

        int insetAll = (int) a.getDimension(carbon_inset, InsetView.INSET_NULL);
        int left = (int) a.getDimension(carbon_insetLeft, insetAll);
        int top = (int) a.getDimension(carbon_insetTop, insetAll);
        int right = (int) a.getDimension(carbon_insetRight, insetAll);
        int bottom = (int) a.getDimension(carbon_insetBottom, insetAll);
        view.setInset(left, top, right, bottom);

        view.setInsetColor(a.getColor(carbon_insetColor, 0));
    }

    public static void initMaxSize(MaxSizeView view, TypedArray a, int[] ids) {
        int carbon_maxWidth = ids[0];
        int carbon_maxHeight = ids[1];

        int width = (int) a.getDimension(carbon_maxWidth, Integer.MAX_VALUE);
        int height = (int) a.getDimension(carbon_maxHeight, Integer.MAX_VALUE);
        view.setMaximumWidth(width);
        view.setMaximumHeight(height);
    }

    public static void initTint(TintedView view, TypedArray a, int[] ids) {
        int carbon_tint = ids[0];
        int carbon_tintMode = ids[1];
        int carbon_backgroundTint = ids[2];
        int carbon_backgroundTintMode = ids[3];
        int carbon_animateColorChanges = ids[4];

        if (a.hasValue(carbon_tint)) {
            TypedValue value = new TypedValue();
            a.getValue(carbon_tint, value);
            if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                view.setTint(value.data);
            } else {
                view.setTint(a.getColorStateList(carbon_tint));
            }
        }
        view.setTintMode(TintedView.modes[a.getInt(carbon_tintMode, 1)]);

        if (a.hasValue(carbon_backgroundTint)) {
            TypedValue value = new TypedValue();
            a.getValue(carbon_backgroundTint, value);
            if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                view.setBackgroundTint(value.data);
            } else {
                view.setBackgroundTint(a.getColorStateList(carbon_backgroundTint));
            }
        }
        view.setBackgroundTintMode(TintedView.modes[a.getInt(carbon_backgroundTintMode, 1)]);

        if (a.hasValue(carbon_animateColorChanges))
            view.setAnimateColorChangesEnabled(a.getBoolean(carbon_animateColorChanges, false));
    }

    public static void initAnimations(AnimatedView view, TypedArray a, int[] ids) {
        int carbon_inAnimation = ids[0];
        int carbon_outAnimation = ids[1];

        view.setInAnimation(AnimUtils.Style.values()[a.getInt(carbon_inAnimation, 0)]);
        view.setOutAnimation(AnimUtils.Style.values()[a.getInt(carbon_outAnimation, 0)]);
    }

    public static void initElevation(ShadowView view, TypedArray a, int id) {
        float elevation = a.getDimension(id, 0);
        view.setElevation(elevation);
        if (elevation > 0)
            AnimUtils.setupElevationAnimator(((StateAnimatorView) view).getStateAnimator(), view);
    }

    public static int getThemeColor(Context context, int attr) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedvalueattr = new TypedValue();
        theme.resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId != 0 ? context.getResources().getColor(typedvalueattr.resourceId) : typedvalueattr.data;
    }

    public static Context getThemedContext(Context context, AttributeSet attributeSet, int[] attrs, int defStyleAttr, int attr) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, attrs, defStyleAttr, 0);
        if (a.hasValue(attr)) {
            int themeId = a.getResourceId(attr, 0);
            context.getTheme().applyStyle(themeId, true);
        }
        a.recycle();
        return context;
    }
}
