package com.example.dysaniazzz.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by fengzhenye on 2016/9/8.
 * 关于UI操作的工具类
 */
public class UIUtils {
    
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

    /**
     * dp转成px的方法
     * @param v
     * @return
     */
    public static int dpToPx(float v) {
        Resources resource = Resources.getSystem();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, resource.getDisplayMetrics());
    }

    /**
     *sp转成px的方法
     * @param v
     * @return
     */
    public static int spToPx(float v) {
        Resources resource = Resources.getSystem();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, v, resource.getDisplayMetrics());
    }

    /**
     * 获取圆形图片
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        //去锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        //保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);
        canvas.drawARGB(0, 0, 0, 0);
        //圆形，所有只用一个
        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
