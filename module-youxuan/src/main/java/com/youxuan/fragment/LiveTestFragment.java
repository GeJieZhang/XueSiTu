package com.youxuan.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.EnvUtils;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.pay.ZhiFuBaoPayUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youxuan.R;
import com.youxuan.R2;

import butterknife.BindView;
import butterknife.OnClick;


public class LiveTestFragment extends BaseAppFragment {


    @BindView(R2.id.btn_token1)
    Button btnToken1;
    @BindView(R2.id.btn_token2)
    Button btnToken2;
    @BindView(R2.id.btn_token3)
    Button btnToken3;
    @BindView(R2.id.btn_token4)
    Button btnToken4;

    @BindView(R2.id.btn_token5)
    Button btnToken5;
    @BindView(R2.id.btn_token6)
    Button btnoken6;


    String roomToken1 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:5ugP-WbuM66BYO9I24HVMhhlYeQ=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiIxMzU0MDM1NDU5NyIsImV4cGlyZUF0IjoxNTYyNDY5NDMwLCJwZXJtaXNzaW9uIjoidXNlciJ9";
    String roomToken2 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:9YrKiLh8Cb6SOLIyYQpTP4pqHAI=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiIxMzU0MDM1NDU5NiIsImV4cGlyZUF0IjoxNTYyNDY5NDMwLCJwZXJtaXNzaW9uIjoidXNlciJ9";
    String roomToken3 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:xf1jHmvqJIHP5apXYOFtKI7G4Pw=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiIxMzU0MDM1NDU5NSIsImV4cGlyZUF0IjoxNTYyNDY5NDMwLCJwZXJtaXNzaW9uIjoidXNlciJ9";
    String orderInfo = "alipay_sdk=alipay-sdk-java-4.3.0.ALL&app_id=2016092900620920&biz_content=%7B%22body%22%3A%22%E5%AD%A6%E6%80%9D%E5%85%94%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22pay20190819163851629352698103%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%AD%A6%E6%80%9D%E5%85%94%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.xuesituedu.cn%3A8081%2Falipay_notify&sign=GeXiPWvZe3gY6cxPkmNriJB9J1XR0Tls0%2F8DtlqkryjQo5mnrrSFbxHQBodi0ReK%2B6o%2FIgb6dpQXZeKw6DqY%2BUQe3wU66r894DYj0hKnHEceNhjngWxbdGZvRh5SlfV4STuypJ8iGlVKtUvkMBYbOL6WHRHcQ2xCrJXSJ9nciHhIx07y7ziKDGPuRFG6c%2BdHT1yEy1%2FqdK9wD%2FMwFv0x7UEo2VB8WiGeUfhGxadeU%2BBHKde8xx6XNl7PS%2FyMA40bdPZ9lfVPaL4f%2FS6y5w3T6vmkoSgy%2FlsyFo1jn9vMFF0hHpXWXvVyEqe%2B1CnfiCeS0iigZI9M%2Bi1D0O%2Fah97efw%3D%3D&sign_type=RSA2&timestamp=2019-08-19+16%3A38%3A51&version=1.0";


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_test;
    }


    @OnClick({R2.id.btn_token1, R2.id.btn_token2, R2.id.btn_token3, R2.id.btn_token4, R2.id.btn_token5, R2.id.btn_token6})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_token1) {


            PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {

                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                            .withString("roomToken", roomToken1)
                            .withString("teacherPhone", "13540354597")
                            .withString("roomName", "room5123")
                            .withString("userPhone", "13540354597")

                            .navigation();

                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });
        } else if (i == R.id.btn_token2) {

            PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {

                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                            .withString("roomToken", roomToken2)
                            .withString("teacherPhone", "13540354597")
                            .withString("roomName", "room5123")
                            .withString("userPhone", "13540354596")
                            .navigation();

                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });

        } else if (i == R.id.btn_token3) {
            PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {

                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                            .withString("roomToken", roomToken3)
                            .withString("teacherPhone", "13540354597")
                            .withString("roomName", "room5123")
                            .withString("userPhone", "13540354595")
                            .navigation();

                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });
        } else if (i == R.id.btn_token4) {
            ARouter.getInstance()
                    .build(ARouterPathUtils.Live_BoardTestActivity)

                    .navigation();
        } else if (i == R.id.btn_token5) {

            PermissionUtil.getInstance(getActivity()).externalZhiFu(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {
                    new ZhiFuBaoPayUtils().toALiPayService(getActivity(), orderInfo);
                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });


        } else if (i == R.id.btn_token6) {
            new ShareAction(getActivity()).withText("hello").setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                    .setCallback(umShareListener).open();
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
}
