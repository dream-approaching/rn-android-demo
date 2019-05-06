package com.lieying.socialappstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseV4Fragment;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/26 0026 15:20
 */
public class XFriendShareFragment extends BaseReactFragment {
    public static XFriendShareFragment newInstance(String param1, String param2 , boolean debug ,String bundle) {
        XFriendShareFragment fragment = new XFriendShareFragment();
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
