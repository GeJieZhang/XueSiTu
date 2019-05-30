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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lib.MyApplication;
import com.lib.base.R;

import com.lib.fastkit.BuildConfig;
import com.lib.fastkit.utils.network.NetUtils;
import com.lib.fastkit.utils.status_bar.QMUI.QMUIDisplayHelper;
import com.lib.fastkit.views.webview_qm.QDWebView;
import com.lib.fastkit.views.webview_qm.util.QMUIPackageHelper;
import com.lib.ui.activity.kit.BaseActivity;


public abstract class BaseWebActivity extends BaseActivity {

    private LinearLayout lin_web;
    private ProgressBar p_bar;
    private QDWebView webView;

    private Handler handler = new Handler();

    @Override
    protected void onCreateView() {

        setContentView(getLayoutId());
        lin_web = findViewById(R.id.lin_web);
        p_bar = findViewById(R.id.p_bar);

        webView = findViewById(R.id.qm_web);
//        webView = new QDWebView(this);
//        lin_web.addView(webView);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    @SuppressLint("SetJavaScriptEnabled")
    protected void init(Context context) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("GBK");
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setTextZoom(100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        String screen = QMUIDisplayHelper.getScreenWidth(context) + "x" + QMUIDisplayHelper.getScreenHeight(context);
        String userAgent = "QMUIDemo/" + QMUIPackageHelper.getAppVersion(context)
                + " (Android; " + Build.VERSION.SDK_INT
                + "; Screen/" + screen + "; Scale/" + QMUIDisplayHelper.getDensity(context) + ")";
        String agent = webView.getSettings().getUserAgentString();
        if (agent == null || !agent.contains(userAgent)) {
            webView.getSettings().setUserAgentString(agent + " " + userAgent);
        }



    }

}
