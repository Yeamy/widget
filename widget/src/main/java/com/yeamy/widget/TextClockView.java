package com.yeamy.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TextClockView extends TextView implements Runnable {

    public static final String DEFAULT_FORMAT_24_HOUR = "HH:mm";
    private String format;
    public long loop;
    private String text;

    private boolean mAttached;

    private Calendar mTime = Calendar.getInstance();

    public TextClockView(Context context) {
        super(context);
    }

    public TextClockView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0, 0);
    }

    public TextClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        init(context, attrs, defStyleAttr, 0);
    }

    public TextClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextClockView);
        String format = a.getString(R.styleable.TextClockView_format);
        format = (format == null) ? DEFAULT_FORMAT_24_HOUR : format;
        this.format = format;
        String text = a.getString(R.styleable.TextClockView_text);
        if (text != null && text.contains("%s")) {
            this.text = text;
        }
        a.recycle();
        if (format.contains("SSS")) {
            loop = 17L;
        } else if (format.contains("s")) {
            loop = 1000L;
        } else {
            loop = 60 * 1000L;
        }
    }

    public void run() {
        String time = new SimpleDateFormat(format, Locale.getDefault()).format(new Date());

        if (text == null) {
            setText(time);
        } else {
            setText(String.format(text, time));
        }

        getHandler().postDelayed(this, loop);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            mAttached = true;
            run();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            getHandler().removeCallbacks(this);
            mAttached = false;
        }
    }
}
