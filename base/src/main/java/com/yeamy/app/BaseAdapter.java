package com.yeamy.app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BaseAdapter<T> extends android.widget.BaseAdapter implements AdapterView.OnItemClickListener {
    public ArrayList<T> data = new ArrayList<>();

    public void clear() {
        data.clear();
    }

    public void add(T src) {
        data.add(src);
    }

    public void addAll(T[] src) {
        Collections.addAll(data, src);
    }

    public void addAll(Collection<T> src) {
        data.addAll(src);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null && getClass().isAnnotationPresent(TargetLayout.class)) {
            int layout = getClass().getAnnotation(TargetLayout.class).value();
            convertView = View.inflate(parent.getContext(), layout, null);
        }
        onBindItem(convertView, position);
        return convertView;
    }

    protected void onBindItem(View convertView, int position) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public static int getHeaderViewsCount(AdapterView<?> parent) {
        if (parent instanceof ListView) {
            ListView lv = (ListView) parent;
            return lv.getHeaderViewsCount();
        }
        return 0;
    }
}
