package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.chapter01.Chapter01Activity;
import com.example.dysaniazzz.chapter02.Chapter02Activity;
import com.example.dysaniazzz.chapter03.Chapter03Activity;
import com.example.dysaniazzz.chapter04.Chapter04Activity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/8.
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
    }

    @OnClick(R.id.btn_main_to_chapter01)
    public void onChapter01Click() {
        Chapter01Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter02)
    public void onChapter02Click() {
        Chapter02Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter03)
    public void onChapter03Click() {
        Chapter03Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter04)
    public void onChapter04Click() {
        Chapter04Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_broadcast)
    public void onBroadcastClick() {
        BroadcastActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_database)
    public void onDatabaseClick() {
        DatabaseActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_runtimepermission)
    public void onPermissionClick() {
        RuntimePermissionActivity.actionStart(mContext);
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

    @OnClick(R.id.btn_main_to_audioplay)
    public void onAudioPlayClick() {
        AudioPlayActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_videoplay)
    public void onVideoPlayClick() {
        VideoPlayActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_service)
    public void onServiceClick() {
        ServiceActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_web)
    public void onWebClick() {
        WebActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_location)
    public void onLocationClick() {
        LocationActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_baidumap)
    public void onBaidumapClick() {
        BaiduMapActivity.actionStart(mContext, -1, -1);
    }

    @OnClick(R.id.btn_main_to_sensor)
    public void onSensorClick() {
        SensorActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_compass)
    public void onCompassClick() {
        CompassActivity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
