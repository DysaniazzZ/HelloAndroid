package com.example.dysaniazzz.chapter10.best_practice;

/**
 * Created by DysaniazzZ on 13/02/2017.
 * 第十章：下载任务的回调接口
 */
public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
