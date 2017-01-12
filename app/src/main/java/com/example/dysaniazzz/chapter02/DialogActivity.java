package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.BaseActivity;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第二章：测试Activity生命周期的Dialog页面
 */
public class DialogActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DialogActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}
