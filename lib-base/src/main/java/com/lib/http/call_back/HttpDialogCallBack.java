package com.lib.http.call_back;

import android.content.Context;

import com.google.gson.Gson;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.http.ok.EngineCallBack;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.err.ResponseErrorListenerImpl;
import com.lib.fastkit.views.dialog.http.DialogUtils;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.Map;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/3/5.
 * Version 1.0
 * Description:
 */
public abstract class HttpDialogCallBack<T> implements EngineCallBack {
    private Context context;

    private ResponseErrorListenerImpl responseErrorListener;

    private DialogUtils dialogUtils;

    private static final int LOADING_TIME = 500;

    public HttpDialogCallBack() {
        dialogUtils = new DialogUtils();
        responseErrorListener = new ResponseErrorListenerImpl();
    }

    @Override
    public void onPreExecute(final Context context, Map<String, Object> params) {
        // 大大方方的添加公用参数  与项目业务逻辑有关
        // 项目名称  context
//        params.put("app_name", "joke_essay");
//        params.put("version_name", "5.7.0");
//        params.put("ac", "wifi");
//        params.put("device_id", "30036118478");
//        params.put("device_brand", "Xiaomi");
//        params.put("update_version_code", "5701");
//        params.put("manifest_version_code", "570");
//        params.put("longitude", "113.000366");
//        params.put("latitude", "28.171377");
//        params.put("device_platform", "android");

        this.context = context;
        onPreExecute();
    }

    // 开始执行了
    public void onPreExecute() {


        dialogUtils.showNormalDialog(context, "加载中");

    }

    @Override
    public void onSuccess(String result) {


        try {

            JSONObject jsonObject = new JSONObject(result);

            int code = (int) jsonObject.get("code");

            if (code == 300) {
                EventBus.getDefault().post(new Event<String>(1, "Token失效！"), EventBusTagUtils.HttpCallBack);
                return;
            }
            Gson gson = new Gson();
            T objResult = (T) gson.fromJson(result,
                    HttpUtils.analysisClazzInfo(this));

            onSuccess(objResult);

            onSuccessHide();
        } catch (Exception e) {
            responseErrorListener.handleResponseError(context, e);
            onSuccessHide();
        }


    }

    // 返回可以直接操作的对象
    public abstract void onSuccess(T result);

    @Override
    public void onError(Exception e) {
        responseErrorListener.handleResponseError(context, e);

        onError(e.getMessage());
        onErrorHide();
    }

    public abstract void onError(String e);

    public void onErrorHide() {

        dialogUtils.dismiss();
        HttpUtils.cancel();

    }

    public void onSuccessHide() {

        dialogUtils.dismiss();
    }


}
