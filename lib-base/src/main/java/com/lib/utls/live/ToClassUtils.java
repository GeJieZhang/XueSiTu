package com.lib.utls.live;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.bean.ToLiveBeanBase;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.utls.pop.LiveCheckMoneyPopupUtils;

/**
 * 对进入直播的流程进行一次封装方便多次调用
 */
public class ToClassUtils {


    private Activity activity;

    public ToClassUtils(Activity activity) {

        this.activity = activity;


    }

    private static ToClassUtils instance;

    public static synchronized ToClassUtils getInstance(Activity activity) {

        if (instance == null) {
            instance = new ToClassUtils(activity);
        }
        return instance;
    }


    /**
     * 去上课
     *
     * @param course_type
     * @param course_id
     */
    public void goToClass(final String course_type, final String course_id) {
        PermissionUtil.getInstance(activity).externalZhiBo(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {


                //type:0晚陪课，1一对一，2班级课，3体验课
                //支付了去上课
                HttpUtils.with(activity)
                        .addParam("requestType", "TO_CLASS")
                        .addParam("course_type", course_type)
                        .addParam("course_id", course_id)
                        .addParam("token", SharedPreferenceManager.getInstance(activity).getUserCache().getUserToken())
                        .execute(new HttpDialogCallBack<ToLiveBeanBase>() {
                            @Override
                            public void onSuccess(final ToLiveBeanBase result) {

                                if (result.getCode() == CodeUtil.CODE_200) {


                                    String identity = SharedPreferenceManager.getInstance(activity).getUserCache().getUserIdentity();

                                    if (identity.equals("1")) {
                                        toClass(result);
                                    } else {


                                        if (course_type.equals("0")) {
                                            //只有晚陪课有
                                            toClassPop(result);
                                        } else {
                                            toClass(result);
                                        }

                                    }


                                } else {
                                    showToast(result.getMsg());
                                }

                            }

                            @Override
                            public void onError(String e) {

                            }


                        });


            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });
    }

    private void toClassPop(final ToLiveBeanBase result) {
        final LiveCheckMoneyPopupUtils liveCheckMoneyPopupUtils = new LiveCheckMoneyPopupUtils(activity);
        liveCheckMoneyPopupUtils.setLiveCheckMoneyPopupUtilsListener(new LiveCheckMoneyPopupUtils.LiveCheckMoneyPopupUtilsListener() {
            @Override
            public void onCancle() {
                liveCheckMoneyPopupUtils.dismiss();
            }

            @Override
            public void onSure() {
                toClass(result);
            }
        });

        String rule = "计费方式:每" + result.getObj().getInfo().getLive_billing_time()
                + "分钟消费" + result.getObj().getInfo().getLive_billing() + "兔币";

        String money = "当前兔币:" + result.getObj().getInfo().getAccount();

        liveCheckMoneyPopupUtils.showAnswerPopuPopu(activity.getWindow().getDecorView(), rule, money);
    }

    private void toClass(ToLiveBeanBase result) {
        String roomToken = result.getObj().getRoomtoken();
        String teacherPhone = result.getObj().getPhoen();
        ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                .withString("roomToken", roomToken)
                .withString("teacherPhone", teacherPhone)
                .withString("roomName", result.getObj().getRoomname())
                .withString("userPhone", SharedPreferenceManager.getInstance(activity).getUserCache().getUserPhone())
                .withString("uuid", result.getObj().getUuid())
                .withString("whitetoken", result.getObj().getWhitetoken())

                .navigation();
    }

    /**
     * Toast
     *
     * @param text
     */

    private Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
            }
            mToast.setText(text);
            mToast.show();
        }
    }

}
