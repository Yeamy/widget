package com.yeamy.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 根据图片宽高和自身宽度计算高度, 达到自动适应
 */
public class BmpImageView extends ImageView {

    public BmpImageView(Context context) {
        super(context);
    }

    public BmpImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);
    }

    public BmpImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = getHeight(drawable, w);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setHeight(getDrawable());
    }

    private int getHeight(Drawable drawable, int w) {
        if (w > 0) {
            int bw = drawable.getMinimumWidth();
            int bh = drawable.getMinimumHeight();
            return w * bh / bw;
        }
        return 0;
    }

    private void setHeight(Drawable drawable) {
        int w = getWidth();
        if (w > 0) {
            if (drawable != null) {
                int bw = drawable.getMinimumWidth();
                int bh = drawable.getMinimumHeight();
                int h = w * bh / bw;
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = h;
                setLayoutParams(params);
            }
        }
    }

}
