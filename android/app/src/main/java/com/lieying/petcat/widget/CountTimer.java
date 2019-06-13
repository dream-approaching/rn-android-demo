package com.lieying.petcat.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by ${liyi} on 2018/2/3.
 * 一个需要控件的时间定时器
 */

public class CountTimer {
    private boolean isStart = false;
    private Context mContext;
    private String mFinishTip;//完成后的提示
    private String mUnits;// 数字单位例如 60s
    private TextView mTextV;
    private long mCountTime;//总时间
    private long mTimeSpace;//时间间隔
    private int mStartColorResId = -1;
    private int mEndColorResId = -1;
    private CountDownTimer mTimer;
    private OnTimerListener mListener;


    public CountTimer(Context context, TextView view, long countTime, long timeSpace) {
        this.mContext = context;
        this.mTextV = view;
        this.mCountTime = countTime;
        this.mTimeSpace = timeSpace;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setTimerListener(OnTimerListener listener) {
        this.mListener = listener;
    }

    /**
     * 倒计时完成的文字切换
     *
     * @param finishTip
     */
    public void setFinishTip(String finishTip) {
        this.mFinishTip = finishTip;
    }

    /**
     * 数字后缀 例如 60s
     *
     * @param units
     */
    public void setUnits(String units) {
        this.mUnits = units;
    }

    /**
     * 颜色切换
     *
     * @param startResId
     * @param endResId
     */
    public void setColor(int startResId, int endResId) {
        mStartColorResId = startResId;
        mEndColorResId = endResId;
    }

    //触发定时事件
    public synchronized void start() {
        if (mTextV.isEnabled()) {
            isStart = true;
            if (mListener != null) {
                mListener.onStartTimer();
            }
            mTextV.setEnabled(false);
            if (mStartColorResId != -1) {
                mTextV.setTextColor(ContextCompat.getColor(mContext, mStartColorResId));
            }
            stop();
            startTimer();
        }
    }

    //执行定时器
    private void startTimer() {
        mTimer = new CountDownTimer(mCountTime, mTimeSpace) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!TextUtils.isEmpty(mUnits)) {
                    mTextV.setText(String.valueOf(millisUntilFinished / mTimeSpace) + mUnits);
                } else {
                    mTextV.setText(String.valueOf(millisUntilFinished / mTimeSpace));
                }
                if (mListener != null) {
                    mListener.onTickTimer(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                stop();
            }
        };
        mTimer.start();
    }

    //关闭定时器
    public void stop() {
        if (mTimer != null) {
            if (mEndColorResId != -1) {
                mTextV.setTextColor(ContextCompat.getColor(mContext, mEndColorResId));
            }

            if (!TextUtils.isEmpty(mFinishTip)) {
                mTextV.setText(mFinishTip);
            }

            mTextV.setEnabled(true);
            if (mListener != null) {
                mListener.onFinishTimer();
            }
            isStart = false;
            mTimer.cancel();
            mTimer = null;
        }

    }

    public interface OnTimerListener {

        void onStartTimer();

        void onTickTimer(long millisUntilFinished);

        void onFinishTimer();
    }
}
