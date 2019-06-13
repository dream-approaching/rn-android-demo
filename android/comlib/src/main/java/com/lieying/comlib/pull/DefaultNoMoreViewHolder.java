package com.lieying.comlib.pull;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lieying.comlib.R;


/**
 * Created by ${liyi} on 2018/3/6.
 */

public class DefaultNoMoreViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvTip;
    private TextView mTvBnt;
    /**
      * @Description:     无更多加载数据时的默认view
      * @Author:         liyi
      * @CreateDate:     2019/5/21 0021 18:18
     */
    public DefaultNoMoreViewHolder(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.item_no_more, null));
        mTvTip = itemView.findViewById(R.id.tv_no_more);
    }

    /**
     * @Description:    完全无数据时的view
     * @Author:         liyi
     * @CreateDate:     2019/5/21 0021 18:18
     */
    public DefaultNoMoreViewHolder(Context context  ,  String bnt, int marginTop) {
        super(LayoutInflater.from(context).inflate(R.layout.item_no_data, null));
        mTvTip = itemView.findViewById(R.id.tv_no_data_tip);
        mTvBnt = itemView.findViewById(R.id.tv_no_data_bnt);

        if (TextUtils.isEmpty(bnt)) {
            mTvBnt.setVisibility(View.GONE);
        } else {
            mTvBnt.setVisibility(View.VISIBLE);
            mTvBnt.setText(bnt);
        }
//        if (marginTop != 0) {
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTvTip.getLayoutParams();
//            params.topMargin = marginTop;
//            mTvTip.setLayoutParams(params);
//        }
    }

    public void setTip(String tip) {
        mTvTip.setText(tip);
    }

    public void setOnClickItemListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
