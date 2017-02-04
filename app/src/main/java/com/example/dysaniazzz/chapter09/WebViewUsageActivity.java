package com.example.dysaniazzz.chapter09;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 04/02/2017.
 * 第九章：WebView的使用页面
 */
public class WebViewUsageActivity extends BaseActivity {

    @BindView(R.id.wv_web_view)
    WebView mWvWebView;

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WebViewUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_usage);
        mUnbinder = ButterKnife.bind(this);
        mWvWebView.getSettings().setJavaScriptEnabled(true);
        mWvWebView.setWebViewClient(new WebViewClient());
        mWvWebView.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
