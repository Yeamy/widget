package com.yeamy.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 选择图片的控件,
 */
public class ImagePicker extends SquareLayout {
    public static final int RequestCode = 78;
    private int limit;

    public ImagePicker(Context context) {
        super(context);
        limit = 9;
        initButtonAdd(context, android.R.drawable.ic_menu_add);
    }

    public ImagePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImagePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressWarnings("NewApi")
    public ImagePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImagePicker);
        limit = a.getInt(R.styleable.ImagePicker_limit, 9);
        int resId = (int) a.getDimension(R.styleable.ImagePicker_btnAdd,
                android.R.drawable.ic_menu_add);
        a.recycle();
        initButtonAdd(context, resId);
    }

    // add the add-view
    private void initButtonAdd(Context context, int resId) {
        ImageView view = new ImageView(context);
        view.setId(android.R.id.icon);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setOnClickListener(childListener);
        view.setBackgroundResource(resId);
        addView(view);

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 必须在 Activity#onActivityResult 调用
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String file = uri2File(getContext(), uri);
            if (file != null) {
                addPhoto(file);
            }
        }
    }

    /**
     * @return 所有已选择的图片
     */
    public String[] getFiles() {
        int count = getChildCount() - 1;
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = getChildAt(i).getTag().toString();
        }
        return result;
    }

    private OnClickListener childListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == android.R.id.icon) {
                Context context = getContext();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//API-18及以上才支持选择多文件
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    activity.startActivityForResult(intent, RequestCode);
                }
            } else {
                getChildAt(getChildCount() - 1).setVisibility(View.VISIBLE);
                removeView(v);
                ImageView img = (ImageView) v;
                BitmapDrawable draw = (BitmapDrawable) img.getDrawable();
                draw.getBitmap().recycle();
                System.gc();
            }
        }
    };

    private void addPhoto(String file) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;//设置为true,表示解析Bitmap对象，该对象不占内存
        BitmapFactory.decodeFile(file, opts);

        BitmapFactory.Options req = new BitmapFactory.Options();

        req.inSampleSize = opts.outWidth * getColumnCount() / getWidth();//设置缩放比例
        req.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(file, req);

        // add view to last
        ImageView view = new ImageView(getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setImageBitmap(bmp);
        view.setOnClickListener(childListener);
        addView(view, getChildCount() - 1);
        // if reach the limit, hide the add-view
        if (getChildCount() - 1 == limit) {
            getChildAt(limit).setVisibility(View.INVISIBLE);
        }
        view.setTag(file);//图片地址保存在tag
    }

    private static String uri2File(Context context, Uri uri) {
        String file = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                file = cursor.getString(column);
            }
            cursor.close();
        }
        return file;
    }

}
