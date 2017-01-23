package com.example.dysaniazzz.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.dysaniazzz.utils.ActivityCollector;
import com.example.dysaniazzz.utils.IGlobalConstants;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.welcome.LoginActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DysaniazzZ on 2016/9/5.
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContext;
    private ForceOfflineReceiver mForceOfflineReceiver;
    private static IPermissionListener sIPermissionListener;

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

    public static void requestRuntimePermissions(String[] permissions, IPermissionListener iPermissionListener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            return;
        }
        sIPermissionListener = iPermissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            //查看还有哪些未被授予的权限，加入集合
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            //动态申请权限
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size() - 1]), 1);
        } else {
            //权限都被授予了
            sIPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissionList = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissionList.add(permission);
                        }
                    }
                    if (deniedPermissionList.isEmpty()) {
                        sIPermissionListener.onGranted();
                    } else {
                        sIPermissionListener.onDenied(deniedPermissionList);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
