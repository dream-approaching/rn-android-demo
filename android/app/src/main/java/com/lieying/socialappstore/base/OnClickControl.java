package com.lieying.socialappstore.base;

import android.view.View;

/**
 * Created by ${liyi} on 2018/1/26.
 */

public class OnClickControl {
    private View.OnClickListener mOnClickListener;

    public OnClickControl(View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    //加入点击事件
    protected void addClickListener(View v) {
        v.setOnClickListener(mOnClickListener);
    }
}
