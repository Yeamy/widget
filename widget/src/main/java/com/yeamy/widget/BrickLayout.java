package com.yeamy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 从左往右,从上往下,自动排列,类似GridLayout,类似TableLayout
 */
public class BrickLayout extends FrameLayout {

    private int dividerHorizontal;
    private int dividerVertical;

    public BrickLayout(Context context) {
        super(context);
    }

    public BrickLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // retrieve selected radio button as requested by the user in the XML layout file
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BrickLayout);

        dividerHorizontal = attributes.getDimensionPixelSize(R.styleable.BrickLayout_dividerColumn, 0);
        dividerVertical = attributes.getDimensionPixelSize(R.styleable.BrickLayout_dividerRow, 0);
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int count = getChildCount();
        boolean firstColumn = true;
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right;
        int bottom = 0;
        int w = getMeasuredWidth() - getPaddingRight();
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            //left
            if (firstColumn) {
                firstColumn = false;
            } else {
                left += dividerHorizontal;
            }
            //right
            right = left + width;
            //new row
            if (right > w) {
                left = getPaddingLeft();
                top = bottom + dividerVertical;
                right = left + width;
            }
            //bottom
            int btm = top + height;
            if (btm > bottom) {
                bottom = btm;
            }
            child.layout(left, top, right, btm);
            left = right;
        }
        setMeasuredDimension(MeasureSpec.getSize(getMeasuredWidth()),
                MeasureSpec.getSize(bottom + getPaddingBottom()));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!changed) {
            return;
        }
//        Log.d(TAG, "changed = "+arg0+" left = "+arg1+" top = "+arg2+" right = "+arg3+" botom = "+arg4);
        final int count = getChildCount();
        boolean firstColumn = true;
        int left = getPaddingLeft();
        int top = 0;
        int right;
        int bottom = 0;
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            //left
            if (firstColumn) {
                firstColumn = false;
            } else {
                left += dividerHorizontal;
            }
            //right
            right = left + width;
            //new row
            if (right > r - l - getPaddingRight()) {
                left = getPaddingLeft();
                top = bottom + dividerVertical;
                right = left + width;
            }
            //bottom
            int btm = top + height;
            if (btm > bottom) {
                bottom = btm;
            }
            child.layout(left, top, right, btm);
            left = right;
        }
    }

}