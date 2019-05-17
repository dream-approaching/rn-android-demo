package com.lieying.socialappstore.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.socialappstore.MainActivity;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {


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
