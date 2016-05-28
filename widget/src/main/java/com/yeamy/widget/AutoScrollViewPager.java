package com.yeamy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Auto Scroll ViewPager
 */
public class AutoScrollViewPager extends ViewPager {
    private long DelayMillis;
    private Task task = new Task();

    public AutoScrollViewPager(Context context) {
        super(context);
        addOnPageChangeListener(task);
        setScroller(context, 700);
        DelayMillis = 3500L;
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(task);
        setScroller(context, 700);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, com.android.internal.R.styleable.View, 0, 0);
        DelayMillis = (long) a.getFloat(R.styleable.AutoScrollViewPager_delayMillis, 3500L);
        a.recycle();
    }

    public void setDelayMillis(long delayMillis) {
        DelayMillis = delayMillis;
    }

    private void setScroller(Context context, final int duration) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            Scroller mScroller = new Scroller(context, new DecelerateInterpolator()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration2) {
                    super.startScroll(startX, startY, dx, dy, duration);
                }
            };
            mField.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        removeCallbacks(task);
        postDelayed(task, DelayMillis);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(task);
        postDelayed(task, DelayMillis);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(task);
    }

    private class Task extends SimpleOnPageChangeListener implements Runnable {

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager#SCROLL_STATE_IDLE
         * @see ViewPager#SCROLL_STATE_DRAGGING
         * @see ViewPager#SCROLL_STATE_SETTLING
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case SCROLL_STATE_IDLE:
                    removeCallbacks(task);
                    postDelayed(task, DelayMillis);
                    break;
                case SCROLL_STATE_DRAGGING:
                    removeCallbacks(task);
                    break;
                case SCROLL_STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void run() {
            PagerAdapter adapter = getAdapter();
            if (adapter != null) {
                int count = adapter.getCount();
                if (count >= 2) {
                    int next = getCurrentItem() + 1;
                    if (next == count) {
                        next = 0;
                    }
                    setCurrentItem(next, true);
                    removeCallbacks(task);
                    postDelayed(this, DelayMillis);
                }
            }
        }
    }

}
