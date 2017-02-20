package com.example.dysaniazzz.utils;

import java.util.List;

/**
 * Created by DysaniazzZ on 23/01/2017.
 * 动态申请权限的回调
 */
public interface IPermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissionList);
}
