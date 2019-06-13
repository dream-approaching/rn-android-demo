package com.lieying.petcat.utils;

import android.util.Log;

import com.lieying.petcat.BuildConfig;


/**
 * wudi
 * 日志封装正式环境自动去除log打印
 */
public class Logger {

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private Logger() {
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void e(String tag, String msg) {
        if (!isDebuggable())
            return;
        Log.e(tag, msg);
    }


    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void i(String tag, String msg) {
        if (!isDebuggable())
            return;

        Log.i(tag, msg);
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void d(String tag, String msg) {
        if (!isDebuggable())
            return;

        Log.i(tag, msg);
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void v(String tag, String msg) {
        if (!isDebuggable())
            return;

        Log.i(tag, msg);
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void w(String tag, String msg) {
        if (!isDebuggable())
            return;

        Log.i(tag, msg);
    }

    public static void printException(String tag, Exception e) {
        if (null != e) {
            Log.e(tag, e.getMessage());
        }
    }

}
