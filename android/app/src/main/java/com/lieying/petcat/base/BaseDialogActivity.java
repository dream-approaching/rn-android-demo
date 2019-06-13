package com.lieying.petcat.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by ${liyi} on 2018/2/8.
 */

public abstract class BaseDialogActivity extends Dialog implements DialogInterface.OnShowListener
        , DialogInterface.OnDismissListener {
    protected Context mContext;
    private OnDismissListener mDismissListener;
    private OnShowListener mShowListener;

    public BaseDialogActivity(Context context) {
        super(context);
        init(context);
    }

    public BaseDialogActivity(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected BaseDialogActivity(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOnDismissListener(this);
        setOnShowListener(this);
    }

    protected synchronized boolean onShowDialog() {
        if (!isShowing()) {
            show();
            return true;
        }
        return false;
    }

    public void setDismissListener(OnDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }

    public void setShowListener(OnShowListener showListener) {
        this.mShowListener = showListener;
    }

    protected synchronized boolean onShowDialog(String tip) {
        setTip(tip);
        if (!isShowing()) {
            show();
            return true;
        }
        return false;
    }

    protected void onDismissDialog() {
        if (isShowing()) {
            dismiss();
        }
    }

    protected void onDismissDialog(String tip, long delayTime) {
        setTip(tip);
        View root = getWindow().getDecorView();
        if (root != null) {
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isShowing()) {
                        dismiss();
                    }
                }
            }, delayTime);
        }
    }

    protected void setTip(String tip) {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mDismissListener != null) {
            mDismissListener.onDismiss(dialog);
        }
    }


    @Override
    public void onShow(DialogInterface dialog) {
        if (mShowListener != null) {
            mShowListener.onShow(dialog);
        }
    }
}
