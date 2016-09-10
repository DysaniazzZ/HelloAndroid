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
    private static ActivityCollector sActivityCollector = new ActivityCollector();
    
    //公共静态的获取实例方法
    public static ActivityCollector getInstance() {
        return sActivityCollector;
    }
    
    public List<Activity> mActivities = new ArrayList<>();
    
    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }
    
    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }
    
    public void finishAll() {
        for(Activity activity : mActivities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
