package com.example.dysaniazzz.chapter12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        setSupportActionBar(mTbMaterialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);              //让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);     //设置导航按钮的图标，默认是左箭头
        }
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
