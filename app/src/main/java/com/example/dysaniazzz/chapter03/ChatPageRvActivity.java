package com.example.dysaniazzz.chapter03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import android.widget.TextView;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.bean.MsgBean;
import com.example.dysaniazzz.utils.StreamUtils;
import com.example.dysaniazzz.utils.UIUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 16/12/2016.
 * 第三章：聊天页面，使用RecyclerView
 */
public class ChatPageRvActivity extends BaseActivity {

    @BindView(R.id.rv_chat_msg)
    RecyclerView mRvChatMsg;
    @BindView(R.id.et_chat_input)
    EditText mEtChatInput;
    @BindView(R.id.tv_chat_send)
    TextView mTvChatSend;

    private Unbinder mUnbinder;
    private String mChatMsg;
    private ChatRvAdapter mChatRvAdapter;
    private List<MsgBean> mMsgBeanList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChatPageRvActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page_rv);
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
        mChatMsg = load();
    }

    private String load() {
        FileInputStream fis = null;
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        try {
            fis = openFileInput("ChatMsg");
            br = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtils.endStream(br);
        }
        return content.toString();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRvChatMsg.setLayoutManager(layoutManager);
        mChatRvAdapter = new ChatRvAdapter(mMsgBeanList);
        mRvChatMsg.setAdapter(mChatRvAdapter);
        if (!TextUtils.isEmpty(mChatMsg)) {
            mEtChatInput.setText(mChatMsg);
            mEtChatInput.setSelection(mChatMsg.length());
            UIUtils.createToast(mContext, "Restoring Succeeded");
        }
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
            mChatRvAdapter.notifyItemInserted(mMsgBeanList.size() - 1);    //当有新消息时，刷新RecyclerView的显示
            mRvChatMsg.scrollToPosition(mMsgBeanList.size() - 1);       //将RecyclerView滑动到最后一条
            mEtChatInput.setText("");                                   //清空输入框
        }
    }

    @Override
    protected void onDestroy() {
        //获取没有发送的内容，并保存到文件中
        mChatMsg = mEtChatInput.getText().toString();
        save(mChatMsg);
        mUnbinder.unbind();
        super.onDestroy();
    }

    private void save(String chatMsg) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            fos = openFileOutput("ChatMsg", Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(chatMsg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.endStream(bw);
        }
    }
}
