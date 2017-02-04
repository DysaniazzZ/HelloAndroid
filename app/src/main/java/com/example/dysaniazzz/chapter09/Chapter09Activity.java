package com.example.dysaniazzz.chapter09;

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
 * Created by DysaniazzZ on 04/02/2017.
 * 第九章：看看精彩的世界，使用网络技术
 */
public class Chapter09Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter09Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_09);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter09_webview_usage, R.id.btn_chapter09_network_request, R.id.btn_chapter09_okhttp_usage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter09_webview_usage:
                WebViewUsageActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter09_network_request:
                NetworkRequestActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter09_okhttp_usage:
                OkHttpUsageActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
