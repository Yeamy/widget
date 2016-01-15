package com.yeamy.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * 圆形ImageView,新的View包括白色背景环形,阴影效果
 *
 * @author Yeamy0754
 */
public class CircleImageView extends ImageView {

    public CircleImageView(Context context) {
        super(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int edgeWidth = ViewUtils.dip2Px(dm, 4f);
        init(dm, 0xFFFAFAFA, edgeWidth);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private float shadowRadius;
    private int shadowColor = 0x3D000000;
    private int edgeWidth;
    private Paint shadowPaint, backgroundPaint, srcPaint;
    private RectF rectF = new RectF();

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.CircleImageView, defStyleAttr, defStyleRes);
        int edgeColor = a.getColor(R.styleable.CircleImageView_edgeColor, 0xFFFAFAFA);
        int edgeWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_edgeWidth, -1);
        a.recycle();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (edgeWidth < 0) {
            edgeWidth = ViewUtils.dip2Px(dm, 4f);
        }
        init(dm, edgeColor, edgeWidth);
    }

    private void init(DisplayMetrics dm, int edgeColor, int edgeWidth) {
        this.edgeWidth = edgeWidth;
        this.shadowRadius = ViewUtils.dip2Point(dm, 3.5f);

        int shadowXOffset = 0;
        int shadowYOffset = ViewUtils.dip2Px(dm, 1.7f);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(edgeColor);
        backgroundPaint.setShadowLayer(shadowRadius, shadowXOffset, shadowYOffset, shadowColor);
        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, backgroundPaint);

        shadowPaint = new Paint();
        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buildDrawingCache(true);//?
        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, shadowPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        RadialGradient mRadialGradient = new RadialGradient(w / 2, h / 2,
                shadowRadius, shadowColor, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        shadowPaint.setShader(mRadialGradient);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        float padding = shadowRadius;

        rectF.set(0, 0, w, h);
        canvas.drawOval(rectF, shadowPaint);
        rectF.set(padding, padding, w - padding, h - padding);
        canvas.drawOval(rectF, backgroundPaint);

        padding = shadowRadius + edgeWidth;
        Bitmap bmp;
        {
            float bw = w - 2 * padding;
            float bh = h - 2 * padding;
            bmp = Bitmap.createBitmap((int) bw, (int) bh, Bitmap.Config.ARGB_8888);
            Canvas cc = new Canvas(bmp);
            super.onDraw(cc);
            canvas.save();
        }
        BitmapShader bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        srcPaint.setShader(bitmapShader);
        rectF.set(padding, padding, w - padding, h - padding);
        canvas.drawOval(rectF, srcPaint);
        srcPaint.setShader(null);
    }

}
