package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第二章：Activity的日常用法：Menu的使用，启动模式，启动方式
 */
public class CommonUsageActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CommonUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_usage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_01:
                UIUtils.createToast(mContext, "You clicked item01");
                break;
            case R.id.item_02:
                UIUtils.createToast(mContext, "You clicked item02");
                break;
            case R.id.item_03:
                UIUtils.createToast(mContext, "You clicked item03");
                break;
        }
        return true;
    }
}
