package com.lieying.comlib.pull;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lieying.comlib.R;


/**
 * Created by ${liyi} on 2018/2/24.
 * 下拉demo
 */

public class SimpleRefreshHeadView extends AbRefreshHeadView {
    private ProgressBar mIvAnim;
    private TextView mTvTip;
//    private AnimationDrawable mAnim;

    public SimpleRefreshHeadView(Context context) {
        super(context);
    }

    @Override
    protected View onCreateView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.view_refresh_head, null);
    }

    @Override
    protected int onCreateRefreshLimitHeight() {
        return getScreenHeight() / 8;
    }

    @Override
    protected void initView() {
        mIvAnim = (ProgressBar) findViewFromId(R.id.iv_refreshing);
        mTvTip = (TextView) findViewFromId(R.id.tv_tip);
    }

    /**
     * 获取屏幕高度
     */
    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public void stopRefreshingAnimation() {
//        if (mAnim != null) {
//            mAnim.stop();
//            mAnim = null;
//        }
        mIvAnim.setVisibility(GONE);
    }

    public void startRefreshingAnimation() {
//        if (mAnim == null) {
////            mIvAnim.setBackgroundResource(R.drawable.anim_list_refresh);
////            mAnim = (AnimationDrawable) mIvAnim.getBackground();
//        }
//        mAnim.start();
        mIvAnim.setVisibility(VISIBLE);
    }

    @Override
    protected void onPullingDown() {
        mTvTip.setVisibility(GONE);
//        mIvAnim.setBackgroundResource(R.drawable.icon_rf_13);
        mIvAnim.setVisibility(VISIBLE);
    }

    @Override
    protected void onReleaseState() {

    }

    @Override
    protected void onRefreshing() {
        startRefreshingAnimation();
    }

    @Override
    protected void onResultSuccess() {
        stopRefreshingAnimation();
        mTvTip.setText(mContext.getString(R.string.refresh_success));
        mTvTip.setVisibility(VISIBLE);
        mIvAnim.setVisibility(GONE);
    }

    @Override
    protected void onResultFail() {
        stopRefreshingAnimation();
        mTvTip.setText(mContext.getString(R.string.refresh_fail));
        mTvTip.setVisibility(VISIBLE);
        mIvAnim.setVisibility(GONE);
    }
}
