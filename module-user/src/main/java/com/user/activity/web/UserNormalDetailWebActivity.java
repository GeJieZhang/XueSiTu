package com.user.activity.web;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.web.base.BaseBusinessWebActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.tencent.smtt.sdk.WebView;
import com.user.R;


/**
 * 陪伴课
 */

@Route(path = ARouterPathUtils.User_UserNormalDetailWebActivity)
public class UserNormalDetailWebActivity extends BaseBusinessWebActivity {


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
        return R.layout.activity_user_web_detail;
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
