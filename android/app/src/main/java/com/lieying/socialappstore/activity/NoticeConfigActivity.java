package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.TitleView;

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
                return RetrofitUtils.getInstance(mContext).getApiService().deleteFans(ReqBody.getReqString(map));
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
                    ToastUtil.showToast("网络层错误");
                } else {
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }
}
