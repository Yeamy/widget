package com.yeamy.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yeamy.widget.SlideMenuLayout;
import com.yeamy.widget.SlideMenuMaskView;

public class SlideMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slidemenu, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView lv = (ListView) view.findViewById(android.R.id.list);
        SlideMenuMaskView maskView = (SlideMenuMaskView) view.findViewById(R.id.mask);
        lv.setAdapter(new Adapter(maskView));
    }

    private class Adapter extends BaseAdapter {
        private SlideMenuMaskView maskView;

        public Adapter(SlideMenuMaskView maskView) {
            this.maskView = maskView;
        }

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.li_slidemenu, null);
            }
            SlideMenuLayout menu = (SlideMenuLayout) convertView.findViewById(R.id.menu);
            menu.setMaskView(maskView);
//            TextView title = (TextView) convertView.findViewById(android.R.id.title);
//            title.setText(String.valueOf(position));
            return convertView;
        }
    }
}
