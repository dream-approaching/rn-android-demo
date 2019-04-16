package com.lieying.socialappstore.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ${ChenJC} on 2018/1/26.
 */

interface BaseIViewUI extends View.OnClickListener {

    /**
     * 查找view
     */
    void findView();

    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化监听器
     */
    void initListener();

    /**
     * 加入点击事件
     *
     * @param view
     */
    void addClickListener(View view);

    void onUnDoubleClickView(View view);

    /**
     * @param targetActivity
     * @param flag
     */
    void startActivity(Class<?> targetActivity, int... flag);

    /**
     * @param targetActivity
     * @param bundle
     * @param flag
     */
    void startActivity(Class<?> targetActivity, Bundle bundle, int... flag);

    /**
     * @param targetActivity
     * @param requestCode
     * @param flag
     */
    void startActivity(Class<?> targetActivity, int requestCode, int... flag);

    /**
     * @param targetActivity
     * @param bundle
     * @param requestCode
     * @param flag
     */
    void startActivity(Class<?> targetActivity, Bundle bundle, int requestCode, int... flag);

    void setDialog(Class<? extends BaseDialog> dialogClass);

    boolean onShowDialog();

    boolean onShowDialog(String tip);

    void onDismissDialog();

    void onDismissDialog(String tip, long delayTime);

    void setTip(String tip);

    void setDialogDismissListener(DialogInterface.OnDismissListener listener);

    void setDialogShowListener(DialogInterface.OnShowListener listener);
}
