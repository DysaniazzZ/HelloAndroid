package com.example.dysaniazzz.chapter01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第一章：开始启程，你的第一行Android代码
 */
public class Chapter01Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter01Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_01);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_chapter01_hello_android)
    public void onClick() {
        HelloAndroidActivity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
