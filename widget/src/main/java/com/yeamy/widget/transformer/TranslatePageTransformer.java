package com.yeamy.widget.transformer;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewGroup;

public class TranslatePageTransformer implements PageTransformer {
    private FloatArray factors = new FloatArray();
    private int[] ids;

    public TranslatePageTransformer() {
    }

    /**
     * @param ids the id of view to translate
     */
    public TranslatePageTransformer(int[] ids) {
        this.ids = ids;
    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        // int pageHeight = view.getHeight();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            view.setAlpha(1);
            if (ids != null) {
                int i = 0;
                for (int id : ids) {
                    View v = view.findViewById(id);
                    if (v != null) {
                        float factor = getFactors(i);
                        v.setTranslationX(position * factor * pageWidth);
                    }
                    ++i;
                }
            } else if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    float factor = getFactors(i);
                    child.setTranslationX(position * factor * pageWidth);
                }
            }

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }

    private float getFactors(int index) {
        if (factors.size() <= index) {
            float factor = (float) (Math.random() * 2);
            factors.add(factor);
            return factor;
        }
        return factors.get(index);
    }

    private static class FloatArray {

        private final static int INCREMENT = 12;
        private final static float[] NULL = {};

        private float[] array;
        private int size = 0;

        public FloatArray() {
            array = NULL;
        }

        public int size() {
            return size;
        }

        public void add(float f) {
            if (array.length == size) {
                float[] array = new float[size + INCREMENT];
                System.arraycopy(this.array, 0, array, 0, size);
                this.array = array;
            }
            array[size] = f;
            ++size;
        }

        public float get(int index) {
            if (index < array.length) {
                return array[index];
            }
            throw new IndexOutOfBoundsException();
        }

    }

}
