package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.adapter.MsgAdapter;
import com.example.dysaniazzz.bean.MsgBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 16/12/2016.
 * 聊天页面
 */
public class ChatActivity extends BaseActivity {

    @BindView(R.id.rv_chat_msg)
    RecyclerView mRvChatMsg;
    @BindView(R.id.et_chat_input)
    EditText mEtChatInput;

    private Unbinder mUnbinder;
    private MsgAdapter mMsgAdapter;
    private List<MsgBean> mMsgBeanList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mUnbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        MsgBean msg1 = new MsgBean("Hello guy.", MsgBean.TYPE_RECEIVED);
        MsgBean msg2 = new MsgBean("Hi, Who is that?", MsgBean.TYPE_SENT);
        MsgBean msg3 = new MsgBean("This is Ali. Nice to talking to you.", MsgBean.TYPE_RECEIVED);
        mMsgBeanList.add(msg1);
        mMsgBeanList.add(msg2);
        mMsgBeanList.add(msg3);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRvChatMsg.setLayoutManager(layoutManager);
        mMsgAdapter = new MsgAdapter(mMsgBeanList);
        mRvChatMsg.setAdapter(mMsgAdapter);
    }

    @OnClick(R.id.btn_chat_send)
    public void onSendClick() {
        String sendContent = mEtChatInput.getText().toString();
        if (!TextUtils.isEmpty(sendContent)) {
            MsgBean msgBean = new MsgBean(sendContent, MsgBean.TYPE_SENT);
            mMsgBeanList.add(msgBean);
            mMsgAdapter.notifyItemInserted(mMsgBeanList.size() - 1);    //当有新消息时，刷新RecyclerView的显示
            mRvChatMsg.scrollToPosition(mMsgBeanList.size() - 1);       //将RecyclerView滑动到最后一条
            mEtChatInput.setText("");                                   //清空输入框
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
