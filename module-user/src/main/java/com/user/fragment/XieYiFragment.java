package com.user.fragment;

import com.lib.ui.fragment.BaseWebFragment;
import com.tencent.smtt.sdk.WebView;
import com.user.R;


public class XieYiFragment extends BaseWebFragment {
    @Override
    protected void onCreateView(WebView webView) {
        webView.loadUrl("http://192.168.2.113/agreement.html");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xieyi;
    }
}
