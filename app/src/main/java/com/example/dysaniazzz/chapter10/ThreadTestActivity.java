package com.example.dysaniazzz.chapter10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 10/02/2017.
 * 第十章：线程的使用页面
 */
public class ThreadTestActivity extends BaseActivity {

    @BindView(R.id.tv_thread_welcome_word)
    TextView mTvThreadWelcomeWord;

    private Unbinder mUnbinder;
    private static final int UPDATE_TEXT = 1;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    //在这里执行UI操作
                    mTvThreadWelcomeWord.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ThreadTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_thread_change_text)
    public void onClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(UPDATE_TEXT);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
