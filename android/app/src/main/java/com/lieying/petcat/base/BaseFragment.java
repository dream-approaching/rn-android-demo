package com.lieying.petcat.base;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieying.petcat.utils.ClickEventUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * activity基类
 *
 * @author lcb
 * @createDate 2016-3-8
 * @createTime 下午4:27:56
 */
public abstract class BaseFragment extends Fragment implements BaseIViewUI {
    private StartControl mControl;
    private OnClickControl mClickControl;
    public boolean isInit = false;
    protected View mView;
    protected Context mContext;
    protected final String TAG = getClass().getName();

    @Override
    public void onClick(View v) {
        if (!ClickEventUtils.doubleEventView(v.getId())) {
            onUnDoubleClickView(v);
        }
    }

    @Override
    public void onUnDoubleClickView(View v) {

    }

    protected List<Disposable> getDisposableList() {
        return mControl.getDisposables();
    }


    @Override
    public void onDestroy() {
        mControl.clearDisposables();
        super.onDestroy();
    }

    //加入点击事件
    protected void addClickListener(int viewId) {
        addClickListener(findViewById(viewId));
    }


    protected View findViewById(int viewId) {
        return mView.findViewById(viewId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();//相对单列来说，有必要重新获取
        if (!isInit) {
            mControl = new StartControl(getActivity());
            mClickControl = new OnClickControl(this);
            mView = getContentView();
            findView();
            initView();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInit) {
            initData();
            initListener();
            isInit = true;
        } else {
            onDataChange();
        }

    }


    /**
     * 查找view
     *
     * @createDate 2016-3-8
     * @createTime 下午4:13:53
     * @auther lcb
     */
    public abstract View getContentView();

    /**
     * 数据改变
     */
    public void onDataChange() {
    }

    //刷新数据
    public void onRefreshData(Object obj) {

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
