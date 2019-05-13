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
import com.lieying.socialappstore.widget.TitleView;

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
        ((TitleView)findViewById(R.id.title_notice_config_activity)).setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
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


}
