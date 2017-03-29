package com.example.dysaniazzz.chapter03;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.MsgBean;
import java.util.List;

/**
 * Created by DysaniazzZ on 17/12/2016.
 * 第三章：聊天页面的Adapter(RecyclerView使用）
 */
public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.ViewHolder> {

    private List<MsgBean> mMsgBeanList;

    public ChatRvAdapter(List<MsgBean> msgBeanList) {
        this.mMsgBeanList = msgBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MsgBean msgBean = mMsgBeanList.get(position);
        if (msgBean.getType() == MsgBean.TYPE_RECEIVED) {
            //如果是收到的消息，就显示左边布局，隐藏右边布局
            holder.mRlLeftLayout.setVisibility(View.VISIBLE);
            holder.mRlRightLayout.setVisibility(View.GONE);
            holder.mTvLeftMsg.setText(msgBean.getContent());
        } else if (msgBean.getType() == MsgBean.TYPE_SENT) {
            //如果是发送的消息，就显示右边布局，隐藏左边布局
            holder.mRlLeftLayout.setVisibility(View.GONE);
            holder.mRlRightLayout.setVisibility(View.VISIBLE);
            holder.mTvRightMsg.setText(msgBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRlLeftLayout;
        RelativeLayout mRlRightLayout;
        TextView mTvLeftMsg;
        TextView mTvRightMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            mRlLeftLayout = (RelativeLayout) itemView.findViewById(R.id.rl_left_layout);
            mRlRightLayout = (RelativeLayout) itemView.findViewById(R.id.rl_right_layout);
            mTvLeftMsg = (TextView) itemView.findViewById(R.id.tv_left_msg);
            mTvRightMsg = (TextView) itemView.findViewById(R.id.tv_right_msg);
        }
    }
}
