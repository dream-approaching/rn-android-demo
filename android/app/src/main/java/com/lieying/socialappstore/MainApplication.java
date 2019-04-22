package com.lieying.socialappstore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.lieying.socialappstore.manager.AppActivityManager;

public class MainApplication extends Application{
    private static MainApplication mApp;

    public static MainApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mApp = this;
        AppActivityManager.getInstance();
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
    }



    /**
     * 管理activity生命周期
     */
    private static class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            AppActivityManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (null == activity) return;
            AppActivityManager.getInstance().removeActivity(activity);
        }
    }

    public Context getContext() {
        return mApp.getApplicationContext();
    }
}
