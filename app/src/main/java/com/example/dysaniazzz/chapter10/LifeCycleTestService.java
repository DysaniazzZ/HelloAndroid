package com.example.dysaniazzz.chapter10;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.welcome.MainActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 第十章：测试服务的生命周期
 */
public class LifeCycleTestService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {

        public void startDownload() {
            Logger.d("startDownload executed");
        }

        public int getProgress() {
            Logger.d("getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Service Started")
                .setContentTitle("Foreground Service")
                .setContentText("The Service is running...")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }
}
