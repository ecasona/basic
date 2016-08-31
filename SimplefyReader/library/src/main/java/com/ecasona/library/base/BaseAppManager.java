package com.ecasona.library.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by AiYang on 2016/8/29.
 * <p>
 * Description: activity 综合处理
 */
public class BaseAppManager {

    private static final String TAG = BaseAppManager.class.getSimpleName();

    private static BaseAppManager instance = null;
    private static List<Activity> activities = new LinkedList<>();

    private BaseAppManager() {
    }

    public static BaseAppManager getInstance() {
        if (null == instance) {
            synchronized (BaseAppManager.class) {
                instance = new BaseAppManager();
            }
        }
        return instance;
    }

    public int size() {
        return activities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? activities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        activities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = activities.size() - 1; i > -1; i--) {
            Activity activity = activities.get(i);
            removeActivity(activity);
            activity.finish();
            i = activities.size();
        }
    }

    public synchronized void clearToTop() {
        for (int i = activities.size() - 2; i > -1; i--) {
            Activity activity = activities.get(i);
            removeActivity(activity);
            activity.finish();
            i = activities.size() - 1;
        }
    }

}
