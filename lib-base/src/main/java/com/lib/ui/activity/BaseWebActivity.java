package com.lib.ui.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.base.R;

import com.lib.fastkit.views.webview_x5.X5WebView;
import com.lib.ui.activity.kit.BaseActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public abstract class BaseWebActivity extends BaseActivity {

    private LinearLayout lin_web;
    private ProgressBar p_bar;
    private X5WebView webView;

    private Handler handler = new Handler();

    @Override
    protected void onCreateView() {


        lin_web = findViewById(R.id.lin_web);
        p_bar = findViewById(R.id.p_bar);
        webView = new X5WebView(this);
        lin_web.addView(webView);
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


    @Override
    public void onBackPressed() {


        if (webView.canGoBack()) {
            webView.goBack();

        } else {
            finish();

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
