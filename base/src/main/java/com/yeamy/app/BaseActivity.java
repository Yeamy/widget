package com.yeamy.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity implements View.OnClickListener {
    private boolean resume;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(TargetLayout.class)) {
            int layout = getClass().getAnnotation(TargetLayout.class).value();
            setContentView(layout);
        }
        resume = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        resume = false;
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * home按钮
     */
    public void finish(View v) {
        finish();
    }

    public void showToast(@StringRes int txt) {
        showToast(getString(txt));
    }

    public void showToast(CharSequence txt) {
        if (resume) {
            if (toast == null) {
                toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
            } else {
                toast.setText(txt);
            }
            toast.show();
        }
    }

    public Context getContext() {
        return this;
    }
}
