package com.lib.utls.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.base.R;
import com.lib.bean.PushBean;
import com.lib.bean.PushDetailBean;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.ui.adapter.BaseAdapter;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class PushPopupUtils {


    private Context context;
    private List<PushDetailBean> list = new ArrayList<>();

    private int positon = 0;

    public PushPopupUtils(Context context) {

        this.context = context;

        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(context)
                .setContentView(R.layout.popup_push)
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


    private ImageView iv_left, iv_right;

    private TextView tv_subject, tv_money;

    private Button btn_sure, btn_cancel;

    private void initView(View view) {
        iv_left = view.findViewById(R.id.iv_left);
        iv_right = view.findViewById(R.id.iv_right);
        tv_subject = view.findViewById(R.id.tv_subject);
        tv_money = view.findViewById(R.id.tv_money);
        btn_sure = view.findViewById(R.id.btn_sure);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (positon > 0) {

                    positon--;
                    showData(positon);


                } else {


                    showToast("没有更多了");
                }

            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (positon < list.size() - 1) {

                    positon++;
                    showData(positon);


                } else {


                    showToast("没有更多了");
                }

            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.size() > 0) {

                    ARouter.getInstance().build(ARouterPathUtils.Dayi_StudentQuestionDetailActivity, GroupUtils.NEED_LOGIN)
                            .withString("questionId", list.get(positon).getObj().getQuestion_id() + "")
                            .navigation();

                    dismiss();
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    private void showToast(String a) {
        Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
    }

    private void showData(int positon) {

        if (list.size() > 0) {
            tv_subject.setText(list.get(positon).getObj().getGrade_name());
            tv_money.setText(list.get(positon).getObj().getExpected_value() + "兔币");


            if (positon == 0 && list.size() == 1) {

                iv_left.setVisibility(View.GONE);
                iv_right.setVisibility(View.GONE);
            } else {
                iv_left.setVisibility(View.VISIBLE);
                iv_right.setVisibility(View.VISIBLE);


                if (positon == 0) {
                    iv_left.setVisibility(View.GONE);
                }


                if (positon == list.size() - 1) {
                    iv_right.setVisibility(View.GONE);
                }
            }


//            if (positon >= 1 && list.size() > 1) {
//                iv_left.setVisibility(View.GONE);
//                iv_right.setVisibility(View.VISIBLE);
//            }
//            if (positon >= 1 && positon == list.size() - 1) {
//                iv_left.setVisibility(View.VISIBLE);
//                iv_right.setVisibility(View.GONE);
//            }


        }


    }


    public void dismiss() {
        popu.dismiss();
        list.clear();
    }


    public void showAnswerPopuPopu(View view) {


        popu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public void updateData(PushDetailBean pushDetailBean) {

        list.add(pushDetailBean);
        showData(positon);
    }
}
