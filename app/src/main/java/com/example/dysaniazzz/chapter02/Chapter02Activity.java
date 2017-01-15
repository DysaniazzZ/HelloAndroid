package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第二章：先从看到的入手，探究活动
 */
public class Chapter02Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter02Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_02);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter02_lifecycle_test, R.id.btn_chapter02_common_usage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter02_lifecycle_test:
                LifeCycleTestActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter02_common_usage:
                CommonUsageActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
