package com.lib.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lib.base.R;
import com.lib.fastkit.views.webview_x5.X5WebView;
import com.lib.ui.fragment.kit.BaseFragment;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public abstract class BaseWebFragment extends BaseFragment {

    private LinearLayout lin_web;
    private ProgressBar p_bar;
    private X5WebView webView;

    private Handler handler = new Handler();

    private Handler handlerBack = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    if (webView != null) {
                        webView.goBack();
                    }

                }
                break;
            }
        }
    };

    private View root;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        root = getRootView();
        lin_web = root.findViewById(R.id.lin_web);
        p_bar = root.findViewById(R.id.p_bar);
        webView = new X5WebView(getContext());
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

            //处理WebView点击返回事件
            webView.setOnKeyListener(new View.OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) { //只处理一次
                            handlerBack.sendEmptyMessage(1);
                        }
                        return true;
                    }
                    return false;
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {


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
        super.onDestroyView();
    }


}
