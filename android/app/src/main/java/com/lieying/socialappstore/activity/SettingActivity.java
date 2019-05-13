package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.socialappstore.BuildConfig;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.widget.TitleView;

public class SettingActivity extends BaseActivity {
    TextView mTvVersion;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void findView() {
        findViewById(R.id.rl_setting_notice).setOnClickListener(this::onUnDoubleClickView);
        findViewById(R.id.rl_setting_update).setOnClickListener(this::onUnDoubleClickView);
        findViewById(R.id.rl_setting_cache).setOnClickListener(this::onUnDoubleClickView);
        findViewById(R.id.rl_setting_policy).setOnClickListener(this::onUnDoubleClickView);
        findViewById(R.id.rl_setting_value).setOnClickListener(this::onUnDoubleClickView);
        findViewById(R.id.tv_setting_out_account).setOnClickListener(this::onUnDoubleClickView);
        mTvVersion = (TextView)findViewById(R.id.tv_setting_occupation);
    }

    @Override
    public void initView() {
        mTvVersion.setText("V"+BuildConfig.VERSION_NAME);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ((TitleView)findViewById(R.id.title_setting_activity)).setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onUnDoubleClickView(View v) {
        super.onUnDoubleClickView(v);
        switch (v.getId()){
            case R.id.rl_setting_notice:
                NoticeConfigActivity.startActivity(mContext);
                break;
            case R.id.rl_setting_update:
                break;
            case R.id.rl_setting_cache:
                break;
            case R.id.rl_setting_policy:
                break;
            case R.id.rl_setting_value:
                break;
            case R.id.tv_setting_out_account:
                break;
        }
    }
}
