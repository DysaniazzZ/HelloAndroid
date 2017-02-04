package com.example.dysaniazzz.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by DysaniazzZ on 2016/9/8.
 * 关于UI操作的工具类
 */
public class UIUtils {

    private static Toast mToast = null;

    /**
     * 创建Toast并显示
     *
     * @param context
     * @param msg
     */
    public static void createToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * 创建Toast并显示
     *
     * @param context
     * @param strId   strings里的字符串id
     */
    public static void createToast(Context context, int strId) {
        if (context == null) {
            return;
        }
        createToast(context, context.getResources().getString(strId));
    }

    /**
     * dp转成px的方法
     *
     * @param v
     * @return
     */
    public static int dpToPx(float v) {
        Resources resource = Resources.getSystem();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, resource.getDisplayMetrics());
    }

    /**
     * sp转成px的方法
     *
     * @param v
     * @return
     */
    public static int spToPx(float v) {
        Resources resource = Resources.getSystem();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, v, resource.getDisplayMetrics());
    }
}
