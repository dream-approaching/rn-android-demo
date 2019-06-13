package com.lieying.petcat.fragment;

import android.os.Bundle;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/26 0026 15:20
 */
public class XFriendShareFragment extends BaseReactFragment {
    /**
     * @Description:   React Native Fragment
     * @PARAMS:         routeName：路由名   module：对应app.json里的"name": "MyReactNativeAppthree"   params：带入RN的参数json格式
     * @Author:         liyi
     * @CreateDate:     2019/5/15 0015 10:28
     */
    public static XFriendShareFragment newInstance(String routeName, String module , String params) {
        XFriendShareFragment fragment = new XFriendShareFragment();
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

    }

    @Override
    public void initListener() {

    }
}
