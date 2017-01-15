package com.example.dysaniazzz.chapter05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.BaseActivity;
import com.example.dysaniazzz.utils.IGlobalConstants;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 19/12/2016.
 * 第五章：广播接收者的使用页面
 */
public class BroadcastReceiverActivity extends BaseActivity {

    private Unbinder mUnbinder;
    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private CustomLocalReceiver mCustomLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    private static final String CUSTOM_LOCAL_BROADCAST = "com.example.dysaniazzz.LOCAL_BROADCAST";

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BroadcastReceiverActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        mUnbinder = ButterKnife.bind(this);
        registerLocalReceiver();
    }

    //动态注册监听网络变化的广播接收者
    @OnClick(R.id.btn_broadcast_register_network_receiver)
    public void onRegisterNetworkClick() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);
    }

    //发送本地广播
    @OnClick(R.id.btn_broadcast_send_local_broadcast)
    public void onSendLocalBroadcastClick() {
        Intent intent = new Intent(CUSTOM_LOCAL_BROADCAST);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    //发送强制下线广播
    @OnClick(R.id.btn_broadcast_send_offline_broadcast)
    public void onSendOfflineBroadcastClick() {
        Intent intent = new Intent(IGlobalConstants.FORCE_OFFLINE_BROADCAST);
        sendBroadcast(intent);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //检查网络是否可用
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                UIUtils.createToast(context, "Your network is available");
            } else {
                UIUtils.createToast(context, "Your network is unavailable");
            }
        }
    }

    /**
     * 注册本地广播
     */
    private void registerLocalReceiver() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(CUSTOM_LOCAL_BROADCAST);
        mCustomLocalReceiver = new CustomLocalReceiver();
        mLocalBroadcastManager.registerReceiver(mCustomLocalReceiver, mIntentFilter);
    }

    class CustomLocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            UIUtils.createToast(context, "Received local broadcast");
        }
    }

    @Override
    protected void onDestroy() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver);
            mNetworkChangeReceiver = null;
        }
        if(mCustomLocalReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mCustomLocalReceiver);
            mCustomLocalReceiver = null;
        }
        mUnbinder.unbind();
        super.onDestroy();
    }
}
