package com.lib.fastkit.http.ok;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.lib.fastkit.http.ok.err.HttpException;
import com.lib.fastkit.http.ok.extension.FileProgressRequestBody;
import com.lib.fastkit.http.ok.extension.file.OnDownloadListener;
import com.lib.fastkit.http.ok.interceptor.CacheInterceptor;
import com.lib.fastkit.http.ok.interceptor.RequestInterceptor;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.num.NumUtil;
import com.lib.fastkit.utils.rsa.RsaAndAesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/3/4.
 * Version 1.0
 * Description: OkHttp默认的引擎
 */
public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient;
    private static List<Call> callList = new ArrayList<>();
    private static final int TIME_OUT = 15;
    private static final String TAG = "网络请求路径======";
    private Handler mDelivery;


    public OkHttpEngine(Context context) {

        mDelivery = new Handler(Looper.getMainLooper());


//        mOkHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(new RequestInterceptor(RequestInterceptor.Level.ALL))
//                .addNetworkInterceptor(new CacheInterceptor())
//                .cache(new Cache(context.getCacheDir(), 10240 * 1024))
//                .build();


        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor(RequestInterceptor.Level.ALL))
                .build();

    }


    /**
     * 组装post请求参数body
     */
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    // 添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {

            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value instanceof File) {
                    // 处理文件 --> Object File
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody
                            .create(MediaType.parse(guessMimeType(file
                                    .getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是 List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    @Override
    public void get(final Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {


        url = HttpUtils.jointParams(url, params);

        Log.e(TAG, url);


        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .get()
                .build();


        Call call = mOkHttpClient.newCall(request);
        callList.add(call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {


                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                        callList.remove(call);

                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String result = response.body().string();
                LogUtil.e(result);

                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.code() != 200) {
                            callBack.onError(new HttpException(response));
                        } else {
                            callBack.onSuccess(result);
                        }
                        callList.remove(call);

                    }
                });
            }
        });

    }

    @Override
    public void post(final Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        final String jointUrl = HttpUtils.jointParams(url, params);  //打印
        Log.e(TAG, jointUrl);

        RequestBody requestBody = appendBody(params);

        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        callList.add(call);
        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(e);

                                callList.remove(call);
                            }
                        });

                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String result = response.body().string();
                        LogUtil.e(result);

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {


                                if (response.code() != 200) {
                                    callBack.onError(new HttpException(response));
                                } else {
                                    callBack.onSuccess(result);
                                }

                                callList.remove(call);

                            }
                        });


                    }
                }
        );

    }


    @Override
    public void download(Context context, final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {

        Log.e(TAG, url);

        Request request = new Request
                .Builder()
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        callList.add(call);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {


                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed(e);
                        callList.remove(call);

                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {


                if (response.code() != 200) {

                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed(new HttpException(response));
                        }
                    });

                } else {


                    donwloadIO(response, destFileDir, destFileName, listener);

                }

                callList.remove(call);

            }


        });
    }

    @Override
    public void upload(Context context, String url, String destFileDir, final EngineCallBack callBack) {


        File file = new File(destFileDir);

        FileProgressRequestBody filePart = new FileProgressRequestBody(file, "application/octet-stream", new FileProgressRequestBody.ProgressListener() {
            @Override
            public void transferred(long size) {

            }
        });
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("up", file.getName(), filePart)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        callList.add(call);
        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(e);

                                callList.remove(call);
                            }
                        });

                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String result = response.body().string();
                        LogUtil.e(result);

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {


                                if (response.code() != 200) {
                                    callBack.onError(new HttpException(response));
                                } else {
                                    callBack.onSuccess(result);
                                }

                                callList.remove(call);

                            }
                        });


                    }
                }
        );

    }

    private void donwloadIO(Response response, String destFileDir, String destFileName, final OnDownloadListener listener) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        // 储存下载文件的目录
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File file = new File(dir, destFileName);
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                final int progress = (int) (sum * 1.0f / total * 100);
                // final int progress = (int) (NumUtil.scaleFloor(sum/total, 2) * 100);

                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        // 下载中更新进度条
                        listener.onDownloading(progress);
                    }
                });


            }
            fos.flush();

            mDelivery.post(new Runnable() {
                @Override
                public void run() {
                    // 下载完成
                    listener.onDownloadSuccess(file);
                }
            });

        } catch (final Exception e) {

            mDelivery.post(new Runnable() {
                @Override
                public void run() {
                    listener.onDownloadFailed(e);
                }
            });

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }


    public void cancel() {
        if (callList.size() > 0) {
            for (Call call : callList) {
                call.cancel();
                callList.remove(call);

            }
        }
    }
}
