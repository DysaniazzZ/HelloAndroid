package com.example.dysaniazzz.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DysaniazzZ on 2016/9/5.
 * 活动管理器
 */
public class ActivityCollector {

    public static List<Activity> mActivities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }

    public static Activity getTopActivity() {
        if (mActivities.isEmpty()) {
            return null;
        } else {
            return mActivities.get(mActivities.size() - 1);
        }
    }

    public static void finishAll() {
        for (Activity activity : mActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        mActivities.clear();
    }
}
