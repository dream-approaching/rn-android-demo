package com.lieying.socialappstore.rnModule;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.socialappstore.activity.CollectionActivity;
import com.lieying.socialappstore.activity.UserIndexActivity;
import com.lieying.socialappstore.manager.UserManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class OpenModule extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY = "KEY_ONE";

    public OpenModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "OpenActivity";
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
        Intent intent = new Intent(message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(intent);
    }
    /**
     * @param type 收藏类型: 1：文章收藏界面    3：App搜藏界面
     *
     */
    @ReactMethod
    public void openCollection( String type) {
        CollectionActivity.startActivity(getReactApplicationContext() , type);
    }

    /**
     * @param type 收藏类型: 1：文章收藏界面    3：App搜藏界面
     *
     */
    @ReactMethod
    public void openUserIndex( String type) {
        UserIndexActivity.startActivity(getReactApplicationContext() , UserManager.getCurrentUser().getPhone());
    }
}
