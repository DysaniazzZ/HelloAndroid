package com.example.dysaniazzz.chapter12;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.FruitBean;

import java.util.List;

/**
 * Created by DysaniazzZ on 16/02/2017.
 * 第十二章：实现Material Design结果的Adapter
 */
public class MaterialFruitAdapter extends RecyclerView.Adapter<MaterialFruitAdapter.ViewHolder> {

    private Context mContext;
    private List<FruitBean> mFruitBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mFruitImage;
        TextView mFruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mFruitImage = (ImageView) itemView.findViewById(R.id.item_fruit_image);
            mFruitName = (TextView) itemView.findViewById(R.id.item_fruit_name);
        }
    }

    public MaterialFruitAdapter(List<FruitBean> fruitBeanList) {
        mFruitBeanList = fruitBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fruit_material, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FruitBean fruitBean = mFruitBeanList.get(position);
        holder.mFruitName.setText(fruitBean.getName());
        Glide.with(mContext).load(fruitBean.getImageId()).into(holder.mFruitImage);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitBean fruitBean = mFruitBeanList.get(position);
                MaterialDetailActivity.actionStart(mContext, fruitBean.getName(), fruitBean.getImageId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFruitBeanList.size();
    }
}
