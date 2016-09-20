package com.example.dysaniazzz.activity;

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

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/19.
 * 选择照片页面
 */
public class ChoosePicActivity extends BaseActivity {

    @BindView(R.id.iv_choosepic_picture)
    ImageView mIvChoosepicPicture;

    Unbinder mUnbinder;
    private Uri mImageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChoosePicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosepic);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_choosepic_takephoto)
    public void onTakephotoClick() {
        //创建File对象，用于存储拍照后的图片
        File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");

        /*try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        mImageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, TAKE_PHOTO);                     //启动相机程序
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(mImageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, CROP_PHOTO);         //启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                        mIvChoosepicPicture.setImageBitmap(bitmap);     //将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
