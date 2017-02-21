package com.example.dysaniazzz.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import com.example.dysaniazzz.chapter13.LongRunningService;
import com.example.dysaniazzz.common.MyApplication;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by DysaniazzZ on 20/02/2017.
 * 定时任务的工具类
 */
public class AlarmUtils {

    private static AlarmManager mAlarmManager;

    private static AlarmManager getAlarmManager() {
        if (mAlarmManager == null) {
            mAlarmManager = (AlarmManager) MyApplication.getContext().getSystemService(ALARM_SERVICE);
        }
        return mAlarmManager;
    }

    public static void setDelayAlarmTask(Context context, long delayMills) {
        //SystemClock.elapsedRealtime()获取的是系统开机至今所经历时间的毫秒数
        long triggerAtMills = SystemClock.elapsedRealtime() + delayMills;
        Intent intent = new Intent(context, LongRunningService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //从Android 4.4开始，定时任务开始不准确，可以用setExact()方法替代set()方法
            getAlarmManager().setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMills, pendingIntent);
        } else {
            getAlarmManager().set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMills, pendingIntent);
        }
    }

    public static void setSingleAlarmTask(Context context, long triggerAtMills) {
        Intent intent = new Intent(context, LongRunningService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //从Android 4.4开始，定时任务开始不准确，可以用setExact()方法替代set()方法
            getAlarmManager().setExact(AlarmManager.RTC_WAKEUP, triggerAtMills, pendingIntent);
        } else {
            getAlarmManager().set(AlarmManager.RTC_WAKEUP, triggerAtMills, pendingIntent);
        }
    }

    public static void setRepeatAlarmTask(Context context, long triggerAtMills, long intervalMills) {
        Intent intent = new Intent(context, LongRunningService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        //在时间点triggerAtMills执行，如果该时间已过则立即执行，然后以intervalAtMills间隔重复执行该任务，时间间隔最少为1分钟
        getAlarmManager().setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMills, intervalMills, pendingIntent);
    }

    public static void cancelAlarmTask(Context context) {
        Intent intent = new Intent(context, LongRunningService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        getAlarmManager().cancel(pendingIntent);
    }
}
