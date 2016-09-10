package com.example.dysaniazzz.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fengzhenye on 2016/9/8.
 * 关于UI操作的工具类（单例模式）
 */
public class UIUtils {

    //私有构造
    private UIUtils(){}

    //私有静态成员变量
    private static UIUtils sUIUtils = new UIUtils();

    //公共静态的获取实例的方法
    public static UIUtils getInstance() {
        return sUIUtils;
    }
    
    private static Toast mToast = null;

    /**
     * 创建Toast并显示
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
     * @param context
     * @param strId strings里的字符串id
     */
    public static void createToast(Context context, int strId) {
        if (context == null) {
            return;
        }
        createToast(context, context.getResources().getString(strId));
    }
}
