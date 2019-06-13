package com.lieying.petcat.fragment;

import android.os.Bundle;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/14 0014 17:53
 */
public class CommentReactFragment extends BaseReactFragment{
    /**
      * @Description:   React Native Fragment
      * @PARAMS:         routeName：路由名   module：对应app.json里的"name": "MyReactNativeAppthree"   params：带入RN的参数json格式
      * @Author:         liyi
      * @CreateDate:     2019/5/15 0015 10:28
     */
    public static CommentReactFragment newInstance(String routeName, String module , String params) {
        CommentReactFragment fragment = new CommentReactFragment();
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
