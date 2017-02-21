package com.example.dysaniazzz.chapter10;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/7.
 * 第十章：服务的操作页面
 */
public class ServiceOperateActivity extends BaseActivity {

    Unbinder mUnbinder;
    private LifeCycleTestService.DownloadBinder mDownloadBinder;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (LifeCycleTestService.DownloadBinder) service; //向下转型拿到Binder对象
            mDownloadBinder.startDownload();
            mDownloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ServiceOperateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_operate);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_service_start_service, R.id.btn_service_stop_service, R.id.btn_service_bind_service, R.id.btn_service_unbind_service, R.id.btn_service_start_intent_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_service_start_service:
                Intent startServiceIntent = new Intent(mContext, LifeCycleTestService.class);
                startService(startServiceIntent);  //启动服务
                break;
            case R.id.btn_service_stop_service:
                Intent stopServiceIntent = new Intent(mContext, LifeCycleTestService.class);
                stopService(stopServiceIntent);    //停止服务
                break;
            case R.id.btn_service_bind_service:
                Intent bindServiceIntent = new Intent(mContext, LifeCycleTestService.class);
                //BIND_AUTO_CREATE标志位表示Activity和Service进行绑定后自动创建Service
                bindService(bindServiceIntent, mConnection, BIND_AUTO_CREATE);     //绑定服务
                break;
            case R.id.btn_service_unbind_service:
                try {
                    unbindService(mConnection); //解绑服务（只能解绑一次）
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_service_start_intent_service:
                Logger.d("Thread id is " + Thread.currentThread().getId());
                Intent startIntentServiceIntent = new Intent(mContext, MyIntentService.class);
                startService(startIntentServiceIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        try {
            unbindService(mConnection); //避免内存泄露
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
