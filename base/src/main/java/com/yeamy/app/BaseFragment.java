package com.yeamy.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;

public class BaseFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class<?> clz = getClass();
        if (clz.isAnnotationPresent(TargetLayout.class)) {
            int layout = clz.getAnnotation(TargetLayout.class).value();
            return inflater.inflate(layout, null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public final void onDestroyView() {
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                if (View.class.isAssignableFrom(field.getType())) {
                    if (field.isAccessible()) {
                        field.set(this, null);
                    } else {
                        field.setAccessible(true);
                        field.set(this, null);
                        field.setAccessible(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
        onViewDestroyed();
    }

    public void onViewDestroyed() {
    }

    @Override
    public void onClick(View v) {
    }

    public void showToast(String txt) {
        if (getContext() != null && isResumed()) {
            Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
        }
    }

}
