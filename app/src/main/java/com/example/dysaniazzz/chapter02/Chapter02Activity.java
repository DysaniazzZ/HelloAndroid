package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.BaseActivity;

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

    @OnClick(R.id.btn_chapter02_lifecycle_test)
    public void onClick() {
        LifeCycleTestActivity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
