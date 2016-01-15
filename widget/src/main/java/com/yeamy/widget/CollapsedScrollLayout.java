package com.yeamy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 纵向的LinearLayout，自动隐藏collapsedId以及前面的View，通过滑动来控制显示/隐藏
 */
public class CollapsedScrollLayout extends LinearLayout {
    private static final int SCROLL_RATE = 2; //手势滚动与实际布局滚动的倍数

    private GestureDetector detector;
    private Scroller scroller;
    private int maxScroll;
    private OnCollapsedListener listener;
    private int collapsedId = NO_ID;

    public CollapsedScrollLayout(Context context) {
        this(context, null);
    }

    public CollapsedScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        detector = new GestureDetector(context, new GestureListener());
        scroller = new Scroller(context, new LinearInterpolator());

//        // retrieve selected radio button as requested by the user in the XML layout file
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CollapsedScrollLayout);

        int value = attributes.getResourceId(R.styleable.CollapsedScrollLayout_collapsedId, View.NO_ID);
        if (value != View.NO_ID) {
            collapsedId = value;
        }
        attributes.recycle();
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation != VERTICAL) {
            throw new IllegalArgumentException("CollapsedScrollLayout is always vertical and does"
                    + " not support horizontal orientation");
        }
        super.setOrientation(orientation);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && collapsedId != View.NO_ID) {
            maxScroll = findViewById(collapsedId).getBottom();
            scrollBy(0, maxScroll);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                // 添加结束事件,实现上下回弹
                int y = getScrollY();
                if (y > 0 && y < maxScroll) {
                    int dy = y > maxScroll / SCROLL_RATE ? maxScroll : 0;
                    scroller.startScroll(0, y, 0, dy - y);
                    invalidate();
                }
        }
        return detector.onTouchEvent(event);
    }

    private boolean collapsed = true;

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {//判断mScroller滚动是否完成
            scrollTo(0, scroller.getCurrY());
            invalidate();
        } else if (listener != null) {//add callback event
            int y = getScrollY();
            if (y == 0 && collapsed) {
                listener.onCollapsedChange(false);
                collapsed = false;
            } else if (y == maxScroll && !collapsed) {
                listener.onCollapsedChange(true);
                collapsed = true;
            }
        }
    }

    public void setOnCollapsedListener(OnCollapsedListener listener) {
        this.listener = listener;
    }

    public interface OnCollapsedListener {
        void onCollapsedChange(boolean isCollapsed);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            scroller.abortAnimation();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int scroll = (int) (getScrollY() + distanceY);
            scroll = scroll > maxScroll ? maxScroll : scroll;
            scroll = scroll < 0 ? 0 : scroll;
            scrollBy(0, scroll - getScrollY());
            return true;
        }

    }

}
