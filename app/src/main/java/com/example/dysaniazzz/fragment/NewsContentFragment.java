package com.example.dysaniazzz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dysaniazzz.R;

/**
 * Created by DysaniazzZ on 18/12/2016.
 */
public class NewsContentFragment extends BaseFragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_news_content, container, false);
        return mView;
    }

    public void refresh(String title, String content) {
        mView.findViewById(R.id.ll_news_layout).setVisibility(View.VISIBLE);
        TextView newsTitle = (TextView) mView.findViewById(R.id.tv_news_title);
        TextView newsContent = (TextView) mView.findViewById(R.id.tv_news_content);
        newsTitle.setText(title);
        newsContent.setText(content);
    }
}
