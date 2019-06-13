package com.lieying.petcat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.RNFetchBlob.RNFetchBlobPackage;
import cn.qiuxiang.react.geolocation.AMapGeolocationPackage;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.utils.GsonUtil;
import com.lieying.petcat.utils.SharedPreferencesUtil;
import com.reactnativecommunity.webview.RNCWebViewPackage;
import com.bolan9999.SpringScrollViewPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.comlib.pull.SimpleRefreshHeadView;
import com.lieying.comlib.pull.SimpleRefreshMoreView;
import com.lieying.petcat.RNUmeng.DplusReactPackage;
import com.lieying.petcat.RNUmeng.RNUMConfigure;
import com.lieying.petcat.manager.AppActivityManager;
import com.microsoft.codepush.react.CodePush;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class MainApplication extends Application implements ReactApplication {
    private static MainApplication mApp;
    String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/508e331646e7ae2f7bc0c8b626072f43/TXUgcSDK.licence"; //您从控制台申请的 licence url
    String ugcKey = "69d306a08224e9b59e5da776cd50cf22";                                                                 //您从控制台申请的 licence key

    public static MainApplication getInstance() {
        return mApp;
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        protected String getJSBundleFile() {
            return CodePush.getJSBundleFile("index.android.bundle");
        }

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
            new RNFetchBlobPackage(),
            new AMapGeolocationPackage(),
            new RNCWebViewPackage(),
            new SpringScrollViewPackage(),
                    new CodePush(BuildConfig.CODEPUSH_KEY, getApplicationContext(), BuildConfig.DEBUG),
                    new RNGestureHandlerPackage(),
                    new CustomToastPackage(),
                    new DplusReactPackage()
            );
        }

        @Nullable
        @Override
        protected String getBundleAssetName() {
            return "index.android.bundle";
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
                Log.e("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        rnUmengInit();
        umengInit();
        String userInfoString = SharedPreferencesUtil.getInstance().getString(Constants.SP_KEY_USER_INFO);
        if (!TextUtils.isEmpty(userInfoString)) {
            UserInfoBean userInfoBean = GsonUtil.GsonToBean(userInfoString, UserInfoBean.class);
            UserManager.getInstance().setCurrentUser(userInfoBean);
        }
        TXLiveBase.getInstance().setLicence(mApp, ugcLicenceUrl, ugcKey);
        TXLiveBase.setConsoleEnabled(true);
        TXLiveBase.setLogLevel(TXLiveConstants.LOG_LEVEL_ERROR);
    }
    /**
     * 设置RN友盟初始化
     */
    private void rnUmengInit() {
        RNUMConfigure.init(this, BuildConfig.UMENG_APP_KEY, "channel", UMConfigure.DEVICE_TYPE_PHONE,null);
    }
    /**
     * 设置友盟初始化
     */
    private void umengInit() {
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        UMConfigure.init(mApp, UMConfigure.DEVICE_TYPE_PHONE,null);
        MobclickAgent.setSecret(this, BuildConfig.UMENG_SECRET);
        // 选用MANUAL页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
        {
            PlatformConfig.setWeixin(BuildConfig.WECHAT_APPKEY, BuildConfig.WECHAT_SECRET);
            PlatformConfig.setSinaWeibo(BuildConfig.SINA_APPKEY, BuildConfig.SINA_SECRET, "http://sns.whalecloud.com");
            PlatformConfig.setQQZone(BuildConfig.QQ_APPKEY, BuildConfig.QQ_SECRET);
        }
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
