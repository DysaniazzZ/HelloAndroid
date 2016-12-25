package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;

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

    @OnClick(R.id.btn_main_to_menu)
    public void onMenuClick() {
        MenuActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_recyclerview)
    public void onRecyclerViewClick() {
        RecyclerViewActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chat)
    public void onChatClick() {
        ChatActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_fragment)
    public void onFragmentClick() {
        FragmentActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_broadcast)
    public void onBroadcastClick() {
        BroadcastActivity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_database)
    public void onDatabaseClick() {
        DatabaseActivity.actionStart(mContext);
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
