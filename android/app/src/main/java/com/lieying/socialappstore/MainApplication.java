package com.lieying.socialappstore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.bolan9999.SpringScrollViewPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.shell.MainReactPackage;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.comlib.pull.SimpleRefreshHeadView;
import com.lieying.comlib.pull.SimpleRefreshMoreView;
import com.lieying.socialappstore.manager.AppActivityManager;
import com.microsoft.codepush.react.CodePush;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class MainApplication extends Application implements ReactApplication {
    private static MainApplication mApp;

    public static MainApplication getInstance() {
        return mApp;
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        protected String getJSBundleFile() {
            return CodePush.getJSBundleFile("tab3.bundle");
        }

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
            new SpringScrollViewPackage(),
                    new CodePush(BuildConfig.CODEPUSH_KEY, getApplicationContext(), BuildConfig.DEBUG),
                    new RNGestureHandlerPackage(),
                    new RNDeviceInfo(),
                    new CustomToastPackage()

            );
        }

        @Nullable
        @Override
        protected String getBundleAssetName() {
            return "tab3.bundle";
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        MultiDex.install(this);
        XGPushConfig.enableDebug(this,true);
        AppActivityManager.getInstance();
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        PullToRefreshRecyclerView.PullSysConfig config = new PullToRefreshRecyclerView
                .PullSysConfig.Builder()
                .moreViewClass(SimpleRefreshMoreView.class)
                .refreshViewClass(SimpleRefreshHeadView.class)
                .build();
        PullToRefreshRecyclerView.setPullSysConfig(config);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
//token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    /**
     * 管理activity生命周期
     */
    private static class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            AppActivityManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (null == activity) return;
            AppActivityManager.getInstance().removeActivity(activity);
        }
    }

    public Context getContext() {
        return mApp.getApplicationContext();
    }
}
