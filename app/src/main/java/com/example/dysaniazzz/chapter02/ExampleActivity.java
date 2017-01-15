package com.example.dysaniazzz.chapter02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 13/01/2017.
 * 第二章：测试意图类型和数据传递的示例页面
 */
public class ExampleActivity extends BaseActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_example);
        mUnbinder = ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String data = getIntent().getStringExtra("send_data");
        if (!TextUtils.isEmpty(data)) {
            UIUtils.createToast(mContext, data);
        }
    }

    @OnClick(R.id.btn_usage_return_data)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("return_data", "Get Result Data");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
