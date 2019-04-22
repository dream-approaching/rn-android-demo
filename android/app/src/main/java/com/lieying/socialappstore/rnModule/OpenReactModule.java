package com.lieying.socialappstore.rnModule;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.socialappstore.activity.CommonReactActivity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class OpenReactModule extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY = "KEY_ONE";

    public OpenReactModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "OpenReactActivity";
    }
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, "aaaa");
        return constants;
    }

    /**
     * @param message
     * com.lieying.content.social.ENTER
     */
    @ReactMethod
    public void open(String message) {
        CommonReactActivity.startActivity(getReactApplicationContext() , message);
    }
}
