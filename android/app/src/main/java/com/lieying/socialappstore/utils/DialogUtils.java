package com.lieying.socialappstore.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lieying.socialappstore.R;
import com.lieying.socialappstore.adapter.ReportAdatper;
import com.lieying.socialappstore.callback.DialogClickCallback;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;

import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by ${liyi} on 2018/1/27.
 */

public class DialogUtils {

    /**
     * 一个按钮
     *
     * @param context
     */
    public static void showReportDialog(Context context , String id) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_one_click);
        ReportAdatper reportAdatper = new ReportAdatper(context, new ReportAdatper.ClickItem() {
            @Override
            public void click(int position) {
                HashMap<String, String> map = new HashMap<>();
                map.put("access_token", UserManager.getCurrentUser().getAccessToken());
                map.put("type" , "1");
                map.put("content_id" , id);
                map.put("'content'" , position+"");
                map.put("mobilephone", UserManager.getCurrentUser().getPhone());
                RetrofitUtils.getInstance(context).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                    @Override
                    public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                        return RetrofitUtils.getInstance(context).getApiService().updateUserPushToken(ReqBody.getReqString(map));
                    }
                }, new BaseObserver<ResponseData<Object>>() {
                    @Override
                    protected void onSuccees(ResponseData<Object> objectResponseData) {
                        dialog.dismiss();
                        if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                            ToastUtil.showToast("举报成功:"+objectResponseData.getMsg());
                        } else {
                            ToastUtil.showToast("举报失败:"+objectResponseData.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) {
                        dialog.dismiss();
                        if (isNetWorkError) {
                            ToastUtil.showToast("网络层错误");
                        } else {
                            ToastUtil.showToast("请求失败");
                        }
                    }
                });

            }
        });
        ListView listView =  dialog.findViewById(R.id.lv_report_dialog);
        listView.setAdapter(reportAdatper);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.findViewById(R.id.iv_dialog_report_clase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
