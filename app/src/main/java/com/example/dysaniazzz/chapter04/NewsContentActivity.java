package com.example.dysaniazzz.chapter04;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

/**
 * Created by DysaniazzZ on 18/12/2016.
 * 第四章：新闻内容页面
 */
public class NewsContentActivity extends BaseActivity {

    private static final String NEWS_TITLE = "news_title";
    private static final String NEWS_CONTENT = "news_content";

    public static void actionStart(Context context, String title, String content) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra(NEWS_TITLE, title);
        intent.putExtra(NEWS_CONTENT, content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(NEWS_TITLE);
        String content = intent.getStringExtra(NEWS_CONTENT);
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.fr_news_content);
        newsContentFragment.refresh(title, content);
    }
}
