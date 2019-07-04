package com.youxuan.activity.web.base;

import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.ui.activity.BaseWebActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public abstract class BaseBusinessWebActivity extends BaseWebActivity {

    //修改支付密码
    public static final int SET_PASSWORD = 1;


    public String token = "";

    private WebView webView;

    @Override
    protected void onCreateView(WebView webView) {

        webView.addJavascriptInterface(javaScriptFunction, "AndroidInterface");
        webView.setWebViewClient(client);

        this.webView = webView;


    }

    @Override
    protected int getLayoutId() {
        return getWebLayoutId();
    }


    protected abstract int getWebLayoutId();


    protected abstract void myOnPageStarted(WebView webView, String s, Bitmap bitmap);

    protected abstract void myShouldOverrideUrlLoading(WebView webView, String url);

    protected abstract void myOnPageFinished(WebView webView, String s);

    private WebViewClient client = new WebViewClient() {


        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            myOnPageStarted(webView, s, bitmap);

        }

        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);

            myShouldOverrideUrlLoading(webView, url);

            return true;
        }


        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);


            webView.loadUrl("javascript:hideheader()");

            myOnPageFinished(webView, s);

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
        void appPushLiveRoom(String roomToken, String teacherPhone, String userToken, String roomName);


        //隐藏当前WebView

        void appHiddenController();

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
                    .withInt("NOW_TYPE", SET_PASSWORD)
                    .navigation();
        }

        @JavascriptInterface
        public void appPushLiveRoom(String roomToken, String teacherPhone, String userToken, String roomName) {


            if (checkToken(userToken)) return;


            toRoom(roomToken, teacherPhone, roomName);

        }

        @JavascriptInterface
        public void appHiddenController() {

            finish();

        }


    };

    private void toRoom(final String roomToken, final String teacherPhone, final String roomName) {
        PermissionUtil.getInstance(BaseBusinessWebActivity.this).externalZhiBo(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {

                ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                        .withString("roomToken", roomToken)
                        .withString("teacherPhone", teacherPhone)
                        .withString("roomName", roomName)
                        .navigation();
            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });
    }

    private boolean checkToken(String userToken) {
        if (!token.equals(userToken)) {

            ARouter.getInstance().build(ARouterPathUtils.User_LoginActivity).navigation();

            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (webView != null) {
            token = SharedPreferenceManager.getInstance(getApplicationContext()).getUserCache().getUserToken();
            webView.loadUrl("javascript:appOrderRefresh(" + token + ")");

        }

    }
}
