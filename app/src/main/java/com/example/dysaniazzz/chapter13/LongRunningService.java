package com.example.dysaniazzz.chapter13;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 第十三章：可以长期在后台运行的服务
 */
public class LongRunningService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这里执行具体的业务逻辑
                Logger.d("executed at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        }).start();

//        int delayMills = 10 * 1000;
//        AlarmUtils.setDelayAlarmTask(this, delayMills);

        return super.onStartCommand(intent, flags, startId);
    }
}
