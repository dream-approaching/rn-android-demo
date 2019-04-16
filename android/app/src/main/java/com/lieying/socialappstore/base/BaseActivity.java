package com.lieying.socialappstore.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.lieying.socialappstore.utils.ActivityUtils;
import com.lieying.socialappstore.utils.ClickEventUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * activity基类
 *
 * @author lcb
 * @createDate 2016-3-8
 * @createTime 下午4:27:56
 */
public abstract class BaseActivity extends Activity implements BaseIViewUI {
    private StartControl mControl;
    private OnClickControl mClickControl;
    protected Context mContext;
    protected final String TAG = getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mControl = new StartControl(this);
        ActivityUtils.addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (!isInterceptStart()) {
            mClickControl = new OnClickControl(this);
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
        mControl.clearDisposables();
        super.onDestroy();
    }

    /**
     * @param savedInstanceState
     */
    protected abstract void setContentView(Bundle savedInstanceState);


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
