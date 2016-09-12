package com.ecasona.library.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ecasona.library.net.*;


/**
 * Created by aiy on 2016/9/6.
 * <p>
 * des:
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SetVersionName(getVersion());
        VolleyManager.init(this);
    }

    public String getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "暂无版本号";
        }
    }

    public static String versionName;

    public static void SetVersionName(String version) {
        versionName = version;
    }

    public static String getVersionName() {
        return versionName;
    }

}
