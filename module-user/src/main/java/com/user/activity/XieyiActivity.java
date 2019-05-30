package com.user.activity;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.webview_qm.QDWebView;
import com.lib.fastkit.views.webview_qm.base.QMUIWebViewClient;
import com.lib.fastkit.views.webview_x5.X5WebView;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.activity.BaseWebActivity;
import com.lib.ui.activity.kit.BaseActivity;
import com.user.R;


@Route(path = ARouterPathUtils.User_XieyiActivity)
public class XieyiActivity extends BaseWebActivity {


    @Override
    protected void onCreateView(WebView webView) {


        webView.setWebViewClient(new QMUIWebViewClient(true, true) {

            @Override
            public void onPageStarted(WebView view, String url, @Nullable Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


        });

        webView.loadUrl("https://www.baidu.com/");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xieyi;
    }


}
