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

    }

    private void initView() {
        initTitle();

    }


    private TextView colse;

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
        colse = (TextView) navigationBar.getView(R.id.left_text);

        colse.setVisibility(View.GONE);
    }


    @Override
    protected int getWebLayoutId() {
        return R.layout.activity_company_class;
    }

    @Override
    protected void myOnPageStarted(WebView webView, String s, Bitmap bitmap) {

    }

    @Override
    protected void myShouldOverrideUrlLoading(WebView webView, String url) {

    }

    @Override
    protected void myOnPageFinished(WebView webView, String s) {
        title.setText(webView.getTitle());


        if (webView.canGoBack()) {
            colse.setVisibility(View.VISIBLE);
        }

    }


}
