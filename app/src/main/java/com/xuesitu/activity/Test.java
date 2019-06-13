package com.xuesitu.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.extension.file.OnDownloadListener;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.ui.activity.BaseAppActivity;
import com.xuesitu.R;
import com.xuesitu.activity.tes.DownloadUtil;

import java.io.File;

public class Test extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nimp);


        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(getFilesDir().getAbsolutePath());


                String url = "http://192.168.2.199:8081/download.";
                String path = getFilesDir().getAbsolutePath() + "/download";

                //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download";
                String name = "xst.zip";
                HttpUtils.with(Test.this)
                        .dowload(url, path, name, new OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(File file) {
                                LogUtil.e("保存路劲" + file.getAbsolutePath());


                            }

                            @Override
                            public void onDownloading(int progress) {

                                LogUtil.e("进度" + progress + "");

                            }

                            @Override
                            public void onDownloadFailed(Exception e) {
                                LogUtil.e("失败");
                            }
                        });


//                DownloadUtil.get().download(url, path, name, new DownloadUtil.OnDownloadListener() {
//                    @Override
//                    public void onDownloadSuccess(File file) {
//                        LogUtil.e("保存路劲" + file.getAbsolutePath());
//
//
//                    }
//
//                    @Override
//                    public void onDownloading(int progress) {
//
//                        LogUtil.e("进度" + progress + "");
//
//                    }
//
//                    @Override
//                    public void onDownloadFailed(Exception e) {
//                        LogUtil.e("失败");
//                    }
//                });

            }
        });
    }
}
