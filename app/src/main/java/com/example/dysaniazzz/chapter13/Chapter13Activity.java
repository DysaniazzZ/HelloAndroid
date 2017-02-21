package com.example.dysaniazzz.chapter13;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.AlarmUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 20/02/2017.
 * 第十三章：继续进阶，你还应该掌握的高级技巧
 */
public class Chapter13Activity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Chapter13Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_13);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_chapter13_alarm_task, R.id.btn_chapter13_sensor_usage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chapter13_alarm_task:
//                Intent intent = new Intent(mContext, LongRunningService.class);
//                startService(intent);
                long intervalMills = AlarmManager.INTERVAL_FIFTEEN_MINUTES;       //测试十五分钟的误差大概是5秒
                AlarmUtils.setRepeatAlarmTask(mContext, System.currentTimeMillis(), intervalMills);
                break;
            case R.id.btn_chapter13_sensor_usage:
                SensorUsageActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
