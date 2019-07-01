package com.youxuan.activity.web.base;

import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.ui.activity.BaseWebActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public abstract class BaseBusinessWebActivity extends BaseWebActivity {

    //修改支付密码
    public static final int CHANGE_PASSWORD = 2;


    public String token = "";

    @Override
    protected void onCreateView(WebView webView) {
        token = SharedPreferenceManager.getInstance(getApplicationContext()).getUserCache().getUserToken();
        webView.addJavascriptInterface(javaScriptFunction, "AndroidInterface");
        webView.setWebViewClient(client);


    }

    @Override
    protected int getLayoutId() {
        return getWebLayoutId();
    }


    protected abstract int getWebLayoutId();

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


            webView.loadUrl("javascript:hideheader()");
        }
    };


    //------------------------------------------------------------------------------------JS交互

    /**
     * JS交互接口
     */
    public interface JavaScriptFunction {
        //登录
        void appPushLogin();

        //获取Token
        String appGetToken();

        //充值
        void appPushRecharge(String userToken);

        //修改支付密码
        void appPushSetPayPass(String userToken);

        //进入直播间
        void appPushLiveRoom(String liveToken, String roomId, String teacherPhone, String userToken);
    }


    private JavaScriptFunction javaScriptFunction = new JavaScriptFunction() {
        @JavascriptInterface // 加上注解 getUrl() 方法才能被 JS 调用
        public void appPushLogin() {
            ARouter.getInstance().build(ARouterPathUtils.User_LoginActivity).navigation();

        }

        @JavascriptInterface
        public String appGetToken() {




            return token;
        }

        @JavascriptInterface
        public void appPushRecharge(String userToken) {

            if (checkToken(userToken)) return;

            ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();


        }

        @JavascriptInterface
        public void appPushSetPayPass(String userToken) {
            if (checkToken(userToken)) return;

            ARouter.getInstance().build(ARouterPathUtils.User_SetZhiFuActivity)
                    .withInt("NOW_TYPE", CHANGE_PASSWORD)
                    .navigation();
        }

        @JavascriptInterface
        public void appPushLiveRoom(String liveToken, String roomId, String teacherPhone, String userToken) {
            if (checkToken(userToken)) return;
        }
    };

    private boolean checkToken(String userToken) {
        if (!token.equals(userToken)) {

            ARouter.getInstance().build(ARouterPathUtils.User_LoginActivity).navigation();

            return true;
        }
        return false;
    }



}
