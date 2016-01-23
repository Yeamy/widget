package com.yeamy.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeamy.widget.transformer.CubePageTransformer;
import com.yeamy.widget.transformer.DefaultPageTransformer;
import com.yeamy.widget.transformer.DepthPageTransformer;
import com.yeamy.widget.transformer.LowerFanPageTransformer;
import com.yeamy.widget.transformer.TranslatePageTransformer;
import com.yeamy.widget.transformer.TurnstilePageTransformer;
import com.yeamy.widget.transformer.UpperFanPageTransformer;
import com.yeamy.widget.transformer.ZoomOutPageTransformer;

public class PageTransformerFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewPager pager = new ViewPager(inflater.getContext());
        pager.setId(R.id.pager);
        return mViewPager = pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewPager = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pagetransformer, menu);
    }

    // Select different effects of page transformer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_transformer_default:
                mViewPager.setPageTransformer(true, new DefaultPageTransformer());
                break;
            case R.id.action_transformer_depth:
                mViewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case R.id.action_transformer_zoomout:
                mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                break;
            case R.id.action_transformer_upperfan:
                mViewPager.setPageTransformer(true, new UpperFanPageTransformer());
                break;
            case R.id.action_transformer_lowerfan:
                mViewPager.setPageTransformer(true, new LowerFanPageTransformer());
                break;
            case R.id.action_transformer_cube:
                mViewPager.setPageTransformer(true, new CubePageTransformer());
                break;
            case R.id.action_transformer_turnstile:
                mViewPager.setPageTransformer(true, new TurnstilePageTransformer());
                break;
            case R.id.action_transformer_translate:
                mViewPager.setPageTransformer(true, new TranslatePageTransformer());
                break;
        }
        mViewPager.setAdapter(mSectionsPagerAdapter);// Reset adapter to fix the bug
        return super.onOptionsItemSelected(item);
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        public static class PlaceholderFragment extends Fragment {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.pagetransformer_fragment, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                int position = getArguments().getInt(ARG_SECTION_NUMBER);
                textView.setText("Hello World from section: " + (position + 1));
                int[] colors = {0xfff9bdbb, 0xff80cbc4, 0xffb3e6fc};
                rootView.setBackgroundColor(colors[position]);
                return rootView;
            }
        }
    }

}
