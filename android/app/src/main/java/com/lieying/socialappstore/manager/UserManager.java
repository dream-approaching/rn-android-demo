package com.lieying.socialappstore.manager;


import com.lieying.comlib.bean.UserInfoBean;

/**
 * Created by liyi on 2018/2/2.
 */

public class UserManager {
    private static UserManager manager;

    public static UserManager getInstance(){
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

    public void setCurrentUser(UserInfoBean currentUser) {
        CurrentUser = currentUser;
    }

    volatile UserInfoBean CurrentUser = new UserInfoBean();

}
