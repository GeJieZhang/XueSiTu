package com.lib.utls.pop;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.base.R;
import com.lib.bean.PushDetailBean;
import com.lib.bean.RobQuestionBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class LiveCheckMoneyPopupUtils {


    private Context context;


    public LiveCheckMoneyPopupUtils(Context context) {

        this.context = context;
        mainHandler = new Handler();
        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(context)
                .setContentView(R.layout.popup_live_money)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setHeight(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {

                        initView(view);


                    }

                })
                .setFocusAndOutsideEnable(true)
                .apply();


    }


    private ImageView iv_delete;
    private TextView tv_time;
    private TextView tv_money;
    private TextView tv_recharge;
    private TextView tv_content;
    private TextView tv_rule;
    private Button btn_cancel;
    private Button btn_sure;

    private void initView(View view) {
        iv_delete = view.findViewById(R.id.iv_delete);
        tv_time = view.findViewById(R.id.tv_time);
        tv_money = view.findViewById(R.id.tv_money);
        tv_recharge = view.findViewById(R.id.tv_recharge);
        tv_content = view.findViewById(R.id.tv_content);
        tv_rule = view.findViewById(R.id.tv_rule);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_sure = view.findViewById(R.id.btn_sure);

        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
            }
        });

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancle();
                }

                dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancle();
                }

                dismiss();
            }
        });


        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSure();
                }

                dismiss();

            }
        });


    }


    private void showToast(String a) {
        Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
    }


    public void dismiss() {


        if (popu != null) {


            popu.dismiss();
            stopTimer();
        }

    }


    public void showAnswerPopuPopu(View view) {


        popu.showAtLocation(view, Gravity.CENTER, 0, 0);
        startTimer();
    }


    //-----------------------------------------------------------------------------记时器

    /**
     * 初始化计时器用来更新倒计时
     */
    private Handler mainHandler;

    private long recordTotalTime = 30 * 1000;

    private void startTimer() {
        mainHandler.postDelayed(runnable, 1000);
    }

    private void stopTimer() {
        mainHandler.removeCallbacks(runnable);

        recordTotalTime = 30 * 1000;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recordTotalTime -= 1000;
            updateTimerUI();

            mainHandler.postDelayed(runnable, 1000);
        }
    };

    private void updateTimerUI() {


        if (recordTotalTime <= 0) {
            dismiss();


        } else {
            String string = TimeUtils.converLongTimeToStr(recordTotalTime);
            tv_time.setText(string + "s");
            LogUtil.e(string);
        }


    }


    private void setText(String money, String content, String rule) {
        tv_money.setText(money);
        tv_content.setText(content);
        tv_rule.setText(rule);
    }


    //==============================================================================================
    //====================================================================================接口回调
    //==============================================================================================


    public interface LiveCheckMoneyPopupUtilsListener {
        void onCancle();

        void onSure();
    }


    private LiveCheckMoneyPopupUtilsListener listener;


    public void setLiveCheckMoneyPopupUtilsListener(LiveCheckMoneyPopupUtilsListener liveCheckMoneyPopupUtilsListener) {
        this.listener = liveCheckMoneyPopupUtilsListener;
    }


}
