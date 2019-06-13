package com.lieying.petcat.rnModule;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.utils.GsonUtil;
import com.lieying.petcat.utils.SharedPreferencesUtil;
import com.lieying.petcat.utils.ToastUtil;

import javax.annotation.Nonnull;

import static com.lieying.comlib.constant.Constants.SP_KEY_USER_INFO;

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

    @ReactMethod
    public void getUserInfoString(Callback successCallback) {
        if(UserManager.getCurrentUser().getUserinfo()!=null){
            successCallback.invoke(GsonUtil.GsonString(UserManager.getCurrentUser().getUserinfo()));
        }else{
            successCallback.invoke("");
        }
    }


    @ReactMethod
    public void exitLogin() {
        UserManager.getCurrentUser().setUserinfo(null);
        SharedPreferencesUtil.getInstance().removeSP(SP_KEY_USER_INFO);
        ToastUtil.showToast("退出成功");
    }


}
