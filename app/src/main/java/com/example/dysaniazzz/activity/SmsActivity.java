package com.example.dysaniazzz.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/19.
 * 短信页面
 */
public class SmsActivity extends BaseActivity {

    @BindView(R.id.tv_sms_sender)
    TextView mTVSmsSender;
    @BindView(R.id.tv_sms_content)
    TextView mTvSmsContent;
    @BindView(R.id.et_sms_to)
    EditText mEtSmsTo;
    @BindView(R.id.et_sms_msg)
    EditText mEtSmsMsg;

    Unbinder mUnbinder;
    private MessagReceiver mMessagReceiver;
    private SendStatusReceiver mSendStatusReceiver;
    private IntentFilter mReceiveFilter;
    private IntentFilter mSendFilter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SmsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        //在代码中动态注册广播接收者
        mReceiveFilter = new IntentFilter();
        mReceiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        //设置优先级（用来拦截短信广播）
        mReceiveFilter.setPriority(100);
        mMessagReceiver = new MessagReceiver();
        registerReceiver(mMessagReceiver, mReceiveFilter);

        mSendFilter = new IntentFilter();
        mSendFilter.addAction("SENT_SMS_ACTION");
        mSendStatusReceiver = new SendStatusReceiver();
        registerReceiver(mSendStatusReceiver, mSendFilter);
    }

    //短信的接收者
    class MessagReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");          //提取短信消息
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String address = messages[0].getOriginatingAddress();   //获取发送方号码
            String fullMessage = "";
            for (SmsMessage message : messages) {
                fullMessage += message.getMessageBody();            //获取短信内容
            }
            mTVSmsSender.setText(address);
            mTvSmsContent.setText(fullMessage);
            //拦截广播
            abortBroadcast();
        }

    }

    //发送状态的接收者
    class SendStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                //短信发送成功
                UIUtils.createToast(mContext, "Send succeeded");
            } else {
                //短信发送失败
                UIUtils.createToast(mContext, "Send failed");
            }
        }

    }

    @OnClick(R.id.btn_sms_send)
    public void onSendSmsClick() {
        SmsManager smsManager = SmsManager.getDefault();
        Intent sentIntent = new Intent("SENT_SMS_ACTION");
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, sentIntent, 0);
        smsManager.sendTextMessage(mEtSmsTo.getText().toString(), null, mEtSmsMsg.getText().toString(), pi, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        //反注册广播接收者
        unregisterReceiver(mMessagReceiver);
        unregisterReceiver(mSendStatusReceiver);
    }
}
