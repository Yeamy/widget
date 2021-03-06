package com.yeamy.widget.transformer;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class UpperFanPageTransformer implements PageTransformer {
    private static final float DEGREE = 30.0f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) { // [-1,1]
            view.setAlpha(1);
            // Calculate the rotation degree
            float rotation = DEGREE * position;
            // Set pivot point
            view.setPivotX(pageWidth * 0.5f);
            view.setPivotY(pageHeight);
            view.setRotation(rotation);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }

    }

}
