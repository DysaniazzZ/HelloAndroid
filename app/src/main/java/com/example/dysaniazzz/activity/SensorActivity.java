package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/24.
 * 传感器页面
 */
public class SensorActivity extends BaseActivity {

    @BindView(R.id.tv_sensor_light)
    TextView mTvSensorLight;

    Unbinder mUnbinder;
    private SensorManager mSensorManager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SensorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
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

        //获取加速度传感器
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册重力感应监听
        mSensorManager.registerListener(mAccelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener mLightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //values数组中第一个下标的值就是当前的光照强度
            float lightLevel = event.values[0];
            mTvSensorLight.setText("Current light level is " + lightLevel + " lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private SensorEventListener mAccelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //加速度可能会是负值，所以要取它们的绝对值
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
        super.onDestroy();
        //释放相关资源
        if(mSensorManager != null) {
            mSensorManager.unregisterListener(mLightListener);
            mSensorManager.unregisterListener(mAccelerometerListener);
        }
        mUnbinder.unbind();
    }
}
