package com.example.dysaniazzz.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.dysaniazzz.database.MyDatabaseHelper;

/**
 * Created by Dysania on 2016/9/18.
 * 自定义的内容提供者，用来共享数据库数据
 */
public class DatabaseProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;

    public static final String AUTHORITY = "com.example.dysaniazzz.provider";

    private static UriMatcher mUriMatcher;
    private MyDatabaseHelper mMyDatabaseHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI("com.example.dysaniazzz.provider", "book", BOOK_DIR);
        mUriMatcher.addURI("com.example.dysaniazzz.provider", "book/#", BOOK_ITEM);
        mUriMatcher.addURI("com.example.dysaniazzz.provider", "category", CATEGORY_ITEM);
        mUriMatcher.addURI("com.example.dysaniazzz.provider", "category/#", CATEGORY_ITEM);
    }

    /**
     * 初始化内容提供者时调用，通常在这里完成对数据库的创建和升级等操作
     * 只用当ContentResolver尝试访问我们程序中的数据时，内容提供者才会被初始化
     * @return true表示内容提供者初始化成功，false则表示初始化失败
     */
    @Override
    public boolean onCreate() {
        mMyDatabaseHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
        return true;    //内容提供者初始化成功
    }

    /**
     * 根据传入的内容URI来返回相应的MIME类型
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        /**
         * 1. 必须以 vnd 开头；
         * 2. 如果内容 URI 以路径结尾，则后接 android.cursor.dir/，如果内容 URI 以 id 结尾，则后接 android.cursor.item/；
         * 3. 最后接上 vnd.<authority>.<path>；
         */
        switch (mUriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.dysaniazzz.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.dysaniazzz.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.dysaniazzz.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.dysaniazzz.provider.category";
        }
        return null;
    }

    /**
     * 从内容提供者中查询数据
     * @param uri 查询哪张表
     * @param projection 查询哪些列
     * @param selection 约束查询那些行
     * @param selectionArgs 约束查询的占位符填充
     * @param sortOrder 对结果进行排序
     * @return 返回带有查询结果的Cursor对象
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (mUriMatcher.match(uri)) {
            case BOOK_DIR:
                //查询book表中的所有数据
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                //查询book表中的单条数据
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id = ?", new String[]{bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                //查询category表中的所有数据
                cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                //查询category表中的单条数据
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection, "id = ?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    /**
     * 向内容提供者添加一条数据
     * @param uri 确定要添加到的表
     * @param values 待添加的数据保存在values参数中
     * @return 返回一个用于表示这条新纪录的URI
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //添加数据
        SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (mUriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("Book", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    /**
     * 从内容提供者中删除数据
     * @param uri 确定要删除哪一张表中的数据
     * @param selection 约束要删除那些行
     * @param selectionArgs 约束条件的占位符填充
     * @return 返回受影响的行数
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //删除数据
        SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (mUriMatcher.match(uri)) {
            case BOOK_DIR:
                deletedRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Book", "id = ?", new String[] {bookId});
                break;
            case CATEGORY_DIR:
                deletedRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Category", "id = ?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return deletedRows;
    }

    /**
     * 更新内容提供者中已有的数据
     * @param uri 确定要更新哪一张表中的数据
     * @param values 新数据保存在values中
     * @param selection 约束要更新哪些行
     * @param selectionArgs 约束条件的占位符填充
     * @return 返回受影响的行数
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //更新数据
        SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (mUriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("Book", values, "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updatedRows = db.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = db.update("Category", values, "id = ?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return updatedRows;
    }

}
