package com.lieying.socialappstore.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.lieying.socialappstore.BuildConfig;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

import java.util.Arrays;

public class FirstFragment extends BaseV4Fragment implements DefaultHardwareBackBtnHandler {
    private final int OVERLAY_PERMISSION_REQ_CODE = 1;
    private RNGestureHandlerEnabledRootView mReactRootView;

    public ReactInstanceManager getmReactInstanceManager() {
        return mReactInstanceManager;
    }

    private ReactInstanceManager mReactInstanceManager;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getContext().getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(getActivity(), this);
        }
    }

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        mReactRootView = new RNGestureHandlerEnabledRootView(getContext());
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getActivity().getApplication())
                .setCurrentActivity(getActivity())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("tab1")
                .addPackages(getPackages())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        // 注意这里的MyReactNativeApp必须对应“index.js”中的
        // “AppRegistry.registerComponent()”的第一个参数
        mReactRootView.startReactApplication(mReactInstanceManager, "MyReactNativeApp", null);
        return mReactRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(getActivity());
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getContext())) {
                    mReactInstanceManager.onActivityResult( getActivity(), requestCode, resultCode, data );
                }
            }
        }
    }


}
