package com.lieying.socialappstore.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${liyi} on 2018/2/26.
 */

public class ActivityUtils {
    private static List<Activity> activityList;

    public static boolean addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        return !activityList.contains(activity) && activityList.add(activity);
    }

    public static boolean removeActivity(Activity activity) {
        return activityList != null && activityList.size() > 0 && activityList.remove(activity);
    }


    /**
     * @param cls Activity类
     * @return finish掉的总数
     */
    public static int onFinishActivity(Class<?> cls) {
        int count = 0;
        if (activityList != null && activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (activity.getClass() == cls) {
                    count++;
                    activity.finish();
                }
            }
        }
        return count;
    }

    //关闭所有activity
    public static boolean onFinishAll() {
        boolean state = false;
        if (activityList != null && activityList.size() > 0) {
            for (Activity activity : activityList) {
                state = true;
                activity.finish();
            }
        }
        return state;
    }
}
