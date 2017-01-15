package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.VideoView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 视频播放页面
 */
public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.vv_videoplay_view)
    VideoView mVvVideoPlayView;

    Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
        mUnbinder = ButterKnife.bind(this);
        initVideoPath();
    }

    private void initVideoPath() {
        //直到视频文件路径
        File file = new File(Environment.getExternalStorageDirectory(), "ShutEmDown.flv");
        mVvVideoPlayView.setVideoPath(file.getPath());
    }

    @OnClick(R.id.btn_videoplay_play)
    public void onPlayClick() {
        if (!mVvVideoPlayView.isPlaying()) {
            mVvVideoPlayView.start();   //开始播放
        }
    }

    @OnClick(R.id.btn_videoplay_pause)
    public void onPauseClick() {
        if (mVvVideoPlayView.isPlaying()) {
            mVvVideoPlayView.pause();   //暂停播放
        }
    }

    @OnClick(R.id.btn_videoplay_replay)
    public void onReplayClick() {
        if (mVvVideoPlayView.isPlaying()) {
            mVvVideoPlayView.resume();  //重新播放
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放相关资源
        if (mVvVideoPlayView != null) {
            mVvVideoPlayView.suspend();
        }
        mUnbinder.unbind();
    }
}
