package com.example.dysaniazzz.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.dysaniazzz.receiver.AlarmReceiver;
import com.orhanobut.logger.Logger;

import java.util.Date;

/**
 * Created by Dysania on 2016/9/22.
 * 一个可以长期在后台运行的服务
 */
public class LongRunningService extends Service {

    private AlarmManager mAlarmManager;
    private PendingIntent mOperation;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //do something...
                Logger.d("executed at " + new Date().toString());
            }
        }).start();

        //定时发送广播，开启服务
        AlarmManager mAlarmManager =  (AlarmManager) getSystemService(ALARM_SERVICE);
        int interval = 10 * 1000;
        //开机至今再加上延迟执行的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + interval;
        //long triggerAtTime = System.currentTimeMillis() + interval;
        Intent i = new Intent(this, AlarmReceiver.class);
        mOperation = PendingIntent.getBroadcast(this, 0, i, 0);
        //从Android 4.4开始，定时任务开始不准确，可以用setExact()方法替代set()方法
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, mOperation);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消定时器
        mAlarmManager.cancel(mOperation);
    }

}
