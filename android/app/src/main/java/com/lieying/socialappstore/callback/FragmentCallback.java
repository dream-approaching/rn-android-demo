package com.lieying.socialappstore.callback;

import com.facebook.react.ReactInstanceManager;

public interface FragmentCallback {
    public void initReactManager(ReactInstanceManager reactInstanceManager);
    public void fragmentBack();
}
