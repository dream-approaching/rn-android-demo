package com.lieying.petcat.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/7/14.
 */

public class PermissionUtil {
    /**
     * 检测权限并申请
     */
    @TargetApi(23)
    public static boolean fgmCheckPermission(Fragment fragment, Context context, int requestCode, String... permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] arr = getNoGrantedPermission(context, permission);
            if (arr.length > 0) {
                fragment.requestPermissions(arr, requestCode);
                return true;
            }
        }
        return false;
    }

    /**
     * 检测权限并申请
     */
    @TargetApi(23)
    public static boolean checkPermission(Context context, int requestCode, String[] permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] arr = getNoGrantedPermission(context, permission);
            if (arr.length > 0) {
                ActivityCompat.requestPermissions((Activity) context, arr, requestCode);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取没有被授权的权限
     *
     * @return
     */
    @TargetApi(23)
    private static String[] getNoGrantedPermission(Context context, String... permission) {
        List<String> arr = new ArrayList<>();
        for (String str : permission) {
            if (!isGranted(context, str)) {
                arr.add(str);
            }
        }
        return arr.toArray(new String[arr.size()]);
    }

    /**
     * 是否已经授权
     *
     * @param permission
     * @return
     */
    @TargetApi(23)
    public static boolean isGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 应用权限界面
     */
    public static void startAppPermissionActivity(Activity activity, int requestCode) {
        try {//小米界面
            activity.startActivityForResult(getMIUIPermissionActivity(activity), requestCode);
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {//魅族
            activity.startActivityForResult(getMeiZuPermissionActivity(activity), requestCode);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {//华为
            activity.startActivityForResult(getHuaWeiPermissionActivity(), requestCode);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intentAll = new Intent();
        intentAll.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intentAll.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intentAll, requestCode);
    }

    /**
     * 应用权限界面
     */
    public static void startAppPermissionActivity(Fragment fragment, Context context, int requestCode) {
//        try {//小米界面
//            fragment.startActivityForResult(getMIUIPermissionActivity(context), requestCode);
//            return;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        try {//魅族
//            fragment.startActivityForResult(getMeiZuPermissionActivity(context), requestCode);
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {//华为
//            fragment.startActivityForResult(getHuaWeiPermissionActivity(), requestCode);
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Intent intentAll = new Intent();//如果用前面且出现异常会有立即回调的现象
        intentAll.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intentAll.setData(Uri.fromParts("package", context.getPackageName(), null));
        fragment.startActivityForResult(intentAll, requestCode);
    }

    /**
     * 华为权限设置界面
     *
     * @return
     */
    private static Intent getHuaWeiPermissionActivity() throws Exception {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        intent.setComponent(comp);
        return intent;
    }

    /**
     * 魅族权限设置界面
     *
     * @return
     */
    private static Intent getMeiZuPermissionActivity(Context context) throws Exception {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());
        return intent;
    }

    /**
     * 小米权限设置界面
     *
     * @return
     */
    private static Intent getMIUIPermissionActivity(Context context) throws Exception {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", context.getPackageName());
        return intent;
    }

    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    public static boolean getAppOps(Context context) {
        try {
            Object object = context.getSystemService("appops");
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(24);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
