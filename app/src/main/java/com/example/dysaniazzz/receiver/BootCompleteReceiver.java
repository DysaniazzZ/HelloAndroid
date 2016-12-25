package com.example.dysaniazzz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dysaniazzz.utils.UIUtils;

/**
 * Created by DysaniazzZ on 2016/9/8.
 * 接收开机启动的广播
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UIUtils.createToast(context, "Boot Complete");
    }
}
