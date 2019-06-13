package com.lieying.petcat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
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
                editText.setHint("请输入您的职业(10字内)");
                editText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(10)});
                break;
            case 2: //个人简介
                params = "profile";
                view_title.setTitle("个人简介");
                editText.setMaxEms(Integer.MAX_VALUE);
                editText.setHint("请输入您的个人简介(20字内)");
                editText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(20)});
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
                update(editText.getText().toString());
            }
        });
        mIvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    view_title.setRightColor(getResources().getColor(R.color.color_fb716b));
                }else{
                    view_title.setRightColor(getResources().getColor(R.color.color_94fb716b));
                }
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
                    ToastUtil.showToast(getString(R.string.string_net_errors));
                } else {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
