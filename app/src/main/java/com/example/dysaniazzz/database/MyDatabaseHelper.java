package com.example.dysaniazzz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dysaniazzz.utils.UIUtils;

/**
 * Created by Dysania on 2016/9/11.
 * 数据库的操作类
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    /**
     * 创建表的语句
     */
    public static final String CREATE_BOOK = "create table book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "    //real代表浮点类型
            + "pages integer, "
            + "name text, "
            + "category_id integer)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    /**
     *
     * @param context
     * @param name      数据库名字
     * @param factory   Cursor
     * @param version   数据库版本
     */
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        UIUtils.createToast(mContext, "Create succeeded");
    }

    /**
     * 更新数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库：如果之前存在同名的数据表，先将其删掉，否则会报错
        //但是这样会每次都把之前数据库的数据都清除掉，正式项目不建议使用
        //db.execSQL("drop table if exists Book");
        //db.execSQL("drop table if exists Category");
        //onCreate(db);

        //Version 1.0：只创建一个Book表
        //Version 2.0：创建Book和Category表
        //Version 3.0: 在Book表中增加一个category_id字段
        //注意，这里没有用break，是为了保证跨数据库版本升级时也能让每一项操作都执行到
        switch (oldVersion) {
            case 1:
                //如果之前版本为1，即Book已经存在，只需要创建Category
                db.execSQL(CREATE_CATEGORY);
            case 2:
                //如果之前版本是2，需要在Book表中增加一个字段
                db.execSQL("alter table Book add column category_id integer");
            default:
        }
    }

}
