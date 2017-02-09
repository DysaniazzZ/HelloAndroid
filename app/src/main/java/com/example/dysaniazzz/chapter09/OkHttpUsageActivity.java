package com.example.dysaniazzz.chapter09;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.App;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by DysaniazzZ on 04/02/2017.
 * 第九章：OkHttp的使用及XML解析
 */
public class OkHttpUsageActivity extends BaseActivity {

    @BindView(R.id.tv_okhttp_response)
    TextView mTvOkhttpResponse;

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OkHttpUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_usage);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_okhttp_send_request)
    public void onClick() {
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            //.url("http://172.16.10.143/get_data.xml")
//                            .url("http://172.16.10.143/get_data.json")
//                            .build();
//                    Response response = okHttpClient.newCall(request).execute(); //OkHttp的同步请求方法
//                    String responseData = response.body().string();
//                    //showResponse(responseData);
//                    //parseXMLWithPull(responseData);
//                    //parseXMLWithSAX(responseData);
//                    //parseJSONWithJSONObject(responseData);
//                    parseJSONWithGson(responseData);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        HttpUtils.sendOkHttpRequest("http://172.16.10.143/get_data.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e(e.getMessage(), null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONWithGson(response.body().string());
            }
        });
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvOkhttpResponse.setText(response);
            }
        });
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Logger.d("id is " + id);
                            Logger.d("name is " + name);
                            Logger.d("version is " + version);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            SAXHandler saxHandler = new SAXHandler();
            xmlReader.setContentHandler(saxHandler);
            //开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Logger.d("id is " + id);
                Logger.d("name is " + name);
                Logger.d("version is " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGson(String jsonData) {
        try {
            Gson gson = new Gson();
            List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {}.getType());
            for (App app : appList) {
                Logger.d("id is " + app.getId());
                Logger.d("name is " + app.getName());
                Logger.d("version is " + app.getVersion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
