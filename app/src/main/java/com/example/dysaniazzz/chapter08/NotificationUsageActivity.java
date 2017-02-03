package com.example.dysaniazzz.chapter08;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.welcome.MainActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/19.
 * 第八章：通知的使用页面
 */
public class NotificationUsageActivity extends BaseActivity {

    Unbinder mUnbinder;
    private int mNotificationId = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NotificationUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_usage);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send_notification)
    public void onSendNotificationClick() {
        //Android 6.0以前的写法：
        //NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Notification notification = new Notification(R.mipmap.ic_notification, "This is ticker text", System.currentTimeMillis());
        //notification.setLatestEventInfo(this, "This is content title", "This is content text", null);
        //manager.notify(1, notification);

        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Android 6.0以后的写法：
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext)
                .setTicker("New Message")                   //设置收到通知时的瞬时提示信息
                .setContentTitle("Title")                   //设置通知的标题
                .setContentText("Content")                  //设置通知的内容（默认只显示一行的内容，多余一行的省略，如果要全部显示可以使用下面的bigText）
                .setContentInfo("Supplement")               //设置通知的补充内容
                .setWhen(System.currentTimeMillis())        //设置通知的时间
                .setSmallIcon(R.drawable.ic_notification)   //设置通知的小图标（用于在状态栏显示，只能使用纯alpha图层的图片）
                .setColor(Color.parseColor("#B5C7CA"))      //设置通知小图标的背景颜色（默认是灰色，有可能和大图标的主色不一致，所以手动设置成我们需要的颜色）
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))       //设置通知的大图标（用于在下拉通知栏显示）
                .setContentIntent(pendingIntent)            //设置通知的点击事件
                .setAutoCancel(true)                        //设置是否点击后自动消失

                .setSound(Uri.fromFile(new File("/system/media/audio/notifications/01_Triumph.ogg")))   //设置通知的声音（该路径为MX5手机的一个音频的路径，具体根据手机而定）
                .setVibrate(new long[]{0, 1000, 1000, 1000})                                            //设置通知的振动（下标为偶数代表静止时长，下标为奇数代表振动时长）
                .setLights(Color.GREEN, 1000, 1000)                                                     //设置通知LED灯闪烁的颜色，亮起时长和暗去时长
//                .setDefaults(NotificationCompat.DEFAULT_ALL)                                     //如果只是想要默认音频，振动和灯光效果，可以这么设置

                //设置通知的长文字
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("Believe in yourself. Under-confidence leads to a self-fulfilling prophecy that you are not good enough for your work."))
                //设置通知的大图片
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.bg_notification)))

                //设置通知的优先级（共有五级，设置最高优先级时收到通知会直接将该条通知显示在最上方）
                .setPriority(NotificationCompat.PRIORITY_MAX)

                .build();

        manager.notify(mNotificationId++, notification);    //要保证每个通知的id都是不同的
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
