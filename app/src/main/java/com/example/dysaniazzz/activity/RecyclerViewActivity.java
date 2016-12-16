package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.adapter.FruitAdapter;
import com.example.dysaniazzz.bean.FruitBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 15/12/2016.
 * RecyclerView页面
 */
public class RecyclerViewActivity extends BaseActivity {

    @BindView(R.id.rv_recycler_view)
    RecyclerView mRvRecyclerView;

    private Unbinder mUnbinder;
    private List<FruitBean> mFruitBeanList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mUnbinder = ButterKnife.bind(this);
        initData(false);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recyclerview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.make_horizontal:
                initData(false);
                mLayoutManager = new LinearLayoutManager(mContext);
                ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                break;
            case R.id.make_vertical:
                initData(false);
                mLayoutManager = new LinearLayoutManager(mContext);
                ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
            case R.id.make_grid:
                initData(false);
                mLayoutManager = new GridLayoutManager(mContext, 2);
                break;
            case R.id.make_stagger:
                initData(true);
                mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        mRvRecyclerView.setLayoutManager(mLayoutManager);
        return super.onOptionsItemSelected(item);
    }

    private void initData(boolean isRandom) {
        mFruitBeanList.clear();
        for (int i = 0; i < 2; i++) {
            FruitBean apple = new FruitBean(getRandomLengthName("Apple", isRandom), R.mipmap.apple_pic);
            FruitBean banana = new FruitBean(getRandomLengthName("Banana", isRandom), R.mipmap.banana_pic);
            FruitBean orange = new FruitBean(getRandomLengthName("Orange", isRandom), R.mipmap.orange_pic);
            FruitBean watermelon = new FruitBean(getRandomLengthName("Watermelon", isRandom), R.mipmap.watermelon_pic);
            FruitBean pear = new FruitBean(getRandomLengthName("Pear", isRandom), R.mipmap.pear_pic);
            FruitBean grape = new FruitBean(getRandomLengthName("Grape", isRandom), R.mipmap.grape_pic);
            FruitBean pineapple = new FruitBean(getRandomLengthName("Pineapple", isRandom), R.mipmap.pineapple_pic);
            FruitBean strawberry = new FruitBean(getRandomLengthName("Strawberry", isRandom), R.mipmap.strawberry_pic);
            FruitBean cherry = new FruitBean(getRandomLengthName("Cherry", isRandom), R.mipmap.cherry_pic);
            FruitBean mango = new FruitBean(getRandomLengthName("Mango", isRandom), R.mipmap.mango_pic);
            mFruitBeanList.add(apple);
            mFruitBeanList.add(banana);
            mFruitBeanList.add(orange);
            mFruitBeanList.add(watermelon);
            mFruitBeanList.add(pear);
            mFruitBeanList.add(grape);
            mFruitBeanList.add(pineapple);
            mFruitBeanList.add(strawberry);
            mFruitBeanList.add(cherry);
            mFruitBeanList.add(mango);
        }
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(mContext);
        mRvRecyclerView.setLayoutManager(mLayoutManager);
        mRvRecyclerView.setAdapter(new FruitAdapter(mFruitBeanList));
    }

    private String getRandomLengthName(String name, boolean isRandom) {
        if(isRandom) {
            Random random = new Random();
            int length = random.nextInt(20) + 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(name);
            }
            return builder.toString();
        } else {
            return name;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
