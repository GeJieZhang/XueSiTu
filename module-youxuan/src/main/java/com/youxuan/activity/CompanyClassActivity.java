package com.youxuan.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.ui.activity.BaseWebActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youxuan.R;

/**
 * 陪伴课
 */

@Route(path = ARouterPathUtils.YouXuan_CompanyClassActivity)
public class CompanyClassActivity extends BaseWebActivity {


    private TextView title;

    @Override
    protected void onCreateView(WebView webView) {


        initWebView(webView);


        initView();


        webView.loadUrl("http://192.168.2.125/index.html");
    }

    private void initWebView(WebView webView) {

        webView.addJavascriptInterface(javaScriptFunction, "CompanyClassActivity");


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


            webView.loadUrl("javascript:hideheader()");
        }
    };

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("")
                .setRightIcon(R.mipmap.nav_share)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击分享");
                    }
                })
                .builder();


        title = navigationBar.getTitleTextView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_class;
    }


    //------------------------------------------------------------------------------------JS交互

    /**
     * JS交互接口
     */
    public interface JavaScriptFunction {
        String requestJava();
    }


    private JavaScriptFunction javaScriptFunction = new JavaScriptFunction() {
        @JavascriptInterface // 加上注解 getUrl() 方法才能被 JS 调用
        public String requestJava() {

            showToast("js调用");
            return "Success";

        }
    };


}
