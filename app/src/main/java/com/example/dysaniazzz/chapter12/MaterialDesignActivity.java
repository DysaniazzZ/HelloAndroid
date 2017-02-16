package com.example.dysaniazzz.chapter12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

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

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MaterialDesignActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(mTbMaterialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);              //让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);     //设置导航按钮的图标，默认是左箭头
        }
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
