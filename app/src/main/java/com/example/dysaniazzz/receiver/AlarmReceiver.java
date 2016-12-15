package com.example.dysaniazzz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dysaniazzz.service.LongRunningService;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 接收定时器发送的广播
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //开启后台服务
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
