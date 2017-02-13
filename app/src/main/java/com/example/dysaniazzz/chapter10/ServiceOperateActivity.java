package com.example.dysaniazzz.chapter10;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.service.LongRunningService;

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

    @OnClick(R.id.btn_service_start_service)
    public void onStartServiceClick() {
        Intent startIntent = new Intent(mContext, LifeCycleTestService.class);
        startService(startIntent);  //启动服务
    }

    @OnClick(R.id.btn_service_stop_service)
    public void onStopServiceClick() {
        Intent stopIntent = new Intent(mContext, LifeCycleTestService.class);
        stopService(stopIntent);    //停止服务
    }

    @OnClick(R.id.btn_service_bind_service)
    public void onBindServiceClick() {
        Intent bindIntent = new Intent(mContext, LifeCycleTestService.class);
        //BIND_AUTO_CREATE标志位表示Activity和Service进行绑定后自动创建Service
        bindService(bindIntent, mConnection, BIND_AUTO_CREATE); //绑定服务
    }

    @OnClick(R.id.btn_service_unbind_service)
    public void onUnbindServiceClick() {
        try {
            unbindService(mConnection); //解绑服务（只能解绑一次）
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_service_stop_task)
    public void onStartTaskClick() {
        Intent intent = new Intent(mContext, LongRunningService.class);
        startService(intent);
    }

    @OnClick(R.id.btn_service_stop_task)
    public void onStopTaskClick() {
        Intent intent = new Intent(mContext, LongRunningService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
