package com.example.dysaniazzz.chapter08;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 03/02/2017.
 * 第八章：调用摄像头和相册
 */
public class CameraAlbumActivity extends BaseActivity {

    @BindView(R.id.iv_display_photo)
    ImageView mIvDisplayPhoto;

    private Unbinder mUnbinder;
    private Uri mImageUri;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CameraAlbumActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_album);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_take_photo, R.id.btn_choose_from_album})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo:
                openCamera();
                break;
            case R.id.btn_choose_from_album:
                //需要动态获取SD卡读写权限
                requestRuntimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
                    @Override
                    public void onGranted() {
                        openAlbum();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissionList) {
                        UIUtils.createToast(mContext, "You denied the permission");
                    }
                });
                break;
        }
    }

    private void openCamera() {
        //启动相机(这里启用的是系统的相机应用，是不需要申请相机权限的）
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File outputImageFile = createImageFile();
            //Android7.0开始直接使用本地真实路径的Uri会报FileUriExposedException异常
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mImageUri = FileProvider.getUriForFile(mContext, "com.example.dysaniazzz.fileprovider", outputImageFile);
            } else {
                mImageUri = Uri.fromFile(outputImageFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(takePictureIntent, TAKE_PHOTO);
        }
    }

    private File createImageFile() {
        //创建File对象，用于存储拍照后的图片，getExternalCacheDir这个方法返回的目录为应用关联缓存目录，刚好不用动态申请权限
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String outputImageFileName = "IMG_" + timeStamp + ".jpg";
        File outputImageFile = new File(getExternalCacheDir(), outputImageFileName);
        try {
            if (outputImageFile.exists()) {
                outputImageFile.delete();
            }
            outputImageFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputImageFile;
    }

    private void openAlbum() {
        Intent choosePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        choosePictureIntent.setType("image/*");
        startActivityForResult(choosePictureIntent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                        mIvDisplayPhoto.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageAfterKitKat(data);
                    } else {
                        //4.3及以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageAfterKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];    //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 根据提供的参数获取图片的路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String imagePath = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return imagePath;
    }

    /**
     * 根据图片路径展示图片
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mIvDisplayPhoto.setImageBitmap(bitmap);
        } else {
            UIUtils.createToast(mContext, "Failed to get image");
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
