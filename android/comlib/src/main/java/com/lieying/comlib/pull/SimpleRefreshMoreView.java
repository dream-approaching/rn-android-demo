package com.lieying.comlib.pull;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lieying.comlib.R;


/**
 * Created by ${liyi} on 2018/2/24.
 * 上拉更多
 */

public class SimpleRefreshMoreView extends AbRefreshMoreView {
    private ProgressBar mIvAnim;
    private TextView mTvTip;

    public SimpleRefreshMoreView(Context context) {
        super(context);
    }

    @Override
    protected void onNormalState() {
        mTvTip.setText(mContext.getString(R.string.up_load_more));
        stopAnimation();
        mTvTip.setVisibility(VISIBLE);
        mIvAnim.setVisibility(GONE);
    }

    @Override
    protected void onLoadingMore() {
        mTvTip.setVisibility(GONE);
        mIvAnim.setVisibility(VISIBLE);
        startAnimation();
    }

    @Override
    protected void onResultSuccess() {
        stopAnimation();
        mTvTip.setText(mContext.getString(R.string.load_more_s));
        onResult();
    }

    @Override
    protected void onResultFail() {
        stopAnimation();
        mTvTip.setText(mContext.getString(R.string.load_more_f));
        onResult();
    }

    private void onResult() {
        mTvTip.setVisibility(VISIBLE);
        mIvAnim.setVisibility(GONE);
    }

    private void startAnimation() {
        mIvAnim.setVisibility(VISIBLE);
    }

    public void stopAnimation() {
        mIvAnim.setVisibility(GONE);
    }

    @Override
    protected View onCreateView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.view_refresh_more, null);
    }

    @Override
    protected void initView() {
        mIvAnim = (ProgressBar) findViewFromId(R.id.iv_anim);
        mTvTip = (TextView) findViewFromId(R.id.tv_tip);
    }
}
