package com.example.dysaniazzz.chapter03;

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
 * Created by DysaniazzZ on 14/01/2017.
 * 第三章：软件也要拼脸蛋，UI开发的点点滴滴
 */
public class Chapter03Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter03Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_03);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter03_common_widgets, R.id.btn_chapter03_chat_page_lv, R.id.btn_chapter03_chat_page_rv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter03_common_widgets:
                CommonWidgetsActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter03_chat_page_lv:
                ChatPageLvActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter03_chat_page_rv:
                ChatPageRvActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
