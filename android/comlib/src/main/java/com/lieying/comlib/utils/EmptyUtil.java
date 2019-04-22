package com.lieying.comlib.utils;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 对象判空工具
 */
public class EmptyUtil {
    private EmptyUtil(){
    }

    /**
     * 判断对象是否为空
     * @param obj 判空对象
     * @return 空-->true  非空-->false
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否非空
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 检查非空 若为空抛出异常
     * @param reference 判空对象
     * @param errorMessage 自定义错误信息
     * @param <T> 若非空将其返回
     * @return 非空对象
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * 校验数据是否为空
     *
     * @param sourceArray
     * @return
     */
    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }


    public static <V> List<V> removeNull(List<V> datas){
        List<V> newDatas = new ArrayList<V>();
        for(V data : datas){
            if(data != null){
                newDatas.add(data);
            }
        }
        return newDatas;
    }


    /**
     * 校验List是否为空
     *
     * @param sourceList
     * @return
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    /**
     * 校验Map是否为空
     *
     * @param sourceMap
     * @return
     */
    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }

    /**
     * 校验String是否为空
     *
     * @param sourceString
     * @return
     */
    public static boolean isEmpty(String sourceString) {
        return (sourceString == null || sourceString.length() == 0);
    }

    /**
     * 校验String是由空格组成
     *
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 校验JSONArray是否为空
     *
     * @param sourceJSONArray
     * @return
     */
    public static boolean isEmpty(JSONArray sourceJSONArray) {
        return (sourceJSONArray == null || sourceJSONArray.length() == 0);
    }

    /**
     * 获取非空,非空格,非"null"字符串
     *
     * @param str 源字符串
     * @return trim后的字符串或者空串
     */
    public static String getUnNullString(String str) {
        if (isBlank(str)) {
            return "";
        } else if (str.trim().toLowerCase(Locale.ENGLISH).equals("null")) {
            return "";
        } else
            return str.trim();
    }
}
