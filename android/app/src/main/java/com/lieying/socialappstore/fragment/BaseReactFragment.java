package com.lieying.socialappstore.fragment;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.lieying.socialappstore.base.BaseV4Fragment;

/**
 * Created by chenjialin on 17/3/13.
 */
public abstract class BaseReactFragment extends BaseV4Fragment {
    private ReactRootView mReactRootView;

    public ReactInstanceManager getmReactInstanceManager() {
        return mReactInstanceManager;
    }

    private ReactInstanceManager mReactInstanceManager;

}
