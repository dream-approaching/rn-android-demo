package com.lieying.socialappstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lieying.socialappstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/13 0013 18:31
 */
public class ReportAdatper extends BaseAdapter {
    public String[] list;
    private LayoutInflater mInflater;//布局装载器对象
    private ClickItem clickItem;
    public ReportAdatper(Context context , ClickItem clickItem){
        this.list = new String[]{context.getString(R.string.string_report_ad), context.getString(R.string.string_report_chaofeng),context.getString(R.string.string_report_wuru)
                ,context.getString(R.string.string_report_yinhui),context.getString(R.string.string_report_weifa),context.getString(R.string.string_report_chaoxi),context.getString(R.string.string_report_other)};
        mInflater = LayoutInflater.from(context);
        this.clickItem = clickItem;
    }
    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_report_dialog,null);
        TextView titleTextView = (TextView) view.findViewById(R.id.tv_item_report_dialog);
        titleTextView.setText( list[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.click(position);
            }
        });
        return view;
    }

    public interface ClickItem{
        public void click(int position);
    }
}
