package com.user.fragment;

import com.lib.ui.fragment.BaseWebFragment;
import com.tencent.smtt.sdk.WebView;
import com.user.R;


public class XieYiFragment extends BaseWebFragment {
    @Override
    protected void onCreateView(WebView webView) {
        webView.loadUrl("https://www.baidu.com/");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xieyi;
    }
}
