package com.example.dysaniazzz.chapter07;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 26/12/2016.
 * 第七章：动态申请权限的页面
 */
public class RuntimePermissionActivity extends BaseActivity {

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RuntimePermissionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_permission_call)
    public void onCallClick() {
        String[] permissionList = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS};
        requestRuntimePermissions(permissionList, new IPermissionListener() {
            @Override
            public void onGranted() {
                //所有权限都被授予
                call();
            }

            @Override
            public void onDenied(List<String> deniedPermissionList) {
                //有权限被拒绝
                for (String deniedPermission : deniedPermissionList) {
                    UIUtils.createToast(mContext, deniedPermission + " was denied");
                }
            }
        });
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
