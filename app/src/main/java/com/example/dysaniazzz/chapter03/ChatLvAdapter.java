package com.example.dysaniazzz.chapter03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.MsgBean;
import java.util.List;

/**
 * Created by DysaniazzZ on 01/03/2017.
 */
public class ChatLvAdapter extends BaseAdapter {

    private Context mContext;
    private List<MsgBean> mMsgBeanList;

    public ChatLvAdapter(Context context, List<MsgBean> msgBeanList) {
        mContext = context;
        mMsgBeanList = msgBeanList;
    }

    @Override
    public int getCount() {
        return mMsgBeanList.size();
    }

    @Override
    public MsgBean getItem(int position) {
        return mMsgBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_item, parent, false);
            viewHolder.mRlLeftLayout = (RelativeLayout) convertView.findViewById(R.id.rl_left_layout);
            viewHolder.mRlRightLayout = (RelativeLayout) convertView.findViewById(R.id.rl_right_layout);
            viewHolder.mTvLeftMsg = (TextView) convertView.findViewById(R.id.tv_left_msg);
            viewHolder.mTvRightMsg = (TextView) convertView.findViewById(R.id.tv_right_msg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MsgBean msgBean = mMsgBeanList.get(position);
        if (msgBean.getType() == MsgBean.TYPE_RECEIVED) {
            //如果是收到的消息，就显示左边布局，隐藏右边布局
            viewHolder.mRlLeftLayout.setVisibility(View.VISIBLE);
            viewHolder.mRlRightLayout.setVisibility(View.GONE);
            viewHolder.mTvLeftMsg.setText(msgBean.getContent());
        } else if (msgBean.getType() == MsgBean.TYPE_SENT) {
            //如果是发送的消息，就显示右边布局，隐藏左边布局
            viewHolder.mRlLeftLayout.setVisibility(View.GONE);
            viewHolder.mRlRightLayout.setVisibility(View.VISIBLE);
            viewHolder.mTvRightMsg.setText(msgBean.getContent());
        }
        return convertView;
    }

    public class ViewHolder {

        RelativeLayout mRlLeftLayout;
        RelativeLayout mRlRightLayout;
        TextView mTvLeftMsg;
        TextView mTvRightMsg;
    }
}
