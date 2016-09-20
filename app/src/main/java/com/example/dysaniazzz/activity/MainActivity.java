package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by fengzhenye on 2016/9/8.
 * 主页面
 */
public class MainActivity extends BaseActivity {

    Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        
    }

    @OnClick(R.id.btn_main_to_database)
    public void onDatabaseClick() {
        DataBaseActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_contacts)
    public void onContactsClick() {
        ContactsActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_notification)
    public void onNotificationClick() {
        NotificationActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_sms)
    public void onSmsClick() {
        SmsActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_choosepic)
    public void onChoosePicClick() {
        ChoosePicActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_demo)
    public void onDemoClick() {
        DemoActivity.actionStart(mContext);
    }
    
    @OnClick(R.id.btn_main_force_offline)
    public void onOfflineClick() {
        //发送强制下线的广播
        Intent intent = new Intent("com.example.dysaniazzz.FORCE_OFFLINE");
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
