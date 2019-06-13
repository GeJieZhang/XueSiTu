package com.xuesitu.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;

import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.extension.file.OnDownloadListener;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.ui.activity.BaseAppActivity;

import com.xuesitu.R;
import com.xuesitu.activity.tes.DownloadUtil;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

public class Test extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nimp);

        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(getFilesDir().getAbsolutePath());


            }
        });
    }


    private void test() {
        String url = "http://192.168.2.199:8081/download.";


    }
}
