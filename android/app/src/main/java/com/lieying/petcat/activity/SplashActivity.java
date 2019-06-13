package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.petcat.MainActivity;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(false).init();
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void findView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        MyHandler myHandler = new MyHandler(this);
        myHandler.sendEmptyMessageDelayed(1, 1800);
    }

    @Override
    public void initListener() {

    }

    private static class MyHandler extends Handler {
        WeakReference<SplashActivity> mWeakReference;

        public MyHandler(SplashActivity activity) {
            mWeakReference = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = mWeakReference.get();
            if (activity != null) {
                activity.goHome();
            }
        }
    }

    public void goHome() {
        MainActivity.startActivity(this);
        finish();
    }
}
