package com.example.dysaniazzz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dysaniazzz.R;
import com.orhanobut.logger.Logger;

/**
 * Created by DysaniazzZ on 18/12/2016.
 */
public class RightFragment extends BaseFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        Logger.d("onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d("onDetach");
    }
}
