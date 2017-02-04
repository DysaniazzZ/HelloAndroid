package com.example.dysaniazzz.chapter08;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.VideoView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 第八章：视频播放页面
 */
public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.vv_video_play_view)
    VideoView mVvVideoPlayView;

    Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        mUnbinder = ButterKnife.bind(this);
        requestRuntimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
            @Override
            public void onGranted() {
                initVideoPath();
            }

            @Override
            public void onDenied(List<String> deniedPermissionList) {
                UIUtils.createToast(mContext, "You denied the permission");
                finish();
            }
        });
    }

    private void initVideoPath() {
        //直到视频文件路径
        File file = new File(Environment.getExternalStorageDirectory(), "Movie.mp4");
        if(!file.exists()) {
            UIUtils.createToast(mContext, "File not found");
            finish();
            return;
        }
        mVvVideoPlayView.setVideoPath(file.getPath());
    }

    @OnClick({R.id.btn_video_play_play, R.id.btn_video_play_pause, R.id.btn_video_play_replay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_video_play_play:
                if (!mVvVideoPlayView.isPlaying()) {
                    mVvVideoPlayView.start();   //开始播放
                }
                break;
            case R.id.btn_video_play_pause:
                if (mVvVideoPlayView.isPlaying()) {
                    mVvVideoPlayView.pause();   //暂停播放
                }
                break;
            case R.id.btn_video_play_replay:
                if (mVvVideoPlayView.isPlaying()) {
                    mVvVideoPlayView.resume();  //重新播放
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //释放相关资源
        if (mVvVideoPlayView != null) {
            mVvVideoPlayView.suspend();
        }
        mUnbinder.unbind();
        super.onDestroy();
    }
}
