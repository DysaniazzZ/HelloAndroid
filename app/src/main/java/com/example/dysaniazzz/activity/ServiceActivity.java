package com.example.dysaniazzz.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.service.DemoService;
import com.example.dysaniazzz.service.LongRunningService;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by fengzhenye on 2016/9/7.
 * 操作服务页面
 */
public class ServiceActivity extends BaseActivity {

    Unbinder mUnbinder;
    private DemoService.DownloadBinder mDownloadBinder;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DemoService.DownloadBinder) service; //向下转型拿到Binder对象
            mDownloadBinder.startDownload();
            mDownloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_service_startservice)
    public void onStartClick() {
        Intent startIntent = new Intent(mContext, DemoService.class);
        startService(startIntent);  //启动服务
    }

    @OnClick(R.id.btn_service_stopservice)
    public void onStopClick() {
        Intent stopIntent = new Intent(mContext, DemoService.class);
        stopService(stopIntent);    //停止服务
    }

    @OnClick(R.id.btn_service_bindservice)
    public void onBindClick() {
        Intent bindIntent = new Intent(mContext, DemoService.class);
        //BIND_AUTO_CREATE标志位表示Activity和Service进行绑定后自动创建Service
        bindService(bindIntent, mConnection, BIND_AUTO_CREATE); //绑定服务
    }

    @OnClick(R.id.btn_service_unbindservice)
    public void onUnbindClick() {
        unbindService(mConnection); //解绑服务（只能解绑一次）
    }

    @OnClick(R.id.btn_service_stoptask)
    public void onStarttaskClick() {
        Intent intent = new Intent(mContext, LongRunningService.class);
        startService(intent);
    }

    @OnClick(R.id.btn_service_stoptask)
    public void onStoptaskClick() {
        Intent intent = new Intent(mContext, LongRunningService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
