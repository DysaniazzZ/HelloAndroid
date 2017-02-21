package com.example.dysaniazzz.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.chapter01.Chapter01Activity;
import com.example.dysaniazzz.chapter02.Chapter02Activity;
import com.example.dysaniazzz.chapter03.Chapter03Activity;
import com.example.dysaniazzz.chapter04.Chapter04Activity;
import com.example.dysaniazzz.chapter05.Chapter05Activity;
import com.example.dysaniazzz.chapter06.Chapter06Activity;
import com.example.dysaniazzz.chapter07.Chapter07Activity;
import com.example.dysaniazzz.chapter08.Chapter08Activity;
import com.example.dysaniazzz.chapter09.Chapter09Activity;
import com.example.dysaniazzz.chapter10.Chapter10Activity;
import com.example.dysaniazzz.chapter11.Chapter11Activity;
import com.example.dysaniazzz.chapter12.Chapter12Activity;
import com.example.dysaniazzz.chapter13.Chapter13Activity;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/8.
 * 主页面
 */
public class MainActivity extends BaseActivity {

    Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_main_to_chapter01)
    public void onChapter01Click() {
        Chapter01Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter02)
    public void onChapter02Click() {
        Chapter02Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter03)
    public void onChapter03Click() {
        Chapter03Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter04)
    public void onChapter04Click() {
        Chapter04Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter05)
    public void onChapter05Click() {
        Chapter05Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter06)
    public void onChapter06Click() {
        Chapter06Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter07)
    public void onChapter07Click() {
        Chapter07Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter08)
    public void onChapter08Click() {
        Chapter08Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter09)
    public void onChapter09Click() {
        Chapter09Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter10)
    public void onChapter10Click() {
        Chapter10Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter11)
    public void onChapter11Click() {
        Chapter11Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter12)
    public void onChapter12Click() {
        Chapter12Activity.actionStart(mContext);
    }

    @OnClick(R.id.btn_main_to_chapter13)
    public void onChapter13Click() {
        Chapter13Activity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
