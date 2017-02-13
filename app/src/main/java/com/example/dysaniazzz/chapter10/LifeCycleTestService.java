package com.example.dysaniazzz.chapter10;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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
