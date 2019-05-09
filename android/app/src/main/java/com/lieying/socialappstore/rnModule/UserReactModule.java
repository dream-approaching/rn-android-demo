package com.lieying.socialappstore.rnModule;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.socialappstore.manager.UserManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class UserReactModule extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT_KEY_TOKEN = "KEY_ONE_TOKEN";
    private static final String DURATION_SHORT_KEY_PHONE = "KEY_ONE_PHONE";
    private static final String DURATION_SHORT_KEY_NICK = "KEY_ONE_NICK";
    private static final String DURATION_SHORT_KEY_HEAD = "KEY_ONE_HEAD";

    public UserReactModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "GetUserInfo";
    }
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY_TOKEN, UserManager.getCurrentUser().getAccessToken());
        constants.put(DURATION_SHORT_KEY_PHONE, UserManager.getCurrentUser().getPhone());
        constants.put(DURATION_SHORT_KEY_NICK, UserManager.getCurrentUser().getName());
        if( UserManager.getCurrentUser().getUserinfo()!=null){
            constants.put(DURATION_SHORT_KEY_HEAD, UserManager.getCurrentUser().getUserinfo().getHead_image());
        }
        return constants;
    }


    /**
     * @param message
     * com.lieying.content.social.ENTER
     */
    @ReactMethod
    public void open(String message) {
    }

}
