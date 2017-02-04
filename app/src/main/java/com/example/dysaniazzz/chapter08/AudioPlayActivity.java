package com.example.dysaniazzz.chapter08;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/22.
 * 第八章：音频播放页面
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
        setContentView(R.layout.activity_audio_play);
        mUnbinder = ButterKnife.bind(this);
        requestRuntimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
            @Override
            public void onGranted() {
                initMediaPlayer();
            }

            @Override
            public void onDenied(List<String> deniedPermissionList) {
                UIUtils.createToast(mContext, "You denied the permission");
                finish();
            }
        });
    }

    private void initMediaPlayer() {
        try {
            //指定音频文件路径
            File file = new File(Environment.getExternalStorageDirectory(), "John Mayer - Who Says.mp3");
            mMediaPlayer.setDataSource(file.getPath()); //指定音频文件的路径
            mMediaPlayer.prepare();                     //让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
            UIUtils.createToast(mContext, "File not found");
            finish();
        }
    }

    @OnClick({R.id.btn_audio_play_play, R.id.btn_audio_play_pause, R.id.btn_audio_play_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_audio_play_play:
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();   //开始播放
                }
                break;
            case R.id.btn_audio_play_pause:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();   //暂停播放
                }
                break;
            case R.id.btn_audio_play_stop:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.reset();    //停止播放
                    initMediaPlayer();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //释放相关资源
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mUnbinder.unbind();
        super.onDestroy();
    }
}
