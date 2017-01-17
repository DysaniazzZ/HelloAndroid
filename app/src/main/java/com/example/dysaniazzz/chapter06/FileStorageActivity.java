package com.example.dysaniazzz.chapter06;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.StreamUtils;
import com.example.dysaniazzz.utils.UIUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 16/01/2017.
 * 第六章：文件存储页面
 */
public class FileStorageActivity extends BaseActivity {

    @BindView(R.id.et_file_storage_content)
    EditText mEtFileStorageContent;

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FileStorageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            mEtFileStorageContent.setText(inputText);
            mEtFileStorageContent.setSelection(inputText.length());
            UIUtils.createToast(mContext, "Restoring Succeeded");
        }
    }

    //读取文件中存储的内容
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.endStream(reader);
        }
        return content.toString();
    }

    @Override
    protected void onDestroy() {
        save(mEtFileStorageContent.getText().toString());
        mUnbinder.unbind();
        super.onDestroy();
    }

    //使用文件存储将输入的内容保存到文件中
    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtils.endStream(writer);
        }
    }
}
