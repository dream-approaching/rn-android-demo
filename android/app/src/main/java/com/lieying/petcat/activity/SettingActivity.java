package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.NativeCallRnBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.BuildConfig;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.manager.VerisonManager;
import com.lieying.petcat.utils.GsonUtil;
import com.lieying.petcat.utils.SharedPreferencesUtil;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.petcat.widget.TitleView;

import static com.lieying.comlib.constant.Constants.SP_KEY_USER_INFO;

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
    protected void onResume() {
        super.onResume();
        if(UserManager.getInstance().NoUserLogin()){
            findViewById(R.id.tv_setting_out_account).setVisibility(View.GONE);
        }
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
                if(UserManager.getInstance().NoUserLogin()){
                    LoginActivity.startActivity(mContext);
                    return;
                }
                NoticeConfigActivity.startActivity(mContext);
                break;
            case R.id.rl_setting_update:
                VerisonManager.getInstance().cheakUpload();
                break;
            case R.id.rl_setting_cache:
                break;
            case R.id.rl_setting_policy:
                WebActivity.startWeb(mContext , "file:////android_asset/procy.html" , true);
                break;
            case R.id.rl_setting_value:
                break;
            case R.id.tv_setting_out_account:
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Constants.BROADCAST_FLAG_REFRESH_MAIN));
                UserManager.getCurrentUser().setUserinfo(null);
                SharedPreferencesUtil.getInstance().removeSP(SP_KEY_USER_INFO);
                ToastUtil.showToast("退出成功");
                NativeCallRnBean nativeCallRnBean = new NativeCallRnBean();
                nativeCallRnBean.setOption(Constants.OPTION_USER_EXIT);
                MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(Constants.NATIVE_CALL_RN_PARAMS, GsonUtil.GsonString(nativeCallRnBean));
                break;
        }
    }
}
