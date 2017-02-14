package com.example.dysaniazzz.chapter03;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.FruitBean;
import com.example.dysaniazzz.utils.UIUtils;

import java.util.List;

/**
 * Created by DysaniazzZ on 15/12/2016.
 * 第三章：RecyclerView的Adapter
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<FruitBean> mFruitBeanList;

    public FruitAdapter(List<FruitBean> fruitBeanList) {
        mFruitBeanList = fruitBeanList;
    }

    //用于创建ViewHolder的实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mFruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                FruitBean fruitBean = mFruitBeanList.get(positon);
                UIUtils.createToast(v.getContext(), "Clicked Item View: " + fruitBean.getName());
            }
        });
        holder.mFruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                FruitBean fruitBean = mFruitBeanList.get(positon);
                UIUtils.createToast(v.getContext(), "Clicked Item Image: " + fruitBean.getName());
            }
        });
        return holder;
    }

    //用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FruitBean fruitBean = mFruitBeanList.get(position);
        holder.mFruitImage.setImageResource(fruitBean.getImageId());
        holder.mFruitName.setText(fruitBean.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mFruitView;
        ImageView mFruitImage;
        TextView mFruitName;

        public ViewHolder(final View itemView) {
            super(itemView);
            mFruitView = itemView;
            mFruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            mFruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }
}
