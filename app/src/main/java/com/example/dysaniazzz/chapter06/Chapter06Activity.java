package com.example.dysaniazzz.chapter06;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DysaniazzZ on 16/01/2017.
 * 第六章：数据存储全方案，详解持久化技术
 */
public class Chapter06Activity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter06Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_06);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter06_file_storage, R.id.btn_chapter06_shared_preferences_storage, R.id.btn_chapter06_sqlite_storage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter06_file_storage:
                //文件存储
                FileStorageActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter06_shared_preferences_storage:
                //SP存储
                SharedPreferencesStorageActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter06_sqlite_storage:
                //SQLite存储
                break;
        }
    }
}
