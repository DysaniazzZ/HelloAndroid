package com.example.dysaniazzz.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.dysaniazzz.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dysania on 2016/9/21.
 * 选择照片的自定义PopupWindow
 */
public class ChoosePicPopView extends PopupWindow {

    private Context mContext;
    private View mMenuView;
    private PopupWindow mPopupWindow;
    private IChoosePicListener mIChoosePicListener;
    public static final int TAKE_PHOTO = 1; //相机拍照
    public static final int FROM_ALBUM = 2; //相册获取
    public static final int CANCEL = 3;

    public ChoosePicPopView(Context context) {
        this(context, null);
    }

    public ChoosePicPopView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ChoosePicPopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mMenuView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_choosepic, null);
        ButterKnife.bind(this, mMenuView);
    }

    @OnClick(R.id.btn_choosepic_takephoto)
    public void onTakephotoClick() {
        if(mIChoosePicListener != null) {
            mIChoosePicListener.onChose(TAKE_PHOTO);
        }
    }

    @OnClick(R.id.btn_choosepic_fromalbum)
    public void onFromalbumClick() {
        if(mIChoosePicListener != null) {
            mIChoosePicListener.onChose(FROM_ALBUM);
        }
    }

    @OnClick(R.id.btn_choosepic_cancel)
    public void onCancelClick() {
        if(mIChoosePicListener != null) {
            mIChoosePicListener.onChose(CANCEL);
        }
    }

    /**
     * 选择监听的接口
     */
    public interface IChoosePicListener {
        void onChose(int type);
    }

    /**
     * 设置接口回调监听
     * @param iChoosePicListener
     */
    public void setIChoosePicListener(IChoosePicListener iChoosePicListener) {
        this.mIChoosePicListener = iChoosePicListener;
    }

    public void show(Activity activity) {
        if(mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //注意：必须要设置背景，播放动画有一个前提，就是窗体必须有背景
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置显示的位置
            mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            //设置动画的样式
            mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
            mPopupWindow.setFocusable(false);
            mPopupWindow.update();
        }
    }

    public void dismiss() {
        if(mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

}
