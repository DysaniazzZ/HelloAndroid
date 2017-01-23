package com.example.dysaniazzz.chapter07;

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
 * Created by DysaniazzZ on 19/01/2017.
 * 第七章：跨程序共享数据，探究内容提供器
 */
public class Chapter07Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter07Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_07);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter07_runtime_permission, R.id.btn_chapter07_read_contacts})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter07_runtime_permission:
                //动态申请权限
                RuntimePermissionActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter07_read_contacts:
                //读取系统联系人
                ReadContactsActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
