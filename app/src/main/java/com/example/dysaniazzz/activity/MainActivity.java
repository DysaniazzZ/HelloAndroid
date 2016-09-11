package com.example.dysaniazzz.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.database.MyDatabaseHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by fengzhenye on 2016/9/8.
 * 主页面
 */
public class MainActivity extends BaseActivity {

    Unbinder mUnBinder;
    private MyDatabaseHelper mMyDatabaseHelper;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        //版本号初始至少为1，数据库升级只需修改版本号即可
        mMyDatabaseHelper = new MyDatabaseHelper(mContext, "BookStore.db", null, 2);
    }
    
    @OnClick(R.id.btn_main_force_offline)
    public void onOfflineClick() {
        //发送强制下线的广播
        Intent intent = new Intent("com.example.dysaniazzz.FORCE_OFFLINE");
        sendBroadcast(intent);
    }

    @OnClick(R.id.btn_main_create_database)
    public void onCreateClick() {
        //mMyDatabaseHelper.getWritableDatabase();    //当数据库不可写入的时候（如磁盘空间已满）getWritableDatabase()方法则将出现异常
        mMyDatabaseHelper.getReadableDatabase();      //当数据库不可写入的时候（如磁盘空间已满）getReadableDatabase()方法返回的对象将以只读的方式去打开数据库
    }

    @OnClick(R.id.btn_main_add_data)
    public void onAddClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.96);
        db.insert("Book", null, values);    //arg0:表名   arg1:如果未指定数据自动赋值为NULL   arg2:用于添加数据
        //开始组装第二条数据
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.95);
        db.insert("Book", null, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}
