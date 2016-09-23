package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.example.dysaniazzz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/23.
 */
public class BaiduMapActivity extends BaseActivity {

    @BindView(R.id.mv_baidumap_mapview)
    MapView mMvBaidumapMapview;

    Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BaiduMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
        mUnbinder = ButterKnife.bind(this);
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
