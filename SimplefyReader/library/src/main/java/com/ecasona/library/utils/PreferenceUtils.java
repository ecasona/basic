package com.ecasona.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

/**
 * 存入以及读取缓存
 *
 * @author Administrator 2015.6.2
 */
@SuppressLint("NewApi")
public class PreferenceUtils {
    /**
     * 保存参数
     *
     * @param name 名称
     */
    public static void putString(Context context, String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 获取各项配置参数
     *
     * @return 返回"" 则 表示无此字段
     */
    public static String getString(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        String params = sharedPreferences.getString(name, "");
        return params;
    }

    /**
     * 保存数字
     *
     * @param context
     * @param name
     */
    public static void putInt(Context context, String name, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    /**
     * 获取各项配置参数
     *
     * @return params 返回-999 则 表示不存在
     */
    public static Integer getInt(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        int params = sharedPreferences.getInt(name, -999);
        return params;
    }

    /**
     * 保存小数
     *
     * @param context
     * @param name
     */
    public static void putFloat(Context context, String name, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    /**
     * 获取各项配置参数
     *
     * @return params 返回-999 则 表示不存在
     */
    public static Double getFloat(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        double params = sharedPreferences.getFloat(name, (float) -0.01);
        return params;
    }

    /**
     * 保存set
     *
     * @param context
     * @param name
     * @param value   set(仅限于String)
     */

    @SuppressLint("NewApi")
    public static void putSet(Context context, String name, Set<String> value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putStringSet(name, value);
        editor.commit();
    }

    /**
     * 获取set
     *
     * @return params 返回null 则 表示不存在
     */
    public static Set<String> getSet(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Set<String> params = sharedPreferences.getStringSet(name, null);
        return params;
    }

    /**
     * 保存set
     *
     * @param context
     * @param name
     * @param value   set(仅限于String)
     */

    @SuppressLint("NewApi")
    public static void putBoolean(Context context, String name, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    /**
     * 获取set
     *
     * @return params 返回null 则 表示不存在
     */
    public static boolean getBoolean(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        boolean params = sharedPreferences.getBoolean(name, false);
        return params;
    }

    /**
     * 将cookie写入本地缓存
     *
     * @param context
     * @param cookie  cookie 值
     */
    public static void setLocalCookie(Context context, String cookie) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("cookie", cookie);
        editor.commit();
    }

    /**
     * 获取缓存中的cookie
     *
     * @param context
     * @return
     */
    public static String getLocalCookie(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "sunflower", Context.MODE_PRIVATE);
        String params = sharedPreferences.getString("cookie", "");
        return params;

    }

}
