package com.example.dysaniazzz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dysaniazzz.utils.UIUtils;

/**
 * Created by fengzhenye on 2016/9/8.
 * 接收强制下线的Receiver
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UIUtils.createToast(context, "Hello");
    }
}
