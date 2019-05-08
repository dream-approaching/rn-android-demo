package com.lieying.socialappstore.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.socialappstore.R;

/**
 * Created by ${liyi} on 2018/1/27.
 */

public class DialogUtils {

    /**
     * 一个按钮
     *
     * @param context
     * @param listener
     */
    public static void showReportDialog(Context context, String title, String tip_one, String tip_two, String btn,
                                          final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_one_click);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.findViewById(R.id.iv_dialog_report_clase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
