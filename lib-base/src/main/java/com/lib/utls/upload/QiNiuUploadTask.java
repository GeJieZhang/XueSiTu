package com.lib.utls.upload;

import android.os.AsyncTask;

import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.num.NumUtil;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.luck.picture.lib.tools.DoubleUtils;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

/**
 * Params：doInBackground方法的参数类型；
 * Progress：AsyncTask所执行的后台任务的进度类型；
 * Result：后台任务的返回结果类型。
 */

public class QiNiuUploadTask extends AsyncTask<String, Integer, String> {
    UploadManager uploadManager;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //LogUtil.e("准备上传");


    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        if (fileUploadListener != null) {
//            fileUploadListener.onSuccess(s);
//            LogUtil.e("onSuccess2");
//        }
        //LogUtil.e("上传结果:" + s);
    }


    @Override
    protected void onProgressUpdate(Integer... values) {//进度条回调方法
        super.onProgressUpdate(values);


        if (fileUploadListener != null) {
            fileUploadListener.onProgress(values[0]);
        }

        //LogUtil.e("进度:" + values[0]);
    }


    @Override
    protected String doInBackground(String... params) {//参数类型是AsyncTask最后一个参数类型

        LogUtil.e("开始上传");
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }


        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {

                        int p = (int) (NumUtil.scaleFloor(percent, 2) * 100);
                        publishProgress(p);

                        LogUtil.e("上传进度:" + p);

                    }


                }, new UpCancellationSignal() {
            @Override
            public boolean isCancelled() {
                return false;
            }
        });

        //调用uploadManager上传
        uploadManager.put(params[0], null, params[1], new UpCompletionHandler() {
            @Override
            public void complete(String key, com.qiniu.android.http.ResponseInfo info, JSONObject response) {


                try {

                    if (response != null) {
                        LogUtil.e("回调结果:" + response.toString());


                        JSONObject jsonObject = new JSONObject(response.toString());
                        String path = (String) jsonObject.get("key");


                        if (path != null) {
                            if (fileUploadListener != null) {

                                fileUploadListener.onSuccess(path);

                                LogUtil.e("onSuccess1");

                            }
                        } else {
                            if (fileUploadListener != null) {

                                fileUploadListener.onError("上传失败");

                                LogUtil.e("onError");

                            }
                        }


                    } else {

                        LogUtil.e("上传失败");
                        if (fileUploadListener != null) {

                            fileUploadListener.onError("上传失败");

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, uploadOptions);

        return params[0];
    }


    private FileUploadListener fileUploadListener;

    public void setFileUploadListener(FileUploadListener listener) {

        this.fileUploadListener = listener;

    }


}
