package com.example.dysaniazzz.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dysaniazzz.utils.ActivityCollector;

/**
 * Created by fengzhenye on 2016/9/5.
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity {
    
    public static final String TAG = "BaseActivity";
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //Log.d(TAG, getClass().getSimpleName());
    }
    
    private void init() {
        mContext = this;
        ActivityCollector.getInstance().addActivity(this);
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
