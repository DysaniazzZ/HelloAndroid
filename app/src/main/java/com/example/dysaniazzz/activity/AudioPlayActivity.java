package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 音频播放页面
 */
public class AudioPlayActivity extends BaseActivity {

    Unbinder mUnbinder;
    private MediaPlayer mMediaPlayer = new MediaPlayer();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AudioPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplay);
        mUnbinder = ButterKnife.bind(this);
        initMediaPlayer();  //初始化MediaPlayer
    }

    private void initMediaPlayer() {
        try {
            //指定音频文件路径
            File file = new File(Environment.getExternalStorageDirectory(), "Evil and Angel.mp3");
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.prepare();                     //让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_audioplay_play)
    public void onPlayClick() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();   //开始播放
        }
    }

    @OnClick(R.id.btn_audioplay_pause)
    public void onPauseClick() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();   //暂停播放
        }
    }

    @OnClick(R.id.btn_audioplay_stop)
    public void onStopClick() {
        //if(mMediaPlayer.isPlaying()) {
        mMediaPlayer.reset();    //停止播放
        initMediaPlayer();
        //}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放相关资源
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mUnbinder.unbind();
    }
}
