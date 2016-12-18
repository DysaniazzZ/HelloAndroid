package com.example.dysaniazzz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.activity.NewsContentActivity;
import com.example.dysaniazzz.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DysaniazzZ on 18/12/2016.
 */
public class NewsTitleFragment extends BaseFragment {

    private boolean isTwoPane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_title, container, false);
        RecyclerView rvNewsTitle = (RecyclerView) view.findViewById(R.id.rv_news_title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvNewsTitle.setLayoutManager(layoutManager);
        NewsAdapter newsAdapter = new NewsAdapter(getNews());
        rvNewsTitle.setAdapter(newsAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;       //双页模式
        } else {
            isTwoPane = false;      //单页模式
        }
    }

    private List<NewsBean> getNews() {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle("This is news title " + i);
            newsBean.setContent(getRandomLengthContent("This is news content " + i + "." + "\n"));
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }

    private String getRandomLengthContent(String content) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(content);
        }
        return builder.toString();
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<NewsBean> mNewsBeanList;

        public NewsAdapter(List<NewsBean> newsBeanList) {
            this.mNewsBeanList = newsBeanList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsBean newsBean = mNewsBeanList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        //如果是双页模式，则刷新NewsContentFragment里的内容
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.fr_news_content);
                        newsContentFragment.refresh(newsBean.getTitle(), newsBean.getContent());
                    } else {
                        //如果是单页模式，则直接启动NewsContentActivity
                        NewsContentActivity.actionStart(getActivity(), newsBean.getTitle(), newsBean.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            NewsBean newsBean = mNewsBeanList.get(position);
            holder.mTvNewsTitle.setText(newsBean.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsBeanList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTvNewsTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                mTvNewsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            }
        }
    }
}
