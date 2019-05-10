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
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.socialappstore.R;
import com.lieying.socialappstore.callback.DialogClickCallback;

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
    /**
     *
     * @param context
     */
    public static void showBottomDialog(final Context context , String btnOne , String btnTwo , String btnThree , DialogClickCallback dialogClickCallback) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom, null);
        dialog.setContentView(view);
        TextView tvOne = dialog.findViewById(R.id.tv_dialog_bottom_one);
        TextView tvTwo = dialog.findViewById(R.id.tv_dialog_bottom_two);
        TextView tvThree = dialog.findViewById(R.id.tv_dialog_bottom_three);
        dialog.getWindow().setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        tvOne.setText(btnOne);
        tvTwo.setText(btnTwo);
        tvThree.setText(btnThree);
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickCallback.onClick(1);
                dialog.dismiss();
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickCallback.onClick(2);
                dialog.dismiss();
            }
        });
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
