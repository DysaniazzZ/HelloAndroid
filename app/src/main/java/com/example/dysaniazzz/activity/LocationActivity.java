package com.example.dysaniazzz.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.utils.HttpUtils;
import com.example.dysaniazzz.utils.UIUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/23.
 * 获取定位页面
 */
public class LocationActivity extends BaseActivity {

    @BindView(R.id.tv_location_coordinate)
    TextView mTvLocationCoordinate;
    @BindView(R.id.tv_location_position)
    TextView mTvLocationPosition;

    Unbinder mUnbinder;
    private String mProvider;
    private LocationManager mLocationManager;
    private static final int SHOW_LOCATION = 1;
    private double mLatitude = -1;
    private double mLongitude = -1;

    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新当前设备的位置信息
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_LOCATION:
                    String position = (String) msg.obj;
                    mTvLocationPosition.setText(position);
                    break;
                default:
                    break;
            }
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LocationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取LocationManager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = mLocationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            mProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            mProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            //当没有可用的位置提供器时，弹出提示
            UIUtils.createToast(mContext, "No Location Provider To Use!");
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            Location location = mLocationManager.getLastKnownLocation(mProvider);
            if (location != null) {
                //显示当前设备的位置信息
                showLocation(location);
            }
            //设置更新的频率及回调
            //String provider, long minTime, float minDistance, LocationListener listener
            mLocationManager.requestLocationUpdates(mProvider, 5000, 1, mLocationListener);
        }

    }

    private void showLocation(final Location location) {
        //显示当前的经纬度坐标
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        String currentPosition = "Latitude is " + mLatitude + "\n" + "Longitude is " + mLongitude;
        mTvLocationCoordinate.setText(currentPosition);
        //联网获取位置信息
        StringBuilder url = new StringBuilder();
        url.append("http://apis.baidu.com/wxlink/here/here?");
        url.append("lat=");
        url.append(mLatitude);
        url.append("&lng=");
        url.append(mLongitude);
        url.append("&cst=1");
        HttpUtils.sendHttpRequest(url.toString(), new HttpUtils.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(!TextUtils.isEmpty(response)) {
                    LocationResult locationResult = new Gson().fromJson(response, LocationResult.class);
                    if(locationResult != null && locationResult.result != null) {
                        List<LocationResult.District> districts = locationResult.result;
                        StringBuilder stringBuilder = new StringBuilder();
                        for(LocationResult.District district : districts) {
                            stringBuilder.append(district.DistrictName);
                            stringBuilder.append("\n");
                        }
                        Message message = new Message();
                        message.what = SHOW_LOCATION;
                        message.obj = stringBuilder.toString();
                        mHandler.sendMessage(message);
                    }
                }
            }
            @Override
            public void onError(Exception e) {
                Logger.d(e.getMessage());
            }
        });

    }

    class LocationResult {
        public List<District> result;
        public String msg;
        public String code;

        class District {
            public String ID;
            public String DistrictName;
            public String TypeName;
        }
    }

    @OnClick(R.id.btn_location_detail)
    public void onDetailClick() {
        if(mLatitude != -1 && mLongitude != -1) {
            BaiduMapActivity.actionStart(mContext, mLatitude, mLongitude);
            finish();
        } else {
            UIUtils.createToast(mContext, "Get Coordinate Failed");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭程序时将监听器移除
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // ActivityCompat#requestPermissions here to request the missing permissions, and then overriding
                // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation for ActivityCompat#requestPermissions for more details.
                return;
            } else {
                mLocationManager.removeUpdates(mLocationListener);
            }
        }
        mUnbinder.unbind();
    }

}
