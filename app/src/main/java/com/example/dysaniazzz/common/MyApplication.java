package com.example.dysaniazzz.common;

import android.app.Application;
import android.content.Context;

import com.example.dysaniazzz.BuildConfig;
import com.example.dysaniazzz.R;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

/**
 * Created by DysaniazzZ on 2016/9/23.
 * 自定义的Application（注意需要修改清单文件application的name属性）
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initializeSdk();
    }

    /**
     * 获取全局的Context
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化一些集成的SDK
     */
    private void initializeSdk() {
        //初始化 Logger SDK
        Logger.init(getString(R.string.app_name)).logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        //初始化 LitePal SDK
        LitePal.initialize(mContext);
    }
}
