package com.example.dysaniazzz.chapter02;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 12/01/2017.
 * 第二章：Activity的日常用法：Menu的使用，启动方式，启动模式
 */
public class CommonUsageActivity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CommonUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_usage);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dial:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:10086"));
                startActivity(dialIntent);
                break;
            case R.id.browse:
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.setData(Uri.parse("https://www.baidu.com"));
                startActivity(viewIntent);
                break;
        }
        return true;
    }

    @OnClick({R.id.btn_usage_explicit_intent, R.id.btn_usage_implicit_intent, R.id.btn_usage_send_data, R.id.btn_usage_start_for_result, R.id.btn_usage_launch_mode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_usage_explicit_intent:
                Intent explicitIntent = new Intent(mContext, ExampleActivity.class);
                startActivity(explicitIntent);
                break;
            case R.id.btn_usage_implicit_intent:
                Intent implicitIntent = new Intent("com.example.dysaniazzz.ACTION_START");
                implicitIntent.addCategory("com.example.dysaniazzz.MY_CATEGORY");
                startActivity(implicitIntent);
                break;
            case R.id.btn_usage_send_data:
                String sendData = "Data from last activity";
                Intent sendDataIntent = new Intent(mContext, ExampleActivity.class);
                sendDataIntent.putExtra("send_data", sendData);
                startActivity(sendDataIntent);
                break;
            case R.id.btn_usage_start_for_result:
                Intent getResultIntent = new Intent(mContext, ExampleActivity.class);
                startActivityForResult(getResultIntent, 1);
                break;
            case R.id.btn_usage_launch_mode:
                StandardActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    String returnData = data.getStringExtra("return_data");
                    if (!TextUtils.isEmpty(returnData)) {
                        UIUtils.createToast(mContext, returnData);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
