package com.example.dysaniazzz.chapter06;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 17/01/2017.
 * 第六章：SharedPreference存储页面
 */
public class SharedPreferencesStorageActivity extends BaseActivity {

    @BindView(R.id.et_shared_preferences_storage_name)
    EditText mEtSharedPreferencesStorageName;
    @BindView(R.id.et_shared_preferences_storage_gender)
    EditText mEtSharedPreferencesStorageGender;

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SharedPreferencesStorageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences_storage);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("User_Info", MODE_PRIVATE);
        String userName = preferences.getString("user_name", "");
        String userGender = preferences.getString("user_gender", "");
        if(!TextUtils.isEmpty(userName)) {
            mEtSharedPreferencesStorageName.setText(userName);
        }
        if(!TextUtils.isEmpty(userGender)) {
            mEtSharedPreferencesStorageGender.setText(userGender);
        }
    }

    @OnClick(R.id.btn_shared_preferences_save)
    public void onClick() {
        SharedPreferences.Editor editor = getSharedPreferences("User_Info", MODE_PRIVATE).edit();
        editor.putString("user_name", mEtSharedPreferencesStorageName.getText().toString());
        editor.putString("user_gender", mEtSharedPreferencesStorageGender.getText().toString());
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
