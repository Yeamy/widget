package com.yeamy.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static android.view.MotionEvent.*;

import android.view.View;

public class SlideMenuMaskView extends View {
    private Rect touchRect = new Rect();
    private Rect iRect = new Rect();
    private SlideMenuLayout menu;

    public SlideMenuMaskView(Context context) {
        super(context);
        init();
    }

    public SlideMenuMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenuMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setVisibility(INVISIBLE);
    }

    void open(SlideMenuLayout menu) {
        if (getVisibility() == VISIBLE) {
            return;
        }
        setVisibility(VISIBLE);
        this.menu = menu;
        menu.getGlobalVisibleRect(touchRect);
        getGlobalVisibleRect(iRect);
        int left = touchRect.left - iRect.left;
        int top = touchRect.top - iRect.top;
        touchRect.set(left, top, left + touchRect.width(), top + touchRect.height());
    }

    void close() {
        setVisibility(INVISIBLE);
        this.menu = null;
    }

    private boolean mDoClosed = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case ACTION_DOWN:
                if (touchRect.contains((int) event.getX(), (int) event.getY())) {
                    mDoClosed = false;
                } else {
                    menu.close();
                    mDoClosed = true;
                }
            default:
                return mDoClosed;
        }
    }

}
