package com.example.dysaniazzz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.MsgBean;

import java.util.List;

/**
 * Created by DysaniazzZ on 17/12/2016.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgBean> mMsgBeanList;

    public MsgAdapter(List<MsgBean> msgBeanList) {
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
            holder.mLlLeftLayout.setVisibility(View.VISIBLE);
            holder.mLlRigthLayout.setVisibility(View.GONE);
            holder.mTvLeftMsg.setText(msgBean.getContent());
        } else if (msgBean.getType() == MsgBean.TYPE_SENT) {
            //如果是发送的消息，就显示右边布局，隐藏左边布局
            holder.mLlLeftLayout.setVisibility(View.GONE);
            holder.mLlRigthLayout.setVisibility(View.VISIBLE);
            holder.mTvRightMsg.setText(msgBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mLlLeftLayout;
        LinearLayout mLlRigthLayout;
        TextView mTvLeftMsg;
        TextView mTvRightMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlLeftLayout = (LinearLayout) itemView.findViewById(R.id.ll_left_layout);
            mLlRigthLayout = (LinearLayout) itemView.findViewById(R.id.ll_right_layout);
            mTvLeftMsg = (TextView) itemView.findViewById(R.id.tv_left_msg);
            mTvRightMsg = (TextView) itemView.findViewById(R.id.tv_right_msg);
        }
    }
}
