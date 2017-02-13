package com.example.dysaniazzz.chapter10.best_practice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.utils.UIUtils;
import com.example.dysaniazzz.welcome.MainActivity;

import java.io.File;

/**
 * Created by DysaniazzZ on 13/02/2017.
 * 第十章：下载任务开启的服务
 */
public class DownloadService extends Service {

    private String mDownloadUrl;
    private DownloadTask mDownloadTask;
    private DownloadBinder mDownloadBinder = new DownloadBinder();

    private DownloadListener mDownloadListener = new DownloadListener() {

        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;
            //下载成功将前台服务关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Succeeded", -1));
            UIUtils.createToast(DownloadService.this, "Download Succeeded");
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            //下载失败将前台服务关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            UIUtils.createToast(DownloadService.this, "Download Failed");
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            //暂停下载，只提示用户就可以了
            UIUtils.createToast(DownloadService.this, "Download Paused");
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            //取消下载，需要将前台服务关闭，并提示用户
            stopForeground(true);
            UIUtils.createToast(DownloadService.this, "Download Canceled");
        }
    };

    class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (mDownloadTask == null) {
                mDownloadUrl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(mDownloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                UIUtils.createToast(DownloadService.this, "Downloading...");
            }
        }

        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.cancelDownload();
            } else {
                if (mDownloadUrl != null) {
                    //取消下载需要将文件删除，并关闭通知
                    String fileName = mDownloadUrl.substring(mDownloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    UIUtils.createToast(DownloadService.this, "Download Canceled");
                    mDownloadUrl = null;
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentIntent(pi);
        if (progress > 0) {
            //当progress大于0时才需要显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);  //第三个参数表示是否使用模糊进度条
        }
        return builder.build();
    }
}
