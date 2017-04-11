package com.yeamy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * a square ViewGroup with square child
 */
public class SquareLayout extends ViewGroup {
    private int columnCount;
    private int childPadding;

    public SquareLayout(Context context) {
        super(context);
        columnCount = 3;
        childPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f,
                getResources().getDisplayMetrics());
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressWarnings("NewApi")
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareLayout);
        columnCount = a.getInt(R.styleable.SquareLayout_columnCount, 3);
        childPadding = (int) a.getDimension(R.styleable.SquareLayout_childPadding, 0f);
        a.recycle();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setChildPadding(int paddingChild) {
        this.childPadding = paddingChild;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int column = columnCount;
        int padding = childPadding;

        if (count > 0) {
            int row = count % column == 0 ? count / column : count / column + 1;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int size = (width - (padding * (column - 1))) / column;
            int height = size * row + (padding * (row - 1));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int childSizeMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            count = getChildCount();
            for (int i = 0; i < count; i++) {
                getChildAt(i).measure(childSizeMeasureSpec, childSizeMeasureSpec);
            }
        } else {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int column = columnCount;
        int padding = childPadding;
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View view = getChildAt(i);
            int size = view.getMeasuredWidth();
            int x = i % column;
            int y = i / column;
            int fromX = (padding + size) * x;
            int fromY = (padding + size) * y;
            int toX = fromX + size;
            int toY = fromY + size;
            view.layout(fromX, fromY, toX, toY);
        }
    }
}
