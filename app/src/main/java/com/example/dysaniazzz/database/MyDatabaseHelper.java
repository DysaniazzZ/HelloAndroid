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
            + "name text)";

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
    }

}
