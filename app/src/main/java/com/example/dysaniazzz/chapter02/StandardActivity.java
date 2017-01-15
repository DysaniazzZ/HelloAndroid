package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 13/01/2017.
 * 第二章：Standard页面
 */
public class StandardActivity extends BaseActivity {

    @BindView(R.id.tv_task_id)
    TextView mTvTaskId;

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, StandardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        mUnbinder = ButterKnife.bind(this);
        mTvTaskId.setText("Task id: " + getTaskId());
    }

    @OnClick({R.id.btn_to_standard, R.id.btn_to_single_top, R.id.btn_to_single_task, R.id.btn_to_single_instance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_to_standard:
                StandardActivity.actionStart(mContext);
                break;
            case R.id.btn_to_single_top:
                SingleTopActivity.actionStart(mContext);
                break;
            case R.id.btn_to_single_task:
                SingleTaskActivity.actionStart(mContext);
                break;
            case R.id.btn_to_single_instance:
                SingleInstanceActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
