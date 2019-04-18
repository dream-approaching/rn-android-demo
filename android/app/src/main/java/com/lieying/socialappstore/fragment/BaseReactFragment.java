package com.lieying.socialappstore.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.lieying.socialappstore.BuildConfig;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.callback.FragmentCallback;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

/**
 * Created by chenjialin on 17/3/13.
 */
public abstract class BaseReactFragment extends BaseV4Fragment implements DefaultHardwareBackBtnHandler {
    protected static final String ARG_PARAM_JSMAINMODULE_PATH = "param1";
    protected static final String ARG_PARAM_MODULE_NAME = "param2";
    protected static final String ARG_PARAM_MODULE_DEBUG = "param3";
    protected static final String ARG_PARAM_MODULE_BUNDLE_NAME = "param4";
    protected RNGestureHandlerEnabledRootView mReactRootView;

    public void setFragmentCallback(FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    public ReactInstanceManager getmReactInstanceManager() {
        return mReactInstanceManager;
    }

    protected void initReactInstanceManager(String jsPath , String moduleName ,boolean debug ,String bundleAssetName){
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getActivity().getApplication())
                .setCurrentActivity(getActivity())
                .setBundleAssetName(bundleAssetName)
                .setJSMainModulePath(jsPath)
                .addPackages(getPackages())
                .setUseDeveloperSupport(debug)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, moduleName, null);
        fragmentCallback.initReactManager(mReactInstanceManager);
    }

    protected ReactInstanceManager mReactInstanceManager;

    protected FragmentCallback fragmentCallback;

    @Override
    public void invokeDefaultOnBackPressed() {
        Log.e("test", "invokeDefaultOnBackPressed ------------- ");
        fragmentCallback.fragmentBack();
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
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        mReactRootView = new RNGestureHandlerEnabledRootView(getContext());
        Bundle args = getArguments();
        String jsPath = (String) args.get(ARG_PARAM_JSMAINMODULE_PATH);
        String moduleName = (String)args.get(ARG_PARAM_MODULE_NAME);
        boolean debug = (boolean)args.get(ARG_PARAM_MODULE_DEBUG);
        String bundleAssetName = args.getString(ARG_PARAM_MODULE_BUNDLE_NAME);
        initReactInstanceManager(jsPath , moduleName ,debug , bundleAssetName);
        return mReactRootView;
    }

}
