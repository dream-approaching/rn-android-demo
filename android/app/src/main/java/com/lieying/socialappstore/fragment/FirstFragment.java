package com.lieying.socialappstore.fragment;

import android.os.Bundle;


public class FirstFragment extends BaseReactFragment  {

    public static FirstFragment newInstance(String param1, String param2 , boolean debug) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_JSMAINMODULE_PATH, param1);
        args.putString(ARG_PARAM_MODULE_NAME, param2);
        args.putBoolean(ARG_PARAM_MODULE_DEBUG, debug);
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
