package com.example.dysaniazzz.chapter12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.FruitBean;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 14/02/2017.
 * 第十二章：使用Material Design风格的页面
 */
public class MaterialDesignActivity extends BaseActivity {

    @BindView(R.id.dl_material_layout)
    DrawerLayout mDlMaterialLayout;
    @BindView(R.id.tb_material_toolbar)
    Toolbar mTbMaterialToolbar;
    @BindView(R.id.nv_material_navigation)
    NavigationView mNvMaterialNavigation;
    @BindView(R.id.rv_material_recycler)
    RecyclerView mRvMaterialRecycler;
    @BindView(R.id.srl_material_refresh)
    SwipeRefreshLayout mSrlMaterialRefresh;

    private Unbinder mUnbinder;
    private FruitBean[] mFruitBeans = {
            new FruitBean("Apple", R.drawable.bg_apple),
            new FruitBean("Banana", R.drawable.bg_banana),
            new FruitBean("Orange", R.drawable.bg_orange),
            new FruitBean("Watermelon", R.drawable.bg_watermelon),
            new FruitBean("Pear", R.drawable.bg_pear),
            new FruitBean("Pineapple", R.drawable.bg_pineapple),
            new FruitBean("Grape", R.drawable.bg_grape),
            new FruitBean("Strawberry", R.drawable.bg_strawberry),
            new FruitBean("Cherry", R.drawable.bg_cherry),
            new FruitBean("Mango", R.drawable.bg_mango)
    };
    private List<FruitBean> mFruitBeanList = new ArrayList<>();
    private MaterialFruitAdapter mMaterialFruitAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MaterialDesignActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        mUnbinder = ButterKnife.bind(this);
        initToolbar();
        initNavigationView();
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initToolbar() {
        setSupportActionBar(mTbMaterialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);              //让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);     //设置导航按钮的图标，默认是左箭头
        }
    }

    private void initNavigationView() {
        mNvMaterialNavigation.setCheckedItem(R.id.nav_call);
        mNvMaterialNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        UIUtils.createToast(mContext, "You clicked Call");
                        break;
                    case R.id.nav_friends:
                        UIUtils.createToast(mContext, "You clicked Friends");
                        break;
                    case R.id.nav_location:
                        UIUtils.createToast(mContext, "You clicked Location");
                        break;
                    case R.id.nav_mail:
                        UIUtils.createToast(mContext, "You clicked Mail");
                        break;
                    case R.id.nav_task:
                        UIUtils.createToast(mContext, "You clicked Task");
                        break;
                }
                mDlMaterialLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initRecyclerView() {
        initFruits();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRvMaterialRecycler.setLayoutManager(layoutManager);
        mMaterialFruitAdapter = new MaterialFruitAdapter(mFruitBeanList);
        mRvMaterialRecycler.setAdapter(mMaterialFruitAdapter);
    }

    private void initFruits() {
        mFruitBeanList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(mFruitBeans.length);
            mFruitBeanList.add(mFruitBeans[index]);
        }
    }

    private void initSwipeRefreshLayout() {
        mSrlMaterialRefresh.setColorSchemeResources(R.color.colorPrimary);  //设置下拉进度条的颜色
        mSrlMaterialRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        mMaterialFruitAdapter.notifyDataSetChanged();
                        mSrlMaterialRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.fab_material_button)
    public void onClick(View view) {
        //Snackbar的使用和Toast类似，传入的View只要是界面布局的任意一个View都可以，但是如果考虑到协调布局，最好传入同一个父布局下的其他子控件
        Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.createToast(mContext, "Data restored");
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDlMaterialLayout.openDrawer(GravityCompat.START);  //将滑动菜单展示出来
                break;
            case R.id.backup:
                UIUtils.createToast(mContext, "You clicked Backup");
                break;
            case R.id.delete:
                UIUtils.createToast(mContext, "You clicked Delete");
                break;
            case R.id.settings:
                UIUtils.createToast(mContext, "You clicked Settings");
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
