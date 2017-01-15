package com.example.dysaniazzz.chapter04;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

/**
 * Created by DysaniazzZ on 18/12/2016.
 * 第四章：Fragment的使用页面
 */
public class FragmentUsageActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FragmentUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_usage);
    }
}
