package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/23.
 * 百度地图页面
 */
public class BaiduMapActivity extends BaseActivity {

    @BindView(R.id.mv_baidumap_mapview)
    MapView mMvBaidumapMapview;

    Unbinder mUnbinder;
    private BaiduMap mBaidumap;
    private double mLatitude = -1;
    private double mLongitude = -1;
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    public static void actionStart(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, BaiduMapActivity.class);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
        mUnbinder = ButterKnife.bind(this);
        initData();
        initMap();
    }

    private void initData() {
        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra(LATITUDE, -1);
        mLongitude = intent.getDoubleExtra(LONGITUDE, -1);
    }

    private void initMap() {
        mBaidumap = mMvBaidumapMapview.getMap();
        //设置地图类型
        //普通地图
        mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
        //mBaidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //空白地图，基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
        //mBaidumap.setMapType(BaiduMap.MAP_TYPE_NONE);

        //实时交通图
        //mBaidumap.setTrafficEnabled(true);
        //城市热力图
        //mBaidumap.setBaiduHeatMapEnabled(true);

        //地图Logo的位置（地图Logo不可以移除，也不允许遮挡，默认在右下角显示）
        //mMvBaidumapMapview.setLogoPosition(LogoPosition.logoPostionRightTop);

        //地图标注
        if (mLatitude != -1 && mLongitude != -1) {
            //定义Maker图标
            LatLng point = new LatLng(mLatitude, mLongitude);
            //构建Maker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marka);
            //构建MakerOption，用于在地图上添加Maker
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
            //在地图上添加Maker，并显示
            mBaidumap.addOverlay(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMvBaidumapMapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMvBaidumapMapview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMvBaidumapMapview.onDestroy();
        mUnbinder.unbind();
    }
}
