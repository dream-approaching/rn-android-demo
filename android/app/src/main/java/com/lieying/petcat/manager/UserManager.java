package com.lieying.petcat.manager;


import com.google.gson.Gson;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.utils.SharedPreferencesUtil;

/**
 * Created by liyi on 2018/2/2.
 */

public class UserManager {
    private static UserManager manager;

    public static UserManager getInstance() {
        synchronized (UserManager.class) {
            if (manager == null) {
                manager = new UserManager();
            }
        }
        return manager;
    }

    public static UserInfoBean getCurrentUser() {
        return getInstance().CurrentUser;
    }
    public boolean NoUserLogin() {
        return getInstance().CurrentUser.getUserinfo() == null;
    }

    public void setCurrentUser(UserInfoBean currentUser) {
        CurrentUser = currentUser;
    }

    volatile UserInfoBean CurrentUser = new UserInfoBean();

    /**
     * @Description: 保存用户信息
     * @Author: liyi
     * @CreateDate: 2019/5/14 0014 11:04
     */
    public  void saveUserData() {
        String userInfo = new Gson().toJson(getInstance().CurrentUser);
        SharedPreferencesUtil.getInstance().putString(Constants.SP_KEY_USER_INFO, userInfo);
    }

    /**
     * @Description: 保存用户信息
     * @Author: liyi
     * @CreateDate: 2019/5/14 0014 11:04
     */
    public void saveUserData(UserInfoBean CurrentUser) {
        setCurrentUser(CurrentUser);
        saveUserData();
    }

}
