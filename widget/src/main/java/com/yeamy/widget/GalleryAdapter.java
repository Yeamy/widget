package com.yeamy.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * a infinite scrolling ViewPager Adapter
 */
public abstract class GalleryAdapter extends FragmentPagerAdapter {

    public GalleryAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setViewPager(ViewPager pager) {
        pager.setAdapter(this);
        pager.setCurrentItem(getMiddleItem());
    }

    public abstract int getItemCount();

    public abstract Fragment getFragment(int position);

    public int getMiddleItem() {
        int count = getItemCount();
        int rate = Integer.MAX_VALUE / count / 2;
        return rate * count;
    }

    @Override
    public final Fragment getItem(int position) {
        return getFragment(position % getItemCount());
    }

    @Override
    public final int getCount() {
        return getItemCount() == 0 ? 0 : Integer.MAX_VALUE;
    }
}