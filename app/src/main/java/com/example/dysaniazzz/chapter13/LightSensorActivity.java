package com.example.dysaniazzz.chapter13;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 21/02/2017.
 * 第十三章：光照传感器页面
 */
public class LightSensorActivity extends BaseActivity {

    @BindView(R.id.tv_light_value)
    TextView mTvLightValue;

    private Unbinder mUnbinder;
    private SensorManager mSensorManager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LightSensorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取传感器
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获取光照传感器
        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //注册光感监听
        mSensorManager.registerListener(mLightListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener mLightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //values数组中第一个下标的值就是当前的光照强度
            float lightLevel = event.values[0];
            mTvLightValue.setText("Current light level is " + lightLevel + " lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onDestroy() {
        //释放资源
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(mLightListener);
        }
        mUnbinder.unbind();
        super.onDestroy();
    }
}
