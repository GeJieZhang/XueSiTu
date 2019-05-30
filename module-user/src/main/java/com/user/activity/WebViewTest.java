package com.user.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.webview_qm.QDWebView;
import com.lib.fastkit.views.webview_qm.base.QMUIWebViewClient;
import com.lib.fastkit.views.webview_x5.X5WebView;
import com.user.R;

@Route(path = ARouterPathUtils.User_XieyiActivity2)
public class WebViewTest extends Activity {

    private X5WebView web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_test);
        web = findViewById(R.id.web);

//        web.setWebViewClient(new QMUIWebViewClient(true, true) {
//            @Override
//            public void onPageStarted(WebView view, String url, @Nullable Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//        });


        web.loadUrl("https://www.baidu.com/");

    }
}
