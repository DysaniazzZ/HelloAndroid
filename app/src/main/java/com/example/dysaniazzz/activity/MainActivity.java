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

    Unbinder mUnBinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        
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
        mUnBinder.unbind();
    }
}
