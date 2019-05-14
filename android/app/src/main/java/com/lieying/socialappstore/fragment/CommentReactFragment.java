package com.lieying.socialappstore.fragment;

import android.os.Bundle;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/14 0014 17:53
 */
public class CommentReactFragment extends BaseReactFragment{
    public static CommentReactFragment newInstance(String param1, String param2 , boolean debug , String bundle) {
        CommentReactFragment fragment = new CommentReactFragment();
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
    }

    @Override
    public void initListener() {

    }
}
