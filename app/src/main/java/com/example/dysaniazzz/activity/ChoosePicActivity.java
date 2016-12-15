package com.example.dysaniazzz.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.utils.UIUtils;
import com.example.dysaniazzz.view.ChoosePicPopView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/19.
 * 选择照片页面
 */
public class ChoosePicActivity extends BaseActivity implements ChoosePicPopView.IChoosePicListener {

    @BindView(R.id.iv_choosepic_picture)
    ImageView mIvChoosepicPicture;

    Unbinder mUnbinder;
    private ChoosePicPopView mChoosePicPopView;
    private Uri mTakePhotoUri;      //拍照后临时图像Uri
    private Uri mCropPhotoUri;      //剪切后缓存图像Uri
    private Uri mTempPhotoUri;      //相册选取的图像Uri

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 3;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChoosePicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosepic);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTakePhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "take_photo.jpg"));
        mCropPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "crop_photo.jpg"));
        mTempPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "temp_photo.jpg"));
        mChoosePicPopView = new ChoosePicPopView(mContext);
        mChoosePicPopView.setIChoosePicListener(this);
    }

    @OnClick(R.id.btn_choosepic_choose)
    public void onChooseClick() {
        mChoosePicPopView.show((Activity) mContext);
    }

    @Override
    public void onChose(int type) {
        mChoosePicPopView.dismiss();
        switch (type) {
            case ChoosePicPopView.TAKE_PHOTO:
                takePhoto();
                break;
            case ChoosePicPopView.FROM_ALBUM:
                fromAlbum();
                break;
            case ChoosePicPopView.CANCEL:
                break;
            default:
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void fromAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempPhotoUri);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    cropPhoto(mTakePhotoUri, mCropPhotoUri, CROP_REQUEST_CODE);
                } else {
                    UIUtils.createToast(mContext, "Take Photo Failed");
                }
                break;
            case GALLERY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData(), mCropPhotoUri, CROP_REQUEST_CODE);
                } else {
                    UIUtils.createToast(mContext, "From Album Failed");
                }
                break;
            case CROP_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = decodeUriAsBitmap(mContext, mCropPhotoUri);
                    if (bitmap != null) {
                        mIvChoosepicPicture.setImageBitmap(bitmap);
                    }
                } else {
                    UIUtils.createToast(mContext, "Crop Photo Failed");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 剪裁图片
     *
     * @param orgUri
     * @param desUri
     * @param requestCode
     */
    private void cropPhoto(Uri orgUri, Uri desUri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(orgUri, "image/*");
        //设置是否可剪裁
        intent.putExtra("crop", "true");
        //设置剪裁的宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置是否可缩放
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 由Uri解析成Bitmap对象
     *
     * @param context
     * @param uri
     * @return
     */
    private Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            /*下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上传到服务器，QQ头像上传采用的方法跟这个类似
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray();
            //将图片流以字符串形式存储下来
            tp = new String(Base64Coder.encodeLines(b));
            这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了，服务器处理的方法是服务器那边的事了

            如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换为我们可以用的图片类型就OK啦
            Bitmap dBitmap = BitmapFactory.decodeFile(tp);
            Drawable drawable = new BitmapDrawable(dBitmap);*/
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
        return UIUtils.getRoundedBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止android.view.WindowLeaked
        if (mChoosePicPopView != null) {
            mChoosePicPopView.dismiss();
        }
        mUnbinder.unbind();
    }
}
