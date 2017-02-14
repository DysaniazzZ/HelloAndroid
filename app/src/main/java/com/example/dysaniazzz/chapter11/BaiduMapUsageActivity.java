package com.example.dysaniazzz.chapter11;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 14/02/2017.
 * 第十一章：百度地图的使用
 */
public class BaiduMapUsageActivity extends BaseActivity {

    @BindView(R.id.tv_baidu_map_position)
    TextView mTvBaiduMapPosition;

    private Unbinder mUnbinder;
    public LocationClient mLocationClient;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BaiduMapUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map_usage);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
            @Override
            public void onGranted() {
                requestLocation();
            }

            @Override
            public void onDenied(List<String> deniedPermissionLis) {
                UIUtils.createToast(mContext, "必须同意所有权限才能使用本功能");
                finish();
            }
        });
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);   //设置定位模式，High_Accuracy高精度模式，可优先使用GPS定位，无法接受GPS信号时使用网络定位；Battery_Saving节电模式，只使用网络定位；Device_Sensors传感器模式，只使用GPS定位
        option.setScanSpan(5000);                                                   //设置发起定位请求的间隔，要大于等于1000ms才会生效。默认为0，即只定位一次。
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            final StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n")
                    .append("经度：").append(bdLocation.getLongitude()).append("\n")
                    .append("定位方式：");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvBaiduMapPosition.setText(currentPosition.toString());
                }
            });
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        mLocationClient.stop();
        super.onDestroy();
    }
}
