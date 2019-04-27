package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.gyf.immersionbar.ImmersionBar;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.lieying.comlib.utils.EmptyUtil;
import com.lieying.socialappstore.BuildConfig;
import com.lieying.socialappstore.CustomToastPackage;
import com.lieying.socialappstore.MainApplication;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.fragment.BaseReactFragment;
import com.lieying.socialappstore.utils.ToastUtil;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;

import java.util.Arrays;
import java.util.List;

public class CommonReactActivity extends BaseActivity implements DefaultHardwareBackBtnHandler {
    private RNGestureHandlerEnabledRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    public static String KEY_BUNDLE_PATH = "bundle_path";
    public static String KEY_BUNDLE_ENTER_NAME = "bundle_name";

    /**
     * @param context
     * @param moduleName  传入rn的moduleName 如：MyReactNativeAppthree
     * @param enterName  传入rn的入口 如：fragment1
     */
    public static void startActivity(Context context, String moduleName ,String enterName) {
        Intent intent = new Intent(context, CommonReactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_BUNDLE_PATH, moduleName);
        intent.putExtra(KEY_BUNDLE_ENTER_NAME, enterName);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(false).keyboardEnable(true).init();
        String bundle_path = getIntent().getStringExtra(KEY_BUNDLE_PATH);
        String bundle_enter = getIntent().getStringExtra(KEY_BUNDLE_ENTER_NAME);
        if(EmptyUtil.isEmpty(bundle_path)){
            ToastUtil.showToast("react native bundle 不存在");
            finish();
            return;
        }
        mReactRootView = new RNGestureHandlerEnabledRootView(this.getApplicationContext());
        mReactInstanceManager = MainApplication.getInstance().getReactNativeHost().getReactInstanceManager();
        Bundle bundle = new Bundle();
        bundle.putString("veiw_name" , bundle_enter);
        mReactRootView.startReactApplication(mReactInstanceManager, bundle_path, bundle);
        setContentView(mReactRootView);
    }

    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new RNGestureHandlerPackage(),
                new RNDeviceInfo(),
                new CustomToastPackage());
    }


    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void findView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU ) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
