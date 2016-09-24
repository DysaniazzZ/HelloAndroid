package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.dysaniazzz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/24.
 * 指南针页面
 */
public class CompassActivity extends BaseActivity {

    @BindView(R.id.iv_compass_circle)
    ImageView mIvCompassCircle;
    @BindView(R.id.iv_compass_arrow)
    ImageView mIvCompassArrow;

    Unbinder mUnbinder;
    private SensorManager mSensorManager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CompassActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //加速度传感器
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //地磁传感器
        Sensor magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(mListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mListener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private SensorEventListener mListener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        private float lastRotateDegree;

        @Override
        public void onSensorChanged(SensorEvent event) {
            //判断当前是加速度传感器还是地磁传感器
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //注意赋值时调用clone()方法
                accelerometerValues = event.values.clone();
            } else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                //注意赋值时调用clone()方法
                magneticValues = event.values.clone();
            }
            //得到包含旋转矩阵的R数组
            float[] R = new float[9];
            mSensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            //计算手机旋转数据
            float[] values = new float[3];
            mSensorManager.getOrientation(R, values);
            //values[0]表示手机围绕Z轴旋转的弧度
            //将计算出的角度取反，用于旋转指南针背景图
            float rotateDegree = -(float)Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
                RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                mIvCompassCircle.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSensorManager != null) {
            mSensorManager.unregisterListener(mListener);
        }
        mUnbinder.unbind();
    }

}
