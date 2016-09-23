package com.example.dysaniazzz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dysania on 2016/9/10.
 * SharePreferenceUtils的工具类
 */
public class PreferenceUtils {

    private static SharedPreferences sp;

    public static SharedPreferences getSharedPreferences(Context context) {
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        sp = getSharedPreferences(context);
        sp.edit().putBoolean(key, value).commit();
    }
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        sp = getSharedPreferences(context);
        return sp.getBoolean(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        sp = getSharedPreferences(context);
        sp.edit().putString(key, value).commit();
    }
    public static String getString(Context context, String key, String defValue) {
        sp = getSharedPreferences(context);
        return sp.getString(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        sp = getSharedPreferences(context);
        sp.edit().putInt(key, value).commit();
    }
    public static int getInt(Context context, String key, int defValue) {
        sp = getSharedPreferences(context);
        return sp.getInt(key, defValue);
    }

    public static void remove(Context context, String key) {
        sp = getSharedPreferences(context);
        sp.edit().remove(key).commit();
    }
}
