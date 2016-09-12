package com.ecasona.library.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.FileDownloader;
import com.android.volley.toolbox.Volley;

/**
 * Created by aiy on 2016/9/6.
 * <p/>
 * des:
 */

public class VolleyManager {

    private static RequestQueue mRequestQueue;
    //    private static RequestQueue mMultiPartQueue;
    private static FileDownloader mFileDownloader;
    private static String TAG = VolleyManager.class.getSimpleName();

    private VolleyManager() {
    }

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mFileDownloader = new FileDownloader(mRequestQueue, 1);
    }

    /**
     * @return instance of the queue
     * if init has not yet been called
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }


    /**
     * @return instance of the queue
     * if init has not yet been called
     */
    public static FileDownloader getFileDownloader() {
        if (mFileDownloader != null) {
            return mFileDownloader;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }
}
