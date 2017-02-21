package com.example.dysaniazzz.chapter13;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

/**
 * Created by DysaniazzZ on 21/02/2017.
 * 第十三章：重力传感器页面
 */
public class AccelerometerSensorActivity extends BaseActivity {

    private SensorManager mSensorManager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AccelerometerSensorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_sensor);
        init();
    }

    private void init() {
        //获取传感器
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获取加速度传感器
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册加速度感应监听
        mSensorManager.registerListener(mAccelerometerListener, accelerometerSensor, mSensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener mAccelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //加速度可能是复制，所以这里取绝对值
            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);
            //如果手机在 X 轴或 Y 轴或 Z 轴方向上的加速度值大于 15m/s2 ，就认为用户摇动了手机
            if (xValue > 15 || yValue > 15 || zValue > 15) {
                UIUtils.createToast(mContext, "Shake It Off");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onDestroy() {
        if(mSensorManager != null) {
            mSensorManager.unregisterListener(mAccelerometerListener);
        }
        super.onDestroy();
    }
}
