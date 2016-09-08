package com.example.dysaniazzz.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengzhenye on 2016/9/5.
 * 活动管理器（单例模式）
 */
public class ActivityCollector {
    
    //私有构造
    private ActivityCollector() {}
    
    //私有静态成员变量
    private static ActivityCollector mActivityCollector = new ActivityCollector();
    
    //公共静态的获取实例方法
    public static ActivityCollector getInstance() {
        return mActivityCollector;
    }
    
    public List<Activity> sActivities = new ArrayList<>();
    
    public void addActivity(Activity activity) {
        sActivities.add(activity);
    }
    
    public void removeActivity(Activity activity) {
        sActivities.remove(activity);
    }
    
    public void finishAll() {
        for(Activity activity : sActivities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
