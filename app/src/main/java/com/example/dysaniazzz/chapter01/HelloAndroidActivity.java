package com.example.dysaniazzz.chapter01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第一章：第一个Activity页面
 */
public class HelloAndroidActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HelloAndroidActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_android);
    }
}
