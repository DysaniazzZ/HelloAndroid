package com.example.dysaniazzz.chapter13;

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
 * Created by DysaniazzZ on 2016/9/24.
 * 第十三章：传感器的使用页面
 */
public class SensorUsageActivity extends BaseActivity {

    Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SensorUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_usage);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_sensor_light, R.id.btn_sensor_accelerometer, R.id.btn_sensor_practice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sensor_light:
                LightSensorActivity.actionStart(mContext);
                break;
            case R.id.btn_sensor_accelerometer:
                AccelerometerSensorActivity.actionStart(mContext);
                break;
            case R.id.btn_sensor_practice:
                CompassActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
