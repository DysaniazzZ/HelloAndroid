package com.example.dysaniazzz.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.example.dysaniazzz.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/19.
 * 通知栏页面
 */
public class NotificationActivity extends BaseActivity {

    Unbinder mUnbinder;
    private int NOTIFICATION_ID = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_notification_send)
    public void onSendNoticeClick() {
        //Android 6.0以前的写法：
        //NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Notification notification = new Notification(R.mipmap.notification_icon, "This is ticker text", System.currentTimeMillis());
        //notification.setLatestEventInfo(this, "This is content title", "This is content text", null);
        //manager.notify(1, notification);

        //Android 6.0以后的写法：
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(mContext);
        //瞬时提示消息
        builder.setTicker("New Message");
        //通知标题
        builder.setContentTitle("Notification Title");
        //主内容区
        builder.setContentText("Notification Content");
        //补充内容
        builder.setContentInfo("Notification Supplement");
        builder.setDefaults(Notification.DEFAULT_ALL);
        //通知的时间
        builder.setWhen(System.currentTimeMillis());

        //音频，震动，呼吸灯（貌似都无效:()
        //设置音频
        builder.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/iPhone.mp3")));
        //设置震动
        long[] vibrates = {0, 1000, 1000, 1000};    //下标为偶数为静止时长，奇数为震动时长
        builder.setVibrate(vibrates);
        //设置呼吸灯
        builder.setLights(Color.GREEN, 1000, 1000);
        //全部设置成默认
        builder.setDefaults(Notification.DEFAULT_ALL);

        //通知小图标
        builder.setSmallIcon(R.mipmap.notification_icon);
        //下拉通知栏大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_icon));
        //是否可以自动取消
        builder.setAutoCancel(true);
        //设置通知的点击事件（PendingIntent可理解为延时意图）
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(NOTIFICATION_ID ++, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
