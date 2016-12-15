package com.example.dysaniazzz.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DysaniazzZ on 2016/9/23.
 * 网络请求的工具类
 */
public class HttpUtils {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //添加API KEY到HTTP请求头
                    connection.setRequestProperty("apikey", "1ace5a5fc9b05ec68706295e075bd5a3");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //对获取到的输入流进行读取
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        //回调onFinish()方法，注意：这里是子线程
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法，注意：这里是子线程
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 网络请求的回调
     */
    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception e);
    }
}
