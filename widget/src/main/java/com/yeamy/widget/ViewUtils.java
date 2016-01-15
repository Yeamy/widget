package com.yeamy.widget;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class ViewUtils {

    public static int dip2Px(DisplayMetrics dm, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, dm);
    }

    public static float dip2Point(DisplayMetrics dm, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, dm);
    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
