package com.lieying.petcat.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.lieying.petcat.MainApplication;

import java.util.Map;
import java.util.Set;

public class SharedPreferencesUtil {
    public static final String mTAG = "lieying";
    //    创建一个写入器
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;
//    构造方法

    public SharedPreferencesUtil() {
        mPreferences = MainApplication.getInstance().getApplicationContext().getSharedPreferences(mTAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void clear() {
        if (null != mEditor) {
            mEditor.clear();
        }
    }

//    单例模式

    public static SharedPreferencesUtil getInstance() {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil();
        }
        return mSharedPreferencesUtil;
    }

//    存入数据

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void putStringSet(String key, Set<String> value) {
        mEditor.putStringSet(key, value);
        mEditor.commit();
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }


//    获取数据

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public Set<String> getStringSet(String key) {
        return mPreferences.getStringSet(key, null);
    }

    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    public float getFloat(String key) {
        return mPreferences.getFloat(key, 0);
    }

    public long getLong(String key) {
        return mPreferences.getLong(key, 0);
    }

    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

//    移除数据

    public void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }
}
