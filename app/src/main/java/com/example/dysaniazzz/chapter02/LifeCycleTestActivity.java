package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第二章：测试Activity生命周期的页面
 */
public class LifeCycleTestActivity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LifeCycleTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_test);
        mUnbinder = ButterKnife.bind(this);
        Logger.d("onCreate");
    }

    @OnClick({R.id.btn_lifecycle_to_normal, R.id.btn_lifecycle_to_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lifecycle_to_normal:
                NormalActivity.actionStart(mContext);
                break;
            case R.id.btn_lifecycle_to_dialog:
                DialogActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("onStop");
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
        Logger.d("onDestroy");
    }
}
