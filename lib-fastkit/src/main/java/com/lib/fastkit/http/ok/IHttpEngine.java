package com.lib.fastkit.http.ok;

import android.content.Context;

import com.lib.fastkit.http.ok.extension.file.OnDownloadListener;

import java.util.Map;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/3/4.
 * Version 1.0
 * Description:  引擎的规范
 */
public interface IHttpEngine {

    // get请求
    void get(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    // post请求
    void post(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    // 下载文件

    /**
     * @param context
     * @param url              下载连接
     * @param destFileDir      下载的文件储存目录
     * @param destFileName     下载文件名称
     * @param downloadListener 下载监听
     */
    void download(Context context, String url, String destFileDir, String destFileName, OnDownloadListener downloadListener);


    // 上传文件


    void upload(Context context, String url, String destFileDir, EngineCallBack callBack);


    // https 添加证书


    //取消请求
    void cancel();
}
