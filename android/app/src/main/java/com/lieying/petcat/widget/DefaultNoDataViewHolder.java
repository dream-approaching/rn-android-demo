package com.lieying.petcat.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lieying.petcat.R;


/**
 * Created by ${liyi} on 2018/3/6.
 */

public class DefaultNoDataViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvTip;
    private TextView mTvBnt;

    public DefaultNoDataViewHolder(Context context, String tips, String bnt, int marginTop) {
        super(LayoutInflater.from(context).inflate(R.layout.item_no_data, null));
        mTvTip = itemView.findViewById(R.id.tv_no_data_tip);
        mTvBnt = itemView.findViewById(R.id.tv_no_data_bnt);

        mTvTip.setText(tips);
        if (TextUtils.isEmpty(bnt)) {
            mTvBnt.setVisibility(View.GONE);
        } else {
            mTvBnt.setVisibility(View.VISIBLE);
            mTvBnt.setText(bnt);
        }
        if (marginTop != 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTvTip.getLayoutParams();
            params.topMargin = marginTop;
            mTvTip.setLayoutParams(params);
        }
    }

    public void setTip(String tip) {
        mTvTip.setText(tip);
    }

    public void setOnClickItemListener(View.OnClickListener listener) {
        mTvBnt.setOnClickListener(listener);
    }
}
