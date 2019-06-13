package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.NoticeBean;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.petcat.widget.TitleView;

import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class NoticeConfigActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    CheckBox mCbFollow;
    CheckBox mCbComments;
    CheckBox mCbPraise;
    CheckBox mCbRecvCotent;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NoticeConfigActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_notice_config);
    }

    @Override
    public void findView() {
        mCbFollow = findViewById(R.id.cb_notice_config_follow);
        mCbComments = findViewById(R.id.cb_notice_config_comments);
        mCbPraise = findViewById(R.id.cb_notice_config_praise);
        mCbRecvCotent = findViewById(R.id.cb_notice_config_recv_content);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getDate();
    }

    @Override
    public void initListener() {
        mCbFollow.setOnCheckedChangeListener(this::onCheckedChanged);
        mCbComments.setOnCheckedChangeListener(this::onCheckedChanged);
        mCbPraise.setOnCheckedChangeListener(this::onCheckedChanged);
        mCbRecvCotent.setOnCheckedChangeListener(this::onCheckedChanged);
        ((TitleView) findViewById(R.id.title_notice_config_activity)).setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TitleView) findViewById(R.id.title_notice_config_activity)).setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TitleView) findViewById(R.id.title_notice_config_activity)).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_notice_config_follow:
                break;
            case R.id.cb_notice_config_comments:
                break;
            case R.id.cb_notice_config_praise:
                break;
            case R.id.cb_notice_config_recv_content:
                break;
        }
    }

    private void uploadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("status", (mCbFollow.isChecked()?"1":"2")+","+ (mCbComments.isChecked()?"1":"2") +","+ (mCbPraise.isChecked()?"1":"2") +","+ (mCbRecvCotent.isChecked()?"1":"2"));
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().setNoticeConfig(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    ToastUtil.showToast("推送通知设置成功");
                        finish();
                } else {
                    ToastUtil.showToast("操作失败:" + objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_net_errors));
                } else {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    public void getDate(){
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<NoticeBean>>>() {
            @Override
            public ObservableSource<ResponseData<NoticeBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getNoticeConfig(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<NoticeBean>>() {
            @Override
            protected void onSuccees(ResponseData<NoticeBean> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    String[] a = objectResponseData.getData().getStatus().split(",");
                    mCbFollow.setChecked(a[0].equals("1"));
                    mCbComments.setChecked(a[1].equals("1"));
                    mCbPraise.setChecked(a[2].equals("1"));
                    mCbRecvCotent.setChecked(a[3].equals("1"));
                } else {
                    ToastUtil.showToast("获取通知设置信息失败:" + objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    ToastUtil.showToast("网络层错误");
                } else {
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }

}
