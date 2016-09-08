package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.dysaniazzz.R;
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

    @BindView(R.id.et_guide_account)
    EditText mEtGuideAccount;
    @BindView(R.id.et_guide_password)
    EditText mEtGuidePassword;

    Unbinder mUnBinder;

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
    }

    @OnClick(R.id.btn_guide_login)
    public void onClick() {
        String account = mEtGuideAccount.getText().toString();
        String password = mEtGuidePassword.getText().toString();
        if(TextUtils.isEmpty(account)) {
            UIUtils.createToast(mContext, R.string.login_account_empty);
            return;
        }
        if(TextUtils.isEmpty(password)) {
            UIUtils.createToast(mContext, R.string.login_password_empty);
            return;
        }
        //TODO 简单实现，初始账号为admin，密码为123456
        if("admin".equals(account) && "123456".equals(password)) {
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
