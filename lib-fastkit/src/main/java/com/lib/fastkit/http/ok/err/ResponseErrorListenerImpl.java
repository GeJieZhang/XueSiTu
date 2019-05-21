/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lib.fastkit.http.ok.err;

import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.lib.fastkit.utils.log.LogUtil;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;


import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Exception t) {
        //Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        String msg = "未知错误";
        if (t instanceof ConnectException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        } else if (t instanceof NullPointerException) {


            return;
        }
        //ArmsUtils.snackbarText(msg);

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    private String convertStatusCode(HttpException httpException) {


        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.code() + "";
        }
        return msg;
    }

}
