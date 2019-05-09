package com.lieying.socialappstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.widget.TitleView;

public class EditUserDataActivity extends BaseActivity {
    public static String KEY_DATA_TYPE = "data_type";
    public static int KEY_RESPONSE_CODE = 24;
    public static int KEY_REQUEST_CODE = 8;
    private TitleView view_title;
    private EditText editText;
    private ImageView mIvDel;
    private int type;

    public static void startActivity(Activity context , int type) {
        Intent intent = new Intent(context, EditUserDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_DATA_TYPE , type);
        context.startActivityForResult(intent , KEY_REQUEST_CODE);
    }

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
                break;
            case 1: // 职业
                view_title.setTitle("职业");
                editText.setHint("请输入您的职业");
                break;
            case 2: //个人简介
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
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra(type +"", editText.getText());
                setResult(KEY_RESPONSE_CODE , intent);
                finish();
            }
        });
        mIvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.clearComposingText();
            }
        });
    }
}
