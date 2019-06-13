package com.lieying.petcat.rnModule;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lieying.petcat.BuildConfig;
import com.lieying.petcat.activity.AppDetailsActivity;
import com.lieying.petcat.activity.CollectionActivity;
import com.lieying.petcat.activity.FollowOrFansActivity;
import com.lieying.petcat.activity.SettingActivity;
import com.lieying.petcat.activity.UserDataActivity;
import com.lieying.petcat.activity.UserIndexActivity;
import com.lieying.petcat.manager.AppActivityManager;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.utils.DialogUtils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class OpenModule extends ReactContextBaseJavaModule {

    private static final String HOST_USER = "host_user";
    private static final String HOST_CONTENT = "host_content";
    private static final String HOST_APP_STORE = "host_app_store";
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
        constants.put(HOST_USER, BuildConfig.USER_HOST);
        constants.put(HOST_CONTENT, BuildConfig.CONTENT_HOST);
        constants.put(HOST_APP_STORE, BuildConfig.APP_STORE_HOST);
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
     * 打开收藏界面
     */
    @ReactMethod
    public void openCollection( String type) {
        CollectionActivity.startActivity(getReactApplicationContext() , type);
    }

    /**
     * @param phone 用户标识（手机号）
     *  打开个人主页界面
     */
    @ReactMethod
    public void openUserIndex( String phone) {
        UserIndexActivity.startActivity(getReactApplicationContext() , phone);
    }

    /**
     * 打开用户中心界面
     */
    @ReactMethod
    public void openUserData() {
        UserDataActivity.startActivity(getReactApplicationContext());
    }

    /**
     * 打开用户中心界面
     */
    @ReactMethod
    public void openAppDetails(String app_id) {
        AppDetailsActivity.startActivity(getReactApplicationContext() ,app_id);
    }

    /**
     * 打开举报对话框
     */
    @ReactMethod
    public void openReportDialog(String contentID ) {
        DialogUtils.showReportDialog(AppActivityManager.getInstance().getTopActivity() , contentID);
    }

    /**
     * 打开我的粉丝或者我的关注
     * type ： 1（我的关注）   2（我的粉丝）
     */
    @ReactMethod
    public void openFansOrFollow(String type ) {
        FollowOrFansActivity.startActivity(getReactApplicationContext() , type , UserManager.getCurrentUser().getPhone());
    }

    /**
     * 打开我的设置
     */
    @ReactMethod
    public void openSetting() {
        SettingActivity.startActivity(getReactApplicationContext());
    }

}
