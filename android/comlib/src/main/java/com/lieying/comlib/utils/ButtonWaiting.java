package com.lieying.comlib.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lieying.comlib.R;


/**
 * Created by ${liyi} on 2018/3/7.
 */

public class ButtonWaiting<T> {
    private Context mContext;
    private ImageView mIvAnim;
    private boolean isWaiting = false;
    private OnWaitingListener<T> mListener;


    public ButtonWaiting(Context context, ImageView imageView) {
        this.mContext = context;
        this.mIvAnim = imageView;
        mIvAnim.setVisibility(View.GONE);
    }

    public void setOnWaitingListener(OnWaitingListener<T> listener) {
        this.mListener = listener;
    }

    public synchronized boolean startWaiting() {
        if (!isWaiting) {
            if (mListener != null) {
                mListener.onStartWaiting();
            }
            mIvAnim.setVisibility(View.VISIBLE);
            mIvAnim.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotate));
            isWaiting = true;
            return true;
        }
        return false;
    }

    public synchronized boolean stopWaiting(T t) {
        if (isWaiting) {
            mIvAnim.clearAnimation();
            if (mListener != null) {
                mListener.onStopWaiting(t);
            }
            mIvAnim.setVisibility(View.GONE);
            isWaiting = false;
            return true;
        }
        return false;
    }

    public interface OnWaitingListener<T> {
        void onStartWaiting();

        void onStopWaiting(T t);
    }
}
