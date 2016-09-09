package com.example.dysaniazzz.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dysaniazzz.BuildConfig;
import com.example.dysaniazzz.utils.ActivityCollector;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by fengzhenye on 2016/9/5.
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity {
    
    public Context mContext;
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        mContext = this;
        ActivityCollector.getInstance().addActivity(this);

        //init the log tag and don't show log for the release versions
        if(BuildConfig.DEBUG) {
            Logger.init(TAG).logLevel(LogLevel.FULL);   //for debug mode, print all log
        } else {
            Logger.init().logLevel(LogLevel.NONE);      //fot release mode, remove any log
        }
        Logger.d(getClass().getSimpleName());

        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);     //友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
        //EventBus.getDefault().unregister(this);
    }
}
