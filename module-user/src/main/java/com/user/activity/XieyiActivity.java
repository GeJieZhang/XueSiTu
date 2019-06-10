package com.user.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;


import com.lib.ui.activity.BaseWebActivity;

import com.lib.ui.activity.kit.BaseActivity;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.TbsLog;
import com.user.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Route(path = ARouterPathUtils.User_XieyiActivity)
public class XieyiActivity extends BaseWebActivity {
    @Override
    protected void onCreateView(WebView webView) {


//        try {
//            if (Build.VERSION.SDK_INT >= 16) {
//                Class<?> clazz = webView.getSettings().getClass();
//                Method method = clazz.getMethod(
//                        "setAllowUniversalAccessFromFileURLs", boolean.class);//利用反射机制去修改设置对象
//                if (method != null) {
//                    method.invoke(webView.getSettings(), true);//修改设置
//                }
//            }
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }


        webView.loadUrl("http://192.168.2.113:8848/xstweb/agreement.html");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xieyi;
    }


//    private WebView lin_web;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_xieyi);
//
//        lin_web = findViewById(R.id.lin_web);
//
//
//        WebSettings webSetting = lin_web.getSettings();
//        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
//        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(false);
//        // webSetting.setLoadWithOverviewMode(true);
//        webSetting.setAppCacheEnabled(true);
//        // webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
//                .getPath());
//        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        // webSetting.setPreFectch(true);
//        long time = System.currentTimeMillis();
//
//        lin_web.loadUrl("https://www.baidu.com/");
//        TbsLog.d("time-cost", "cost time: "
//                + (System.currentTimeMillis() - time));
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
//    }


}
