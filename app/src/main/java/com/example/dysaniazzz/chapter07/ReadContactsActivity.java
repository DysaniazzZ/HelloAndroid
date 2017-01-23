package com.example.dysaniazzz.chapter07;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.IPermissionListener;
import com.example.dysaniazzz.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 2016/9/18.
 * 第七章：读取系统联系人页面
 */
public class ReadContactsActivity extends BaseActivity {

    @BindView(R.id.lv_contacts_info)
    ListView mLvContactsInfo;

    private Unbinder mUnbinder;
    private ArrayAdapter<String> mContactsAdapter;
    private List<String> mContactsList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ReadContactsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mContactsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mContactsList);
        mLvContactsInfo.setAdapter(mContactsAdapter);
        //动态获取联系人权限
        requestRuntimePermissions(new String[]{Manifest.permission.READ_CONTACTS}, new IPermissionListener() {
            @Override
            public void onGranted() {
                readContacts();
            }

            @Override
            public void onDenied(List<String> deniedPermissionList) {
                UIUtils.createToast(mContext, "Your denied the read contact permission");
            }
        });
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            //查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mContactsList.add(displayName + "\n" + number);
                }
                mContactsAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
