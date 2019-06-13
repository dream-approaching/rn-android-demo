package com.lieying.petcat.callback;

import com.facebook.react.ReactInstanceManager;

public interface FragmentCallback {
    public void initReactManager(ReactInstanceManager reactInstanceManager);
    public void fragmentBack();
}
