package com.ecasona.library.utils;

import android.util.Log;

/**
 * Created by AiYang on 2016/8/30.
 * <p>
 * Description:自定义log打印
 */
public class ELog {

    private static final boolean isLogEnable = true;

    public static void i(String TAG, String msg) {
        if (isLogEnable) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (isLogEnable) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (isLogEnable) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (isLogEnable) {
            Log.v(TAG, msg);
        }
    }

}
