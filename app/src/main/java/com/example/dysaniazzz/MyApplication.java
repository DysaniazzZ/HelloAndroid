package com.example.dysaniazzz;

import android.app.Application;
import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;
import com.example.dysaniazzz.utils.StreamUtils;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by Dysania on 2016/9/23.
 * 自定义的Application（注意需要修改清单文件application的name属性）
 */
public class MyApplication extends Application {

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {    //Throwable所有异常的父类
            ex.printStackTrace();
            //把异常信息写到日志
            File file = new File(Environment.getExternalStorageDirectory(), "exception.txt");
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(file);
                ex.printStackTrace(pw);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StreamUtils.endStream(pw);
            }
            //结束当前进程(应用闪退就是这么来的...)
            //System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        initializeSdk();
        //捕获全局未处理异常
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
    }

    private void initializeSdk() {
        //initialize baidu map
        SDKInitializer.initialize(getApplicationContext());
    }

}
