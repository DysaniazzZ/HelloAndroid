package com.example.dysaniazzz.chapter12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 17/02/2017.
 * 第十二章：使用Material Design风格的详情页面
 */
public class MaterialDetailActivity extends BaseActivity {

    @BindView(R.id.iv_material_title)
    ImageView mIvMaterialTitle;
    @BindView(R.id.tl_material_toolbar)
    Toolbar mTlMaterialToolbar;
    @BindView(R.id.ctl_material_layout)
    CollapsingToolbarLayout mCtlMaterialLayout;
    @BindView(R.id.abl_material_appbar)
    AppBarLayout mAblMaterialAppbar;
    @BindView(R.id.tv_material_content)
    TextView mTvMaterialContent;

    private Unbinder mUnbinder;
    private String mFruitName;
    private int mFruitImgId;
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE = "fruit_image";

    public static void actionStart(Context context, String fruitName, int FruitImage) {
        Intent intent = new Intent(context, MaterialDetailActivity.class);
        intent.putExtra(FRUIT_NAME, fruitName);
        intent.putExtra(FRUIT_IMAGE, FruitImage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);
        mUnbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mFruitName = intent.getStringExtra(FRUIT_NAME);
        mFruitImgId = intent.getIntExtra(FRUIT_IMAGE, 0);
    }

    private void initView() {
        setSupportActionBar(mTlMaterialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCtlMaterialLayout.setTitle(mFruitName);
        Glide.with(mContext).load(mFruitImgId).into(mIvMaterialTitle);
        String fruitContent = generateFruitContent(mFruitName);
        mTvMaterialContent.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
