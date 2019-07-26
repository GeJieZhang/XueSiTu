package com.dayi.utils.pop;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dayi.R;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.zyyoona7.popup.EasyPopup;

public class PayPopupUtils {


    private Activity activity;

    public PayPopupUtils(Activity ac) {

        this.activity = ac;

        initPopuPopu();
    }

    private EasyPopup popu;

    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_pay)

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


    private Button btn_sure, btn_cancel;

    private LinearLayout lin_parent;

    private CardView card;

    private TextView tv_money, tv_recharge, tv_cut_money;


    private void initView(View view) {

        tv_money = view.findViewById(R.id.tv_money);


        tv_recharge = view.findViewById(R.id.tv_recharge);
        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener!=null){
                    listener.onRechargeClick();
                }

            }
        });

        tv_cut_money = view.findViewById(R.id.tv_recharge);


        lin_parent = view.findViewById(R.id.lin_parent);
        card = view.findViewById(R.id.card);

        btn_sure = view.findViewById(R.id.btn_sure);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lin_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();
            }
        });


        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (listener != null) {
                    listener.onSure();
                }

                popu.dismiss();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();


            }
        });

    }

    public void showAnswerPopuPopu(View view) {

        popu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private PayPopupUtilsListener listener;

    public void setPayPopupUtilsListener(PayPopupUtilsListener payPopupUtilsListener) {

        this.listener = payPopupUtilsListener;

    }

    public void dismiss() {
        popu.dismiss();
    }

    public interface PayPopupUtilsListener {

        void onSure();

        void onRechargeClick();
    }

    public void setPopupValue(String money, String haveMoney) {
        tv_cut_money.setText(money);

        tv_money.setText(haveMoney);

        int moneyValue = Integer.valueOf(haveMoney);

        if (moneyValue <= 0) {
            tv_money.setTextColor(activity.getResources().getColor(R.color.base_money));

            tv_recharge.setVisibility(View.VISIBLE);
        } else {
            tv_money.setTextColor(activity.getResources().getColor(R.color.base_blue));
            tv_recharge.setVisibility(View.GONE);
        }
    }


}
