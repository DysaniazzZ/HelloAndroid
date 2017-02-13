package com.example.dysaniazzz.chapter10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.chapter10.best_practice.DownloadActivity;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 10/02/2017.
 * 第十章：后台默默的劳动者，探究服务
 */
public class Chapter10Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter10Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_10);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter10_thread_test, R.id.btn_chapter10_service_usage, R.id.btn_chapter10_best_practice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter10_thread_test:
                ThreadTestActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter10_service_usage:
                ServiceOperateActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter10_best_practice:
                DownloadActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
