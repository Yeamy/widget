package com.yeamy.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 图片添加邮票剪边效果
 *
 * @author Yeamy0754
 */
public class StampView extends ImageView {
    @SuppressLint("RtlHardcoded")
    public static final int LEFT = 0x1, RIGHT = 0x2, TOP = 0x4, BOTTOM = 0x8, ALL = LEFT | RIGHT | TOP | BOTTOM;
    private final float defaultDotSize = 10f;
    private final float defaultDistance = 5f;

    private Paint drawPaint;
    private Paint clearPaint;
    private float dotSize;// 点的半径 dotRadius
    private float distance;// 两点距离
    private int sides;

    public StampView(Context context) {
        super(context);
        dotSize = defaultDotSize;// 点的半径 dotRadius
        distance = defaultDistance;// 两点距离
        sides = ALL;
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public StampView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StampView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("NewApi")
    public StampView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StampView);
        dotSize = a.getDimension(R.styleable.StampView_dotSize, defaultDotSize);
        distance = a.getDimension(R.styleable.StampView_distance, defaultDistance);
        sides = a.getInt(R.styleable.StampView_sides, ALL);
        a.recycle();
    }

    /**
     * which side to draw dot <br>
     * 设置要画点的边
     *
     * @param sides the sides to draw dots such as #LEFT|#RIGHT
     * @see #ALL
     * @see #LEFT
     * @see #RIGHT
     * @see #TOP
     * @see #BOTTOM
     */
    public void setSides(int sides) {
        this.sides = sides;
    }

    /**
     * set the size of the dot<br>
     * 设置点的大小
     *
     * @param dotSize dots' size
     */
    public void setDotSize(float dotSize) {
        this.dotSize = dotSize;
    }

    /**
     * set the distance between two dots <br>
     * 设置两点间的距离
     *
     * @param distance the distance between two dots
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int flags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        int w = getWidth();
        int h = getHeight();

        int sc = canvas.saveLayer(0, 0, w, h, drawPaint, flags);
        super.onDraw(canvas);

        drawPoints(canvas, clearPaint, w, h);

        canvas.restoreToCount(sc);
    }

    private void drawPoints(Canvas canvas, Paint paint, int w, int h) {
        float per = dotSize + distance;// 每单位距离
        int countX = (int) Math.ceil(w / per);
        int countY = (int) Math.ceil(h / per);

        RectF rectf = new RectF();
        float dx = 0f;//dotSize * 0.1f;// 偏移

        // draw top
        if ((sides & LEFT) > 0) {
            float top = -dotSize / 2 - dx;
            float bottom = top + dotSize;
            for (int i = 0; i < countY; i++) {
                float left = i * per + distance;
                float right = left + dotSize;
                rectf.set(left, top, right, bottom);
                canvas.drawOval(rectf, paint);
            }
        }
        // draw bottom
        if ((sides & BOTTOM) > 0) {
            float top = h - dotSize / 2 + dx;
            float bottom = top + dotSize;
            for (int i = 0; i < countX; i++) {
                float left = i * per + distance;
                float right = left + dotSize;
                rectf.set(left, top, right, bottom);
                canvas.drawOval(rectf, paint);
            }
        }
        // draw left
        if ((sides & LEFT) > 0) {
            float left = -dotSize / 2 - dx;
            float right = left + dotSize;
            for (int i = 0; i < countY; i++) {
                float top = i * per + distance;
                float bottom = top + dotSize;
                rectf.set(left, top, right, bottom);
                canvas.drawOval(rectf, paint);
            }
        }
        // draw right
        if ((sides & RIGHT) > 0) {
            float left = w - dotSize / 2 + dx;
            float right = left + dotSize;
            for (int i = 0; i < countY; i++) {
                float top = i * per + distance;
                float bottom = top + dotSize;
                rectf.set(left, top, right, bottom);
                canvas.drawOval(rectf, paint);
            }
        }
    }

}
