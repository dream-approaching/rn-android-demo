package com.lieying.petcat.rnModule;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.comlib.constant.Constants;

import javax.annotation.Nonnull;

public class RnCallBackModule extends ReactContextBaseJavaModule {


    public RnCallBackModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "RnCallBack";
    }

    @ReactMethod
    public void callBackFirstFragment(String json) {
        Intent intent = new Intent(Constants.BROADCAST_FLAG_FIRST_FG_FRESH_FIRST_ITEM);
        intent.putExtra(Constants.KEY_FIRSTH_FG_JSON , json);
        LocalBroadcastManager.getInstance(getReactApplicationContext() ).sendBroadcast(intent);
    }

    @ReactMethod
    public void callTopicFg(String json) {
        Intent intent = new Intent(Constants.BROADCAST_FLAG_TOPIC_FG_FRESH);
        intent.putExtra(Constants.KEY_FIRSTH_FG_JSON , json);
        LocalBroadcastManager.getInstance(getReactApplicationContext() ).sendBroadcast(intent);
    }


}
