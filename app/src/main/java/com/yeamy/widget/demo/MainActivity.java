package com.yeamy.widget.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new MainFragment())
                .commit();
    }

    public static class MainFragment extends ListFragment {

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Adapter adapter = new Adapter();
            setListAdapter(adapter);
            getListView().setOnItemClickListener(adapter);
        }

        private class Bean {
            public Bean(Class<? extends Fragment> clazz) {
                this.clazz = clazz;
                this.name = clazz.getSimpleName();
            }

            Class<? extends Fragment> clazz;
            String name;
        }

        private class Adapter extends BaseAdapter implements AdapterView.OnItemClickListener {
            final Bean[] array = new Bean[]{
                    new Bean(CircleImageViewFragment.class),
                    new Bean(CollapsedScrollLayoutFragment.class),
                    new Bean(ImageCheckButtonFragment.class),
                    new Bean(OvalImageViewFragment.class),
                    new Bean(ScaleIndicatorFragment.class),
                    new Bean(StampViewFragment.class),
                    new Bean(TextClockViewFragment.class)
            };

            @Override
            public int getCount() {
                return array.length;
            }

            @Override
            public Bean getItem(int position) {
                return array[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(getItem(position).name);
                return convertView;
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Class<? extends Fragment> clazz = getItem(position).clazz;
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("menu")
                            .replace(R.id.content, clazz.newInstance()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}