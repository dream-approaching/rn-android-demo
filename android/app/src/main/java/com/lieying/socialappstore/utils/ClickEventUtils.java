package com.lieying.socialappstore.utils;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by ${liyi} on 2017/10/13.
 */

public class ClickEventUtils {
    private final static long EVENT_DUR_TIME = 800;
    private static Map<Integer, Long> viewMap = new Hashtable<>();

    //是否为双击
    public static boolean doubleEventView(int viewId) {
        boolean isDouble = false;
        if (viewMap.containsKey(viewId)) {
            if (System.currentTimeMillis() - viewMap.get(viewId) < EVENT_DUR_TIME) {
                isDouble = true;
            } else {
                viewMap.remove(viewId);
            }
        } else {
            viewMap.put(viewId, System.currentTimeMillis());
        }
        return isDouble;
    }

}
