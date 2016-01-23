package com.yeamy.widget.transformer;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

//https://github.com/kelp11211/PageTransformer
public class CubePageTransformer implements PageTransformer {
    private static final float DEGREE = 60.0f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getMeasuredWidth();
        int pageHeight = view.getMeasuredHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 0) { // [-1,0]
            view.setAlpha(1);
            // Calculate the rotation degree
            float rotation = DEGREE * position;
            // Set pivot point
            view.setPivotX(pageWidth);
            view.setPivotY(pageHeight / 2);
            view.setRotationY(rotation);
        } else if (position <= 1) { // (0,1]
            view.setAlpha(1);
            // Calculate the rotation degree
            float rotation = DEGREE * position;
            // Set pivot point
            view.setPivotX(0);
            view.setPivotY(pageHeight / 2);
            view.setRotationY(rotation);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }

    }

}
