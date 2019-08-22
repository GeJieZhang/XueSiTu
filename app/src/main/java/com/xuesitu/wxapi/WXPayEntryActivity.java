package com.xuesitu.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付回调页面
 * 删除后会影响微信支付功能
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx2747c1e8b040c4d1");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onResp(BaseResp resp) {

        LogUtil.e("onResp:" + new Gson().toJson(resp));

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {


                ARouter.getInstance().build(ARouterPathUtils.User_PayStateActivity)
                        .withInt("state", 1)
                        .navigation();
            } else {

                ARouter.getInstance().build(ARouterPathUtils.User_PayStateActivity)
                        .withInt("state", 0)
                        .navigation();
            }
            finish();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.e("onReq:" + new Gson().toJson(baseReq));
    }
}