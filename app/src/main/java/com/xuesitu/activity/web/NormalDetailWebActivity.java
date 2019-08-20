package com.xuesitu.activity.web;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.web.base.BaseBusinessWebActivity;
import com.lib.utls.share.ShareUtils;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.tencent.smtt.sdk.WebView;
import com.youxuan.R;

/**
 * 陪伴课
 */

@Route(path = ARouterPathUtils.App_NormalDetailWebActivity)
public class NormalDetailWebActivity extends BaseBusinessWebActivity {


    private TextView title;

    private String urlPath;

    private WebView webView;

    private String htmlShare[] = {"curriculum_accompany.html"
            , "curriculum_many.html"
            , "article_content.html"
            , "assistant/assistant_message.html"
            , "videocourse/videocourse_message.html"
    };

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

                        toShare();

                    }
                })
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finish();
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


    private boolean isShareCircle = false;

    private void toShare() {

        String url = webView.getUrl();

        for (String s : htmlShare) {

            if (url.contains(s)) {
                isShareCircle = true;
            }

        }


        if (isShareCircle) {

            ShareUtils.getInstance(this)
                    .setShareParams(ShareUtils.BUSINESS_TYPE_EMPTY, url)
                    .hideCircle(false)
                    .onPenCoustomShareBorad();

        } else {

            ShareUtils.getInstance(this)
                    .setShareParams(ShareUtils.BUSINESS_TYPE_EMPTY, url)
                    .hideCircle(true)
                    .onPenCoustomShareBorad();
        }


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
