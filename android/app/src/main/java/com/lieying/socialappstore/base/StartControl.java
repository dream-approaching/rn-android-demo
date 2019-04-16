package com.lieying.socialappstore.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by ${ChenJC} on 2018/1/26.
 */

public class StartControl {
    private Activity mActivity;
    private BaseDialog mDialog;

        private List<Disposable> mDisposables;

    public StartControl(Activity activity) {
        this.mActivity = activity;
        this.mDisposables = new ArrayList<>();
    }



    protected void clearDisposables() {
        for (Disposable disposable : mDisposables) {
            disposable.dispose();
        }
    }

    protected List<Disposable> getDisposables() {
        return mDisposables;
    }

    public void setDialog(Class<? extends BaseDialog> dialogClass) {
        try {
            Constructor st = dialogClass.getConstructor(Context.class);
            this.mDialog = (BaseDialog) st.newInstance(mActivity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public BaseDialog getDialog() {
        return mDialog;
    }

    /**
     * @param targetActivity
     * @param flag
     */
    public void startActivity(Class<?> targetActivity, int... flag) {
        startActivity(targetActivity, null, Integer.MAX_VALUE, flag);
    }

    /**
     * @param targetActivity
     * @param bundle
     * @param flag
     */
    public void startActivity(Class<?> targetActivity, Bundle bundle, int... flag) {
        Intent intent = new Intent();
        intent.setClass(mActivity, targetActivity);
        startActivity(targetActivity, bundle, Integer.MAX_VALUE, flag);
    }

    /**
     * @param targetActivity
     * @param requestCode
     * @param flag
     */
    public void startActivity(Class<?> targetActivity, int requestCode, int... flag) {
        Intent intent = new Intent();
        intent.setClass(mActivity, targetActivity);
        startActivity(targetActivity, null, requestCode, flag);
    }

    /**
     * @param targetActivity
     * @param bundle
     * @param requestCode
     * @param flag
     */
    public void startActivity(Class<?> targetActivity, Bundle bundle, int requestCode, int... flag) {
        Intent intent = new Intent();
        intent.setClass(mActivity, targetActivity);
        for (Integer fg : flag) {
            intent.addFlags(fg);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode < Integer.MAX_VALUE) {
            mActivity.startActivityForResult(intent, requestCode);
        } else {
            mActivity.startActivity(intent);
        }
    }
}
