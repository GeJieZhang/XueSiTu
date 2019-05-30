package com.user.activity;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.webview_qm.QDWebView;
import com.lib.fastkit.views.webview_qm.base.QMUIWebViewClient;
import com.lib.ui.activity.BaseWebActivity;
import com.lib.ui.activity.kit.BaseActivity;
import com.user.R;



public class XieyiActivity2 extends BaseActivity {


    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xieyi2;
    }

    @Override
    protected void onCreateView() {
        webView = findViewById(R.id.lin_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setDatabaseEnabled(true);   //开启 database storage API 功能
        webView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
        webView.getSettings().setAppCacheMaxSize(8 * 1024 * 1024);


        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);//开启 Application Caches 功能
        webView.getSettings().setBlockNetworkImage(true);//最后加载图片

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                showLog(newProgress+"");
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        webView.loadUrl("http://www.baidu.com/");


    }


}
