package com.example.dysaniazzz.chapter05;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 15/01/2017.
 * 第五章：全局大喇叭，详解广播机制
 */
public class Chapter05Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter05Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_05);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_chapter05_broadcast_receiver)
    public void onClick() {
        BroadcastReceiverActivity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
