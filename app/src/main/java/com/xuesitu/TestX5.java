package com.xuesitu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lib.fastkit.utils.log.LogUtil;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

public class TestX5 extends AppCompatActivity {

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x5);
        web = findViewById(R.id.web);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {

                LogUtil.e(s);
                return false;
            }
        });
        WebSettings webSetting = web.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        //webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();


        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();


//        //兼容视频
//        try {
//            if (web.getX5WebViewExtension() != null) {
//                Bundle data = new Bundle();
//
//                data.putBoolean("standardFullScreen", false);
//                //true表示标准全屏，false表示X5全屏；不设置默认false，
//
//                data.putBoolean("supportLiteWnd", false);
//                //false：关闭小窗；true：开启小窗；不设置默认true，
//
//                data.putInt("DefaultVideoScreen", 2);
//                //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
//
//                web.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }


        web.loadUrl("https://www.baidu.com/");


    }
}
