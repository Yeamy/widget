package com.yeamy.app;

import android.content.res.Resources;
import android.view.View;

import java.lang.reflect.Field;

public class ViewBinder {

    public ViewBinder(View v) {
        try {
            bindView(this, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bindView(Object obj, View v) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        Resources res = v.getResources();
        String pkn = v.getContext().getPackageName();
        for (Field f : fields) {
            if (!f.isAccessible()) continue;
            Class<?> clz = f.getDeclaringClass();
            if (clz != null && clz.isAssignableFrom(View.class)) {
                int id;
                if (f.isAnnotationPresent(TargetView.class)) {
                    id = f.getAnnotation(TargetView.class).value();
                } else {
                    id = res.getIdentifier(f.getName(), "id", pkn);
                }
                if (id > 0) f.set(obj, v.findViewById(id));
            }
        }
    }
}
