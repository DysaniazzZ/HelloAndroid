package com.example.dysaniazzz.chapter12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 14/02/2017.
 * 第十二章：最佳的UI体验，Material Design实战
 */
public class Chapter12Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter12Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_12);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_chapter12_material_design)
    public void onClick() {
        MaterialDesignActivity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
