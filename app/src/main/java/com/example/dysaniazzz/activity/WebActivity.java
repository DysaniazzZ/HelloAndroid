package com.example.dysaniazzz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.HttpUtils;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dysania on 2016/9/23.
 * 展示网页页面
 */
public class WebActivity extends BaseActivity {

    /*@BindView(R.id.wv_web_view)
    WebView mWvWebView;*/
    @BindView(R.id.tv_web_response)
    TextView mTvWebResponse;

    Unbinder mUnbinder;
    private static final int RESPONSE_SUCCEED = 0;
    private static final int RESPONSE_ERROR = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESPONSE_SUCCEED:
                    String respose = (String) msg.obj;
                    //在这里进行UI操作，将结果显示再页面上
                    mTvWebResponse.setText(respose);
                    break;
                case RESPONSE_ERROR:
                    String error = (String) msg.obj;
                    UIUtils.createToast(mContext, error);
                    break;
                default:
                    break;
            }
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        /*mWvWebView.getSettings().setJavaScriptEnabled(true);
        mWvWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  //根据传入的参数去加载网页
                return true;        //表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器
            }
        });
        mWvWebView.loadUrl("http://www.baidu.com");*/
    }

    @OnClick(R.id.btn_web_sendrequest)
    public void onSendClick() {
        sendRequest();
    }

    private void sendRequest() {
        String url = "http://www.baidu.com";
        HttpUtils.sendHttpRequest(url, new HttpUtils.HttpCallbackListener() {

            Message message = new Message();

            @Override
            public void onFinish(String response) {
                message.what = RESPONSE_SUCCEED;
                //将服务器返回的结果存放到Message中
                message.obj = response.toString();
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {
                message.what = RESPONSE_ERROR;
                message.obj = e.toString();
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
