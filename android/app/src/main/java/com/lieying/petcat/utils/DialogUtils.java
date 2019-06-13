package com.lieying.petcat.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lieying.comlib.bean.UpgradeBean;
import com.lieying.petcat.R;
import com.lieying.petcat.adapter.ReportAdatper;
import com.lieying.petcat.callback.DialogClickCallback;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.share.ShareContent;
import com.lieying.petcat.widget.ShareBoardView;
import com.umeng.socialize.UMShareListener;

import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by ${liyi} on 2018/1/27.
 */

public class DialogUtils {

    /**
     * 举报对话框
     *
     * @param context
     */
    public static void showReportDialog(Context context, String id) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_one_click);
        ReportAdatper reportAdatper = new ReportAdatper(context, new ReportAdatper.ClickItem() {
            @Override
            public void click(int position) {

                HashMap<String, String> map = new HashMap<>();
                map.put("access_token", UserManager.getCurrentUser().getAccessToken());
                map.put("type", "1");
                map.put("content_id", id);
                map.put("content", (position+1) + "");
                map.put("mobilephone", UserManager.getCurrentUser().getPhone());
                RetrofitUtils.getInstance(context).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                    @Override
                    public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                        return RetrofitUtils.getInstance(context).getApiService().report(ReqBody.getReqString(map));
                    }
                }, new BaseObserver<ResponseData<Object>>() {
                    @Override
                    protected void onSuccees(ResponseData<Object> objectResponseData) {
                        if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                            ToastUtil.showToast("举报成功");
                        } else {
                            ToastUtil.showToast("举报失败");
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) {
                        if (isNetWorkError) {
                            ToastUtil.showToast(context.getString(R.string.string_net_errors));
                        } else {
                            ToastUtil.showToast(context.getString(R.string.string_request_fails));
                        }
                    }
                });
                dialog.dismiss();
            }
        });
        ListView listView = dialog.findViewById(R.id.lv_report_dialog);
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
     *  选择照片对话框
     */
    public static void showChoicePic(final Context context, String btnOne, String btnTwo, String btnThree, DialogClickCallback dialogClickCallback) {
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


    /**
     * 第三方分享对话框
     */
    public static void showShareDialog(final Context context , ShareContent contentMap, UMShareListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share_layout, null);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        ShareBoardView mShareBoardView = dialog.findViewById(R.id.share_board_view);
        mShareBoardView.setSharePlantform(contentMap, listener);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);


        dialog.show();
    }

    /**
     * 升级对话框
     */
    public static void showUpGradeDialog(final Context context, UpgradeBean upgradeBean, View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_upgrade, null);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        LinearLayout linearLayout = view.findViewById(R.id.ll_upgrade_dialog_version_tip);
        ((TextView) view.findViewById(R.id.tv_upgrade_dialog_version)).setText(upgradeBean.getVer_code());
        ((TextView) view.findViewById(R.id.tv_upgrade_dialog_download)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickListener.onClick(v);
            }
        });
        String[] sourceStrArray = upgradeBean.getContent().split("\\|");
        for (int i = 0; i < sourceStrArray.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_upgrade_dialog_tag, null);
            linearLayout.addView(textView);
            textView.setText(sourceStrArray[i]);
        }

        dialog.show();
    }


    /**
     * 删除对话框
     */
    public static void showDeleteDialog(final Context context , View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((TextView)dialog.findViewById(R.id.tv_dialog_delete_cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((TextView)dialog.findViewById(R.id.tv_dialog_delete_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickListener.onClick(v);
            }
        });
        dialog.show();
    }

    public static void showDialog(Context context, String title, String content, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        final View v = LayoutInflater.from(context).inflate(R.layout.dialog_ugc_tip, null);
        dialog.setContentView(v);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_msg);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        tvTitle.setText(title);
        tvContent.setText(content);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            }
        });
        dialog.show();
    }
}
