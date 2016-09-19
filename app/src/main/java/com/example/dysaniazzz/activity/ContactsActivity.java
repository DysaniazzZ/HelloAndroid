package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dysaniazzz.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/18.
 * 联系人页面
 */
public class ContactsActivity extends BaseActivity {

    @BindView(R.id.lv_contacts_info)
    ListView mLvContactsInfo;

    private Unbinder mUnbinder;
    private ArrayAdapter<String> mContactsAdapter;
    private List<String> mContactsList = new ArrayList<String>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ContactsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mContactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mContactsList);
        mLvContactsInfo.setAdapter(mContactsAdapter);
        readContacts();
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            //查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while(cursor.moveToNext()) {
                //获取联系人姓名
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mContactsList.add(displayName + "\n" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
