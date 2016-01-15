package com.yeamy.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形ImageView
 * @author Yeamy0754
 *
 */
public class OvalImageView extends ImageView {

	public OvalImageView(Context context) {
		super(context);
	}

	public OvalImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OvalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap bmp;
		{
			bmp = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
			Canvas cc = new Canvas(bmp);
			super.onDraw(cc);
			canvas.save();
		}
		BitmapShader bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		// 构建ShapeDrawable对象并定义形状为椭圆
		ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
		// 得到画笔并设置渲染器
		shapeDrawable.getPaint().setShader(bitmapShader);
		// 设置显示区域
		shapeDrawable.setBounds(0, 0, getWidth(), getHeight());
		// 绘制shapeDrawable
		shapeDrawable.draw(canvas);
	}
}
