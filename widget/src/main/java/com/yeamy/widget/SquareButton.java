package com.yeamy.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

/**
 * A square button with center Gravity
 */
public class SquareButton extends Button {

    public SquareButton(Context context) {
        super(context);
        setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    public SquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    @SuppressWarnings("NewApi")
    public SquareButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w_size = MeasureSpec.getSize(widthMeasureSpec);
        int h_size = MeasureSpec.getSize(heightMeasureSpec);
        int measureSpec = (w_size < h_size) ? widthMeasureSpec : heightMeasureSpec;
        super.onMeasure(measureSpec, measureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            Drawable drawable = getCompoundDrawables()[1];
            if (drawable != null) {
                int h = drawable.getBounds().height()
                        + getCompoundDrawablePadding()
                        + (int) getTextSize();
                int padding = (getHeight() - h) / 2;
                setPadding(0, padding, 0, 0);
            }
        }
        super.onLayout(changed, left, top, right, bottom);
    }

}
