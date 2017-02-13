package com.example.dysaniazzz.chapter10;

import android.app.IntentService;
import android.content.Intent;

import com.orhanobut.logger.Logger;

/**
 * Created by DysaniazzZ on 13/02/2017.
 * 第十章：IntentService的使用
 */
public class MyIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //该方法在子线程执行，结束后可以自动结束服务，并回调onDestroy()方法
        Logger.d("Thread id is " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }
}
