package com.lib.utls.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.utls.pay.bean.PayResult;
import com.lib.utls.pay.tool.OrderInfoUtil2_0;


import java.util.Map;

/**
 * Created by chaohx on 2017/7/5.
 */

public class ZhiFuBaoPayUtils {


    private Activity context;
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016092900620920";
    /**
     * 应用私钥
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdhaEO5TbI/ihgkT/1h0u0imQbLMcI6gpYScwEa5nloDWK/qRoXt76HKy4jXght5x0CCoIy99hy/SQXUi6OJywUfE3boqOC6HWiSmN1yxPfNnYa2SS0vw1hCfywx2pRS+DMKnbMFnWQr1vinIERlCmUqSCs1ma0Mcq9VrrDqnXrKEtfaixbK+VgAEprRdoovUBFtDfwGUX4YX0XQPDMYMkXWaJHx4sclyK6ZLbdxZpAPXR7kLV6w2y3xfyzJf9ngK72nIvkbA1spQoPxrM1ifLVeYDxm7MZAFHH53XoQyufDVA1/NK0pzytaTTAS87V1QG9wIIKla3ddJwXGPtfkC/AgMBAAECggEAbqtfyHNJ1lSaUZ467Wmsyp8gTedbvuuPcRAIAmMGsMmqVIkrQRjGV7qlrtPVvvqdNPg3cA6sVq1QmcywOAVmMIqhimxjdADAJ1Yel8TBIOd6tg5djrok8AR1fJ2QzUgKZYVfENwbgelJ5n/CMzajmvcdFaakIZzlEU1oHxUiRp+lSHuCPTgSQJQANCFPmQ2RP42AJefQ3AUXZ48bAtjCEoQffgvkS9t9f+dm8rbN8C1HTFxBkZ/rDwb/fA7XysyTBCxgJJm39mMdBVANjWYqXKJCOeW00ttyiYGVYvNHfKCVpedZ2P0NScJ0vIFPt2XMaVUIgSpN0LDWCF1aCJRyoQKBgQD6sfD2Q0kezH6aAxHpJm7aOUABIVWH/aTWRi+Ge4M7wWiJhc0yYxuISywZu7osnwkeRqH6OBIctu1u3uCORCBF0dVpqBnN35B9qqjJ+0ydqVYBW5G7+gGkxZ0VHIHTtxqtn6erjCNAy7QnLeL0V16oZLY44sUk4GvWcG9oEgyCywKBgQCg2vP8GyC+e+NlugvZFw1fZ+HYEJIZ6A293ZnN3Y4BC/cZLmbKfsVf5RB0mPp9tc0ZsqRuQf1YAPse3S1sWRpLa9uZZHmdnZJmryc8N3DPe4hiSaS5+tYnud1HHwS+bTprgUTrg+8hZubPyBbvKSZ8LejidvoMy17m+gNutseXXQKBgQCWqT3hIgrdoZQsRtAKyHgyWdmnVDss4TihNcj2D2CNFu0WSP1K3Nj9DgBQ7ssyadl3Aofx8qSf8Se8S+G0XijvDO4u80Gewk751whdatNepRf09O0ehV9bgQ+oUluLuy0jTyHbLPs6w7FfImqU4bUXin8grNhCE0V7uFKbvs0aBQKBgGGQzOKCddb0ABaoxRz1DYhr0yGh02xhQ0yBiBPFKafa4RLmATUbtY4Xu2Ztj6oGRpQqte+mmQry9V5gG3gXh4ptrnIY7mc6tx0PakXs/sDqleu/7WldCt5nrx6/Yro6OHASpYIQsU/aQW+QVtDTbeNuWU5bigmOpU6GIOTbeq6FAoGBANSfdrT2ICrTbocZs5LSspZ5REXpPWdzxPvVxBgQUKJGanSV97My0386FGjGSM2ST0LMYR/XhGH2OE72A/3WvR6nGLFuan1mYllSwYvHpTtdsULllLcbXUUgf8oVoAuWULYbh69be4C4MAvRT5IfM992odvNIvqCpDtjdfStLRFw";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            switch (payResult.getResultStatus()) {
                case "9000":
                    Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case "8000":
                    Toast.makeText(context, "正在处理中", Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(context, "订单支付失败", Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(context, "重复请求", Toast.LENGTH_SHORT).show();
                    break;
                case "6001":
                    Toast.makeText(context, "已取消支付", Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(context, "网络连接出错", Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(context, "正在处理中", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 签名发在客户端来做。
     *
     * @param context
     */

    public void toALiPayByClient(final Activity context) {


        this.context = context;

        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(context, "APPID或RSA_PRIVATE未配置");
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2
                        (orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 签名在服务端来做
     *
     * @param context
     * @param orderInfo
     */
    public void toALiPayService(final Activity context, final String orderInfo) {
        this.context = context;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2
                        (orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }


    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("Confirm", null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
