package com.youxuan.activity.web;


import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youxuan.R;
import com.youxuan.activity.web.base.BaseBusinessWebActivity;

/**
 * 陪伴课
 */

@Route(path = ARouterPathUtils.YouXuan_NormalDetailWebActivity)
public class NormalDetailWebActivity extends BaseBusinessWebActivity {


    private TextView title;

    private String urlPath;

    private WebView webView;

    @Override
    protected void onCreateView(WebView webView) {

        super.onCreateView(webView);
        this.webView = webView;

        urlPath = getIntent().getStringExtra("urlPath");
        initWebView(webView);
        initView();


        webView.loadUrl(urlPath);
    }

    private void initWebView(WebView webView) {


        webView.setWebViewClient(client);
    }

    private void initView() {
        initTitle();


    }


    private WebViewClient client = new WebViewClient() {


        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);


        }

        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);

            return true;
        }


        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);

            title.setText(webView.getTitle());


        }
    };

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("")
                .setRightIcon(R.mipmap.nav_share)
                .setLeftText("关闭")

                .setLeftTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                })
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (webView.canGoBack()) {
                            webView.goBack();

                        } else {
                            finish();

                        }
                    }
                })
                .builder();


        title = navigationBar.getTitleTextView();

    }


    @Override
    protected int getWebLayoutId() {
        return R.layout.activity_company_class;
    }


}
