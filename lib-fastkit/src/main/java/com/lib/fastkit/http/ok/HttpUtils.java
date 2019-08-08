package com.lib.fastkit.http.ok;

import android.content.Context;

import com.lib.fastkit.http.ok.extension.file.OnDownloadListener;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.rsa.RsaAndAesUtils;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/3/4.
 * Version 1.0
 * Description:  自己的一套实现
 */
public class HttpUtils {

    // 正式
    //private String mUrl = "http://192.168.2.28:8081/xuesitu_api/api/";
    //本地
    private  static String mUrl = "";


    // 请求方式
    private int mType = POST_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0022;

    private boolean IS_ENCRYPT = false;


    private Map<String, Object> mParams;

    // 上下文
    private Context mContext;
    // 默认OkHttpEngine
    private static IHttpEngine mHttpEngine;

    private HttpUtils(Context context) {
        mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    // 请求的方式
    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    //是否使用加密
    public HttpUtils isEncrypt(boolean b) {
        IS_ENCRYPT = b;
        return this;
    }


    // 添加参数
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    // 请求头：

    // 添加回掉 执行
    public void execute(EngineCallBack callBack) {
        // 每次执行都会进入这个方法：这里添加是行不通的
        // 1.baseLibrary里面这里面不包含业务逻辑
        // 2.如果有多条业务线，
        // 让callBack回调去
        callBack.onPreExecute(mContext, mParams);

        if (callBack == null) {
            callBack = EngineCallBack.DEFUALT_CALL_BACK;
        }

        // 判断执行方法
        if (mType == POST_TYPE) {
            post(mUrl, mParams, callBack);
        }

        if (mType == GET_TYPE) {
            get(mUrl, mParams, callBack);
        }
    }


    public void execute() {
        execute(null);
    }


    // 在Application初始化引擎
    public static void init(IHttpEngine httpEngine, String url) {

        mUrl=url;
        mHttpEngine = httpEngine;

    }


    /**
     * 每次可以自带引擎
     */
    public HttpUtils exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }


    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        if (IS_ENCRYPT && params.size() > 0) {
            JSONObject json = new JSONObject(params);
            params.clear();
            Map<String, String> formatParams = RsaAndAesUtils.paramsFormat(json.toString());
            params.putAll(formatParams);
        }
        mHttpEngine.get(mContext, url, params, callBack);
    }


    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {

        if (IS_ENCRYPT && params.size() > 0) {
            JSONObject json = new JSONObject(params);
            LogUtil.e(json.toString());
            params.clear();
            Map<String, String> formatParams = RsaAndAesUtils.paramsFormat(json.toString());


            LogUtil.e(formatParams.toString());
            params.putAll(formatParams);
        }


        mHttpEngine.post(mContext, url, params, callBack);
    }


    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }


    public static void cancel() {
        if (mHttpEngine!=null){
            mHttpEngine.cancel();
        }

    }


}
