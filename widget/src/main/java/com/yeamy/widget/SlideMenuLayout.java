package com.yeamy.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideMenuLayout extends FrameLayout {
    private GestureDetector detector;
    private Scroller scroller;
    private int MAX_SCROLL;
    private SlideMenuMaskView maskView;
    private OnClickListener l;

    public SlideMenuLayout(Context context) {
        super(context);
        init(context);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        detector = new GestureDetector(context, new GestureListener());
        scroller = new Scroller(context, new LinearInterpolator());
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            MAX_SCROLL = 0;
            for (int i = 0, count = getChildCount(); i < count; i++) {
                View child = getChildAt(i);
                if (i == 0) {
                    child.layout(0, 0, r - l, b - t);
                } else if (child.getVisibility() != GONE) {
                    int width = child.getMeasuredWidth();
                    int left = r - l;
                    child.layout(left, 0, left + width, b - t);
                    r += width;
                    MAX_SCROLL += width;
                }
            }
        }
    }

    public void setMaskView(SlideMenuMaskView maskView) {
        this.maskView = maskView;
    }

    public void close() {
        int x = getScrollX();
        if (x == 0) {
            return;
        }
        int y = getScrollY();
        scroller.startScroll(x, y, 0 - x, y);
        invalidate();
        if (maskView != null) {
            maskView.close();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                // 添加结束事件,实现上下回弹
                int mid = MAX_SCROLL / 2;
                int x = getScrollX();
                int dx;
                if (x >= mid) {//scroll end
                    dx = MAX_SCROLL;
                    if (maskView != null) {
                        maskView.open(this);
                    }
                } else {
                    dx = 0;
                    if (maskView != null) {
                        maskView.close();
                    }
                }
                scroller.startScroll(x, 0, dx - x, 0);
                invalidate();
        }
        return detector.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {//判断mScroller滚动是否完成
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            scroller.abortAnimation();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (getScrollX() > 0) {
                close();
            } else if (l != null) {
                l.onClick(SlideMenuLayout.this);
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            getParent().requestDisallowInterceptTouchEvent(true);
            int scroll = (int) (getScrollX() + distanceX);
            scroll = scroll > MAX_SCROLL ? MAX_SCROLL : scroll;
            scroll = scroll < 0 ? 0 : scroll;
            scrollBy(scroll - getScrollX(), 0);
            return true;
        }

    }

}