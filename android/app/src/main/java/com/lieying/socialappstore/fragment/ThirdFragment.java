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
import com.lieying.socialappstore.BuildConfig;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

import java.util.Arrays;

public class ThirdFragment extends BaseReactFragment implements DefaultHardwareBackBtnHandler {
    private final int OVERLAY_PERMISSION_REQ_CODE = 1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static ThirdFragment newInstance(String param1, String param2 , boolean debug ,String bundle) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_JSMAINMODULE_PATH, param1);
        args.putString(ARG_PARAM_MODULE_NAME, param2);
        args.putBoolean(ARG_PARAM_MODULE_DEBUG, debug);
        args.putString(ARG_PARAM_MODULE_BUNDLE_NAME , bundle);
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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(getContext())) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getContext().getPackageName()));
//                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//            }
//        }
    }

    @Override
    public void initListener() {

    }

}
