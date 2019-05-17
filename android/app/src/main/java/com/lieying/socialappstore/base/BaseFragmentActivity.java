package com.lieying.socialappstore.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.lieying.socialappstore.utils.ActivityUtils;
import com.lieying.socialappstore.utils.ClickEventUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseFragmentActivity extends FragmentActivity implements BaseIViewUI {
    private StartControl mControl;
    private OnClickControl mClickControl;
    protected Context mContext;
    protected FragmentManager mFgmManager;
    protected final String TAG = getClass().getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        mContext = this;
        mControl = new StartControl(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (!isInterceptStart()) {
            mClickControl = new OnClickControl(this);
            mFgmManager = getSupportFragmentManager();
            setContentView(savedInstanceState);
            findView();
            initView();
            initData();
            initListener();
        } else {
            finish();
        }
    }

    //是否拦截启动
    protected boolean isInterceptStart() {
        return false;
    }

    protected List<Disposable> getDisposableList() {
        return mControl.getDisposables();
    }


    @Override
    protected void onDestroy() {
        ActivityUtils.removeActivity(this);
        mControl.clearDisposables();
        super.onDestroy();
    }

    protected abstract void setContentView(Bundle bundle);

    @Override
    public void onClick(View v) {
        if (!ClickEventUtils.doubleEventView(v.getId())) {
            onUnDoubleClickView(v);
        }
    }

    @Override
    public void onUnDoubleClickView(View v) {

    }

    //加入点击事件
    protected void addClickListener(int viewId) {
        addClickListener(findViewById(viewId));
    }

    @Override
    public void addClickListener(View view) {
        mClickControl.addClickListener(view);
    }

    @Override
    public void startActivity(Class<?> targetActivity, int... flag) {
        mControl.startActivity(targetActivity, flag);
    }

    @Override
    public void startActivity(Class<?> targetActivity, Bundle bundle, int... flag) {
        mControl.startActivity(targetActivity, bundle, flag);
    }

    @Override
    public void startActivity(Class<?> targetActivity, int requestCode, int... flag) {
        mControl.startActivity(targetActivity, requestCode, flag);
    }

    @Override
    public void startActivity(Class<?> targetActivity, Bundle bundle, int requestCode, int... flag) {
        mControl.startActivity(targetActivity, bundle, requestCode, flag);
    }


    @Override
    public void setTip(String tip) {
        mControl.getDialog().setTip(tip);
    }

    @Override
    public void onDismissDialog(String tip, long delayTime) {
        mControl.getDialog().onDismissDialog(tip, delayTime);
    }

    @Override
    public void onDismissDialog() {
        mControl.getDialog().onDismissDialog();
    }

    @Override
    public boolean onShowDialog(String tip) {
        return mControl.getDialog().onShowDialog(tip);
    }

    @Override
    public boolean onShowDialog() {
        return mControl.getDialog().onShowDialog();
    }

    @Override
    public void setDialog(Class<? extends BaseDialogActivity> dialogClass) {
        mControl.setDialog(dialogClass);
    }

    @Override
    public void setDialogShowListener(DialogInterface.OnShowListener listener) {
        mControl.getDialog().setOnShowListener(listener);
    }

    @Override
    public void setDialogDismissListener(DialogInterface.OnDismissListener listener) {
        mControl.getDialog().setOnDismissListener(listener);
    }
}