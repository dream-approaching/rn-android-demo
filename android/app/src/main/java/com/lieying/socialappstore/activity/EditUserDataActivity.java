package com.lieying.socialappstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.SharedPreferencesUtil;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.TitleView;

import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class EditUserDataActivity extends BaseActivity {
    public static String KEY_DATA_TYPE = "data_type";
    public static int KEY_RESPONSE_CODE = 24;
    public static int KEY_REQUEST_CODE = 8;
    private TitleView view_title;
    private EditText editText;
    private ImageView mIvDel;
    private int type;
    private String params;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_edit_user_data);
    }

    @Override
    public void findView() {
        view_title = findViewById(R.id.view_title);
        editText = findViewById(R.id.et_user_msg);
        mIvDel = findViewById(R.id.iv_edit_user_del);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra(KEY_DATA_TYPE , 0);
        switch (type){
            case 0: //昵称
                view_title.setTitle("昵称");
                editText.setHint("取一个喜欢的名字吧");
                params = "nick_name";
                break;
            case 1: // 职业
                params = "career";
                view_title.setTitle("职业");
                editText.setHint("请输入您的职业");
                break;
            case 2: //个人简介
                params = "profile";
                view_title.setTitle("个人简介");
                editText.setMaxEms(Integer.MAX_VALUE);
                editText.setHint("请输入您的个人简介");
                break;

        }

    }

    @Override
    public void initListener() {
        view_title.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        view_title.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update("msg");
            }
        });
        mIvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    private void update(String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put(params , msg);
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().updateUserInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    Intent intent = new Intent();
                    //把返回数据存入Intent
                    intent.putExtra("msg", editText.getText().toString());
                    setResult(KEY_RESPONSE_CODE , intent);
                    finish();
                } else {
                    ToastUtil.showToast(objectResponseData.getMsg());
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
