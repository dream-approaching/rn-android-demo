package com.lieying.socialappstore.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.lieying.socialappstore.BuildConfig;
import com.lieying.socialappstore.CustomToastPackage;
import com.lieying.socialappstore.utils.ClickEventUtils;
import com.microsoft.codepush.react.CodePush;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseV4Fragment extends Fragment implements BaseIViewUI {
    private StartControl mControl;
    private OnClickControl mClickControl;
    public boolean isInit = false;
    public Context mContext;
    public View mView;
    public ViewGroup mViewGroup;
    protected final String TAG = getClass().getName();

    protected abstract View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isInit) {
            mContext = getContext();
            mViewGroup = container;
            mControl = new StartControl(getActivity());
            mClickControl = new OnClickControl(this);
            mView = setContentView(inflater, container, savedInstanceState);
            findView();
            initView();
            initData();
            initListener();
            isInit = true;
        }
        return mView;
    }

    public List<Disposable> getDisposableList() {
        return mControl.getDisposables();
    }


    @Override
    public void onDestroy() {
        mControl.clearDisposables();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (!ClickEventUtils.doubleEventView(v.getId())) {
            onUnDoubleClickView(v);
        }
    }

    @Override
    public void onUnDoubleClickView(View v) {

    }

    public void onRefreshData(Object obj) {

    }

    //加入点击事件
    protected void addClickListener(int viewId) {
        addClickListener(findViewById(viewId));
    }

    public View findViewById(int viewId) {
        return mView.findViewById(viewId);
    }

    //页面可见(首次初始化不回调)
    public void onFragmentVisible() {

    }

    //页面不可见(首次初始化不回调)
    public void onFragmentHint() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isInit) {
            if (isVisibleToUser) {
                onFragmentVisible();
            } else {
                onFragmentHint();
            }
        }
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
    public void setDialog(Class<? extends BaseDialog> dialogClass) {
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