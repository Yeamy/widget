package com.yeamy.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Auto sliding ViewPager, will auto start and auto stop
 */
public class Gallery extends ViewPager {
    private final static long DelayMillis = 3500L;
    private Task task = new Task();

    public Gallery(Context context) {
        super(context);
        addOnPageChangeListener(task);
        setScroller(context, 700);
    }

    public Gallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(task);
        setScroller(context, 700);
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
        task.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        task.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        task.stop();
    }

    private class Task extends SimpleOnPageChangeListener implements Runnable {
        private boolean RUN;

        public void start() {
            RUN = true;
            removeCallbacks(task);
            postDelayed(this, DelayMillis);
        }

        public void stop() {
            RUN = false;
            removeCallbacks(this);
        }

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
            if (RUN) {
                PagerAdapter adapter = getAdapter();
                if (adapter != null) {
                    try {// 老是喜欢报错!为什么?
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
