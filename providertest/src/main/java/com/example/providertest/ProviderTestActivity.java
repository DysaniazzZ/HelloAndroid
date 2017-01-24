package com.example.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DysaniazzZ on 2016/01/24.
 * 第七章：测试内容提供者共享的数据
 */
public class ProviderTestActivity extends AppCompatActivity implements View.OnClickListener {

    private String mNewId;
    private TextView mTvProviderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_test);
        initView();
    }

    private void initView() {
        mTvProviderInfo = (TextView) findViewById(R.id.tv_provider_info);
        findViewById(R.id.btn_provider_insert_data).setOnClickListener(this);
        findViewById(R.id.btn_provider_update_data).setOnClickListener(this);
        findViewById(R.id.btn_provider_delete_data).setOnClickListener(this);
        findViewById(R.id.btn_provider_query_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_provider_insert_data:
                //添加数据
                Uri insertUri = Uri.parse("content://com.example.dysaniazzz.provider/book");
                ContentValues insertValues = new ContentValues();
                insertValues.put("name", "A Clash Of Kings");
                insertValues.put("author", "George Martin");
                insertValues.put("pages", 1040);
                insertValues.put("price", 22.85);
                Uri newInsertUri = getContentResolver().insert(insertUri, insertValues);
                mNewId = newInsertUri.getPathSegments().get(1);
                break;
            case R.id.btn_provider_update_data:
                //更新数据
                Uri updateUri = Uri.parse("content://com.example.dysaniazzz.provider/book/" + mNewId);
                ContentValues updateValues = new ContentValues();
                updateValues.put("name", "A Storm Of Swards");
                updateValues.put("pages", 1216);
                updateValues.put("price", 24.95);
                getContentResolver().update(updateUri, updateValues, null, null);
                break;
            case R.id.btn_provider_delete_data:
                //删除数据
                Uri deleteUri = Uri.parse("content://com.example.dysaniazzz.provider/book/" + mNewId);
                getContentResolver().delete(deleteUri, null, null);
                break;
            case R.id.btn_provider_query_data:
                //查询数据
                Uri queryUri = Uri.parse("content://com.example.dysaniazzz.provider/book");
                Cursor cursor = getContentResolver().query(queryUri, null, null, null, null);
                StringBuilder sb = new StringBuilder();
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        sb.append("book name is " + name + "\nbook author is " + author + "\nbook pages is " + pages + "\nbook price is " + price + "\n\n");
                    }
                    mTvProviderInfo.setText(sb.toString());
                    cursor.close();
                }
                break;
        }
    }
}
