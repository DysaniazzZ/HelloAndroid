package com.example.dysaniazzz.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.database.MyDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by fengzhenye on 2016/9/12.
 * 数据库操作Demo页面(CRUD)
 */
public class DataBaseActivity extends BaseActivity {
    
    @BindView(R.id.tv_database_info)
    TextView mTvDatabaseInfo;

    Unbinder mUnBinder;
    private MyDatabaseHelper mMyDatabaseHelper;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DataBaseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        mUnBinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        //版本号初始至少为1，数据库升级只需修改版本号即可
        mMyDatabaseHelper = new MyDatabaseHelper(mContext, "BookStore.db", null, 2);
    }

    @OnClick(R.id.btn_database_create_database)
    public void onCreateClick() {
        //mMyDatabaseHelper.getWritableDatabase();    //当数据库不可写入的时候（如磁盘空间已满）getWritableDatabase()方法则将出现异常
        mMyDatabaseHelper.getReadableDatabase();      //当数据库不可写入的时候（如磁盘空间已满）getReadableDatabase()方法返回的对象将以只读的方式去打开数据库
    }

    @OnClick(R.id.btn_database_insert_data)
    public void onInsertClick() {
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

    @OnClick(R.id.btn_database_update_data)
    public void onUpdateClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 10.99);
        db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});   //arg2和arg3用于去约束更新某一行或某几行中的数据，不指定则默认更新所有行
    }
    
    @OnClick(R.id.btn_database_delete_data)
    public void onDeleteClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        db.delete("Book", "pages > ?", new String[]{"500"});
    }
    
    @OnClick(R.id.btn_database_query_data)
    public void onQueryClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        //arg0:table            指定查询的表名
        //arg1:columns          指定查询的列名，如果不指定则默认查询所有列
        //arg2:selection        指定where的约束条件
        //arg3:selectionArgs    为where中的占位符提供具体的值
        //arg4:groupBy          指定需要group by的列
        //arg5:having           对group by后的结果进一步约束
        //arg6:orderBy          指定查询结果的排序方式

        //查询表中多有的数据
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        StringBuilder sb = new StringBuilder();
        //遍历Cursor对象，取出数据并打印
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                sb.append("book name is " + name + "\nbook author is " + author + "\nbook pages is " + pages + "\nbook price is " + price + "\n\n");
                
            } while (cursor.moveToNext());
        }
        mTvDatabaseInfo.setText(sb.toString());
        cursor.close();
    }
    
    //使用SQL语句操作数据库
    @OnClick(R.id.btn_database_sql_insert)
    public void onSQLInsertClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)", new String[] { "解忧杂货店", "东野圭吾", "291", "39.50" });
    }

    @OnClick(R.id.btn_database_sql_update)
    public void onSQLUpdateClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        db.execSQL("update Book set price = ? where name = ?", new String[] { "0.00", "解忧杂货店" });
    }

    @OnClick(R.id.btn_database_sql_delete)
    public void onSQLDeleteClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        db.execSQL("delete from Book where pages > ?", new String[] { "200" });
    }

    @OnClick(R.id.btn_database_sql_query)
    public void onSQLQueryClick() {
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book", null);
        StringBuilder sb = new StringBuilder();
        //遍历Cursor对象，取出数据并打印
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                sb.append("book name is " + name + "\nbook author is " + author + "\nbook pages is " + pages + "\nbook price is " + price + "\n\n");

            } while (cursor.moveToNext());
        }
        mTvDatabaseInfo.setText(sb.toString());
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}