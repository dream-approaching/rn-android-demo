package com.lieying.socialappstore.rnModule;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.socialappstore.activity.Main3Activity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class OpenModule extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY = "KEY_ONE";
    private static final String DURATION_LONG_KEY = "LONG";

    public OpenModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "liyi";
    }
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, "aaaa");
        return constants;
    }
    @ReactMethod
    public void open(String message) {
        Log.e("test" , message+"~~~~~");
        getReactApplicationContext().startActivity(new Intent(getReactApplicationContext() , Main3Activity.class));
    }
}
