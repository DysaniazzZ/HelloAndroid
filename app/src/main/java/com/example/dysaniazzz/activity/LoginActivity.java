package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.utils.IGlobalConstants;
import com.example.dysaniazzz.utils.PreferenceUtils;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by fengzhenye on 2016/9/5.
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_loign_account)
    EditText mEtLoginAccount;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;
    @BindView(R.id.cb_login_remember)
    CheckBox mCbLoginRemember;

    Unbinder mUnBinder;
    String mAccount;
    String mPassword;

    //启动Activity的方法，还可以重载方法，添加参数作为传过来的数据
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnBinder = ButterKnife.bind(this);     //ButterKnife别忘了在onDestroy()方法里unbind
        init();
    }

    private void init() {
        //判断是否保存密码
        boolean isRemember = PreferenceUtils.getBoolean(mContext, IGlobalConstants.REMEMBER_PASSWORD, false);
        if(isRemember) {
            //回显账号和密码
            mAccount = PreferenceUtils.getString(mContext, "account", "");
            mPassword = PreferenceUtils.getString(mContext, "password", "");
            mEtLoginAccount.setText(mAccount);
            mEtLoginPassword.setText(mPassword);
            //这时的CheckBox为选中状态
            mCbLoginRemember.setChecked(true);
        }
    }

    @OnClick(R.id.btn_login_login)
    public void onClick() {
        mAccount = mEtLoginAccount.getText().toString();
        mPassword = mEtLoginPassword.getText().toString();
        if(TextUtils.isEmpty(mAccount)) {
            UIUtils.createToast(mContext, R.string.login_account_empty);
            return;
        }
        if(TextUtils.isEmpty(mPassword)) {
            UIUtils.createToast(mContext, R.string.login_password_empty);
            return;
        }
        //TODO 简单实现，初始账号为admin，密码为123456
        if("admin".equals(mAccount) && "123456".equals(mPassword)) {
            //检查CheckBox的选中状态
            if(mCbLoginRemember.isChecked()) {
                PreferenceUtils.putBoolean(mContext, IGlobalConstants.REMEMBER_PASSWORD, true);
                PreferenceUtils.putString(mContext, "account", mAccount);
                PreferenceUtils.putString(mContext, "password", mPassword);
            }
            MainActivity.actionStart(mContext);
            finish();
        } else {
            UIUtils.createToast(mContext, R.string.login_account_password_error);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}
