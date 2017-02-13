package com.example.dysaniazzz.chapter10.best_practice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 13/02/2017.
 * 第十章：下载任务的操作页面
 */
public class DownloadActivity extends BaseActivity {

    private Unbinder mUnbinder;
    private DownloadService.DownloadBinder mDownloadBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DownloadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = new Intent(mContext, DownloadService.class);
        startService(intent);                                       //启动服务
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);  //绑定服务
        requestRuntimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {

            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(List<String> deniedPermissionList) {
                UIUtils.createToast(mContext, "Permission was denied");
                finish();
            }
        });
    }

    @OnClick({R.id.btn_download_start, R.id.btn_download_pause, R.id.btn_download_cancel})
    public void onClick(View view) {
        if (mDownloadBinder == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_download_start:
                String downloadUrl = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                mDownloadBinder.startDownload(downloadUrl);
                break;
            case R.id.btn_download_pause:
                mDownloadBinder.pauseDownload();
                break;
            case R.id.btn_download_cancel:
                mDownloadBinder.cancelDownload();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        unbindService(mServiceConnection);  //避免内存泄露
        super.onDestroy();
    }
}
