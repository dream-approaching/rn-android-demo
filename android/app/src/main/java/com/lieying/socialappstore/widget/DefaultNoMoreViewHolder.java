package com.lieying.socialappstore.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lieying.socialappstore.R;


/**
 * Created by ${liyi} on 2018/3/6.
 */

public class DefaultNoMoreViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvTip;

    public DefaultNoMoreViewHolder(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.item_no_more, null));
        mTvTip = itemView.findViewById(R.id.tv_no_more);
    }

    public void setTip(String tip) {
        mTvTip.setText(tip);
    }

    public void setOnClickItemListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
