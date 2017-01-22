package com.example.dysaniazzz.common;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dysaniazzz.utils.ActivityCollector;
import com.example.dysaniazzz.utils.IGlobalConstants;
import com.example.dysaniazzz.welcome.LoginActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by DysaniazzZ on 2016/9/5.
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContext;
    private ForceOfflineReceiver mForceOfflineReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityCollector.addActivity(this);
        Logger.d(getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerOfflineReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //保证只有栈顶Activity才能收到该广播
        if (mForceOfflineReceiver != null) {
            unregisterReceiver(mForceOfflineReceiver);
            mForceOfflineReceiver = null;
        }
    }

    public void registerOfflineReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IGlobalConstants.FORCE_OFFLINE_BROADCAST);
        mForceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(mForceOfflineReceiver, intentFilter);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("Warning");
            dialogBuilder.setMessage("You are forced to be offline. Please try to login again.");
            dialogBuilder.setCancelable(false);         //不可取消，只能点击对话框的按钮
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();      //销毁所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);      //重启LoginActivity
                }
            });
            dialogBuilder.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
