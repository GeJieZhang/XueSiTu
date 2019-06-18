package com.lib.fastkit.utils.log;


import android.util.Log;

/**
 * Created by XiaoJianjun on 2017/1/16.
 * Log日志工具，封装logger
 */
public class LogUtil {
    //用于在开发模式与上线模式的切换
    private static final boolean DEBUG = true;
    private static final String TAG = "调试日志======";

    public static void e(String content) {

        String str = "空日志信息";

        if (content != null ||! content.equals("null") || !content.equals("")) {
            str = content;
        }

        if (DEBUG) {
            Log.e(TAG, str);
        }
    }


}
