package com.example.dysaniazzz.chapter03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.MsgBean;
import com.example.dysaniazzz.common.BaseActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DysaniazzZ on 01/03/2017.
 * 第三章：聊天页面，使用ListView
 */
public class ChatPageLvActivity extends BaseActivity {

    @BindView(R.id.lv_chat_msg)
    ListView mLvChatMsg;
    @BindView(R.id.et_chat_input)
    EditText mEtChatInput;
    @BindView(R.id.tv_chat_send)
    TextView mTvChatSend;

    private Unbinder mUnbinder;
    private ChatLvAdapter mChatLvAdapter;
    private List<MsgBean> mMsgBeanList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChatPageLvActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page_lv);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        MsgBean msg1 = new MsgBean("Hello guy.", MsgBean.TYPE_RECEIVED);
        MsgBean msg2 = new MsgBean("Hi, Who is that?", MsgBean.TYPE_SENT);
        MsgBean msg3 = new MsgBean("This is Ali. Nice to talking to you.", MsgBean.TYPE_RECEIVED);
        mMsgBeanList.add(msg1);
        mMsgBeanList.add(msg2);
        mMsgBeanList.add(msg3);
        mChatLvAdapter = new ChatLvAdapter(mContext, mMsgBeanList);
        mLvChatMsg.setAdapter(mChatLvAdapter);
        mEtChatInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)) {
                    mTvChatSend.setEnabled(false);
                } else {
                    mTvChatSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick(R.id.tv_chat_send)
    public void onSendClick() {
        String sendContent = mEtChatInput.getText().toString();
        if (!TextUtils.isEmpty(sendContent)) {
            MsgBean msgBean = new MsgBean(sendContent, MsgBean.TYPE_SENT);
            mMsgBeanList.add(msgBean);
            mChatLvAdapter.notifyDataSetChanged();
            mLvChatMsg.smoothScrollToPosition(mMsgBeanList.size() - 1);
            mEtChatInput.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
