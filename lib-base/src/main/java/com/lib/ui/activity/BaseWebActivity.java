package com.lib.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lib.MyApplication;
import com.lib.base.R;

import com.lib.fastkit.BuildConfig;
import com.lib.fastkit.utils.network.NetUtils;
import com.lib.fastkit.utils.status_bar.QMUI.QMUIDisplayHelper;

import com.lib.fastkit.views.webview_x5.X5WebView;
import com.lib.ui.activity.kit.BaseActivity;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.TbsLog;


public abstract class BaseWebActivity extends BaseActivity {

    private LinearLayout lin_web;
    private ProgressBar p_bar;
    private X5WebView webView;

    private Handler handler = new Handler();

    @Override
    protected void onCreateView() {

        setContentView(getLayoutId());
        lin_web = findViewById(R.id.lin_web);
        p_bar = findViewById(R.id.p_bar);

        //webView = findViewById(R.id.qm_web);
        webView = new X5WebView(this, null);
//        webView = new QDWebView(this);
        lin_web.addView(webView);
        //init(MyApplication.getInstance());
        setWebView();
        onCreateView(webView);
    }


    protected abstract void onCreateView(WebView webView);

    protected abstract int getLayoutId();


    /**
     * 开始加载Url
     */

    public void setWebView() {


        try {

            webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }


            });

            webView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);

                    if (newProgress >= 100) {

                        p_bar.setProgress(100);


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                p_bar.setVisibility(View.GONE);

                            }
                        }, 250);//3秒后执行Runnable中的run方法
                    } else {
                        p_bar.setVisibility(View.VISIBLE);
                        p_bar.setProgress(newProgress);
                    }

                }

            });


            initSeting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSeting() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }


    /**
     * 点击返回按钮如果有浏览记录就返回上一个页面，没有就结束Actiity
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();

        }
        super.onDestroy();
    }


}
