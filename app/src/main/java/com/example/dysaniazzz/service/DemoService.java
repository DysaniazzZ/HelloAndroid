package com.example.dysaniazzz.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 用来演示服务的生命周期
 */
public class DemoService extends Service {

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate");
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
