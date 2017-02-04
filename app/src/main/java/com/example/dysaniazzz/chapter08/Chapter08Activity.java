package com.example.dysaniazzz.chapter08;

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
 * Created by DysaniazzZ on 03/02/2017.
 * 第八章：丰富你的程序，运用手机多媒体
 */
public class Chapter08Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter08Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_08);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter08_notification_usage, R.id.btn_chapter08_camera_album})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter08_notification_usage:
                NotificationUsageActivity.actionStart(mContext);
                break;
            case R.id.btn_chapter08_camera_album:
                CameraAlbumActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
