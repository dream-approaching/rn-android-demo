package com.lieying.socialappstore.fragment;

import android.os.Bundle;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

public class FourthFragment extends BaseReactFragment implements DefaultHardwareBackBtnHandler {
    private final int OVERLAY_PERMISSION_REQ_CODE = 1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /**
     * @Description:   React Native Fragment
     * @PARAMS:         routeName：路由名   module：对应app.json里的"name": "MyReactNativeAppthree"   params：带入RN的参数json格式
     * @Author:         liyi
     * @CreateDate:     2019/5/15 0015 10:28
     */
    public static FourthFragment newInstance(String routeName, String module , String params) {
        FourthFragment fragment = new FourthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_JSMAINMODULE_PATH, routeName);
        args.putString(ARG_PARAM_MODULE_NAME, module);
        args.putString(KEY_BUNDLE_ENTER_PARAMS , params);
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
