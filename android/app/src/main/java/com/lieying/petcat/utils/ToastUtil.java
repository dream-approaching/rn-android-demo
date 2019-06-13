package com.lieying.petcat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.lieying.comlib.utils.EmptyUtil;
import com.lieying.petcat.MainApplication;

/**
 * Author:wudi
 * Time:2018/9/13
 * Description:ToastUtil
 */
public class ToastUtil {
    private ToastUtil(){}

    public static void showToast(@StringRes int stringId) {
        showToast(MainApplication.getInstance().getApplicationContext(), stringId);
    }

    public static void showToast(String message) {
        showToast(MainApplication.getInstance().getApplicationContext(), message);
    }

    public static void showToast(Context context, @StringRes int StringId) {
        if (null != context) {
            try {
                String str = context.getResources().getString(StringId);
                if (EmptyUtil.isNotEmpty(str)) {
                    showToast(context, str);
                }
            } catch (Resources.NotFoundException e) {
                Logger.e(e.getMessage());
            }
        }
    }

    public static void showToast(Context context, String massage) {
        if (null != context && EmptyUtil.isNotEmpty(massage)) {
            Toast.makeText(context, massage, Toast.LENGTH_SHORT).show();
        } else {
            //TODO nothing
        }
    }
}
