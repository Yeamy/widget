package com.yeamy.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeamy.widget.OnPageScrolledListener;
import com.yeamy.widget.ScaleIndicatorLayout;

public class ScaleIndicatorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scaleindicator, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new Adapter());
        final ScaleIndicatorLayout indicator =
                (ScaleIndicatorLayout) view.findViewById(R.id.indicator);

        OnPageScrolledListener listener = new OnPageScrolledListener() {

            @Override
            public void onPageScrolled(int curItem, int nextItem, float radio) {
                indicator.setRadio(curItem, 1f - radio);
                indicator.setRadio(nextItem, radio);
            }
        };
        listener.setViewPager(pager);
        int curIndex = 0;
        pager.setCurrentItem(curIndex);
        indicator.setRadio(curIndex, 1f);
    }

    private class Adapter extends FragmentStatePagerAdapter {
        int[] colors = {0xffff0000, 0xff00ff00, 0xff0000ff};

        public Adapter() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            SubFragment fragment = new SubFragment();
            fragment.color = colors[position];
            return fragment;
        }

        @Override
        public int getCount() {
            return colors.length;
        }
    }

    public static class SubFragment extends Fragment {
        int color;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = new View(inflater.getContext());
            view.setBackgroundColor(color);
            return view;
        }
    }
}
