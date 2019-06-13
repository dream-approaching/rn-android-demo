package com.lieying.petcat;


import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.lieying.petcat.rnModule.OpenModule;
import com.lieying.petcat.rnModule.OpenReactModule;
import com.lieying.petcat.rnModule.RnCallBackModule;
import com.lieying.petcat.rnModule.UserReactModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomToastPackage implements ReactPackage {

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new OpenModule(reactContext));
        modules.add(new OpenReactModule(reactContext));
        modules.add(new RnCallBackModule(reactContext));
        modules.add(new UserReactModule(reactContext));
        return modules;
    }

}
