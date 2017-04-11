package com.yeamy.app;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetLayout {
    @LayoutRes
    int value();
}
