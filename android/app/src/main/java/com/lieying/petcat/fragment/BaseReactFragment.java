package com.lieying.petcat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.callback.FragmentCallback;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

/**
 * Created by chenjialin on 17/3/13.
 */
public abstract class BaseReactFragment extends BaseV4Fragment implements DefaultHardwareBackBtnHandler {
    protected static final String ARG_PARAM_JSMAINMODULE_PATH = "param1";
    protected static final String ARG_PARAM_MODULE_NAME = "param2";
    public static String KEY_BUNDLE_ENTER_PARAMS = "bundle_name_params";
    protected RNGestureHandlerEnabledRootView mReactRootView;

    public void setFragmentCallback(FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    public ReactInstanceManager getmReactInstanceManager() {
        return mReactInstanceManager;
    }

    public void setmReactInstanceManager(ReactInstanceManager mReactInstanceManager) {
        this.mReactInstanceManager = mReactInstanceManager;
    }

    protected void initReactInstanceManager(String jsPath , String moduleName ,String params){
        Bundle bundle = new Bundle();
        bundle.putString("veiw_name" , jsPath);
        bundle.putString("params" , params);
        mReactInstanceManager = MainApplication.getInstance().getReactNativeHost().getReactInstanceManager();
        mReactRootView.startReactApplication(mReactInstanceManager, moduleName, bundle);
        try {
            if(fragmentCallback!=null){
                fragmentCallback.initReactManager(mReactInstanceManager);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    protected ReactInstanceManager mReactInstanceManager;

    protected FragmentCallback fragmentCallback;

    @Override
    public void invokeDefaultOnBackPressed() {

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
        String params = args.getString(KEY_BUNDLE_ENTER_PARAMS);
        initReactInstanceManager(jsPath , moduleName ,params);
        return mReactRootView;
    }

}
