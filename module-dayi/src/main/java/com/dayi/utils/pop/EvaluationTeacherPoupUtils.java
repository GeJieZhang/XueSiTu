package com.dayi.utils.pop;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.zyyoona7.popup.EasyPopup;

public class EvaluationTeacherPoupUtils {

    private Activity activity;

    public EvaluationTeacherPoupUtils(Activity ac) {


        this.activity = ac;
        initAnswerPopuPopu();

    }


    private EasyPopup answerPopu;


    private LinearLayout lin_parent;


    private TextView tv_complaints;
    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_teacher_evaluation)

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

    public void showAnswerPopuPopu(View view) {

        answerPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private LinearLayout lin_true;
    private TextView tv_true;


    private LinearLayout lin_false;
    private TextView tv_false;


    private LinearLayout lin_like;

    private TextView tv_like;
    private LinearLayout lin_dislike;
    private TextView tv_dislike;


    private Button btn_sure;



    private String correct = "";
    private String praise = "";

    private CardView card;


    private void initView(View view) {
        lin_parent = view.findViewById(R.id.lin_parent);
        lin_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPopu.dismiss();
            }
        });
        card = view.findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("click");
            }
        });

        lin_true = view.findViewById(R.id.lin_true);

        lin_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lin_true.setBackgroundResource(R.drawable.bg_circle_b);
                tv_true.setTextColor(activity.getResources().getColor(R.color.white));
                lin_false.setBackgroundResource(R.drawable.bg_circle_g_w);
                tv_false.setTextColor(activity.getResources().getColor(R.color.base_title));
                correct = "0";
            }
        });

        tv_true = view.findViewById(R.id.tv_true);
        lin_false = view.findViewById(R.id.lin_false);

        lin_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_true.setBackgroundResource(R.drawable.bg_circle_g_w);
                tv_true.setTextColor(activity.getResources().getColor(R.color.base_title));
                lin_false.setBackgroundResource(R.drawable.bg_circle_g);
                tv_false.setTextColor(activity.getResources().getColor(R.color.white));
                correct = "1";
            }
        });


        lin_like = view.findViewById(R.id.lin_like);
        tv_like = view.findViewById(R.id.tv_like);
        lin_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_like.setBackgroundResource(R.drawable.bg_circle_b);
                tv_like.setTextColor(activity.getResources().getColor(R.color.white));
                lin_dislike.setBackgroundResource(R.drawable.bg_circle_g_w);
                tv_dislike.setTextColor(activity.getResources().getColor(R.color.base_title));
                praise = "0";
            }
        });
        tv_dislike = view.findViewById(R.id.tv_dislike);

        lin_dislike = view.findViewById(R.id.lin_dislike);

        lin_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_like.setBackgroundResource(R.drawable.bg_circle_g_w);
                tv_like.setTextColor(activity.getResources().getColor(R.color.base_title));
                lin_dislike.setBackgroundResource(R.drawable.bg_circle_g);
                tv_dislike.setTextColor(activity.getResources().getColor(R.color.white));
                praise = "1";
            }
        });
        tv_dislike = view.findViewById(R.id.tv_dislike);


        tv_false = view.findViewById(R.id.tv_false);


        btn_sure = view.findViewById(R.id.btn_sure);

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correct.equals("")) {
                    Toast.makeText(activity, "老师回答的是否正确！", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (praise.equals("")) {
                    Toast.makeText(activity, "是否喜欢老师的回答！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listener != null) {
                    listener.onSure(correct, praise);
                }

            }
        });


        tv_complaints = view.findViewById(R.id.tv_complaints);

        tv_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onComplaintsTeacher();
                }
            }
        });


    }


    private EvaluationTeacherPoupUtilsListener listener;

    public void setEvaluationTeacherPoupUtilsListener(EvaluationTeacherPoupUtilsListener evaluationTeacherPoupUtilsListener) {
        this.listener = evaluationTeacherPoupUtilsListener;
    }

    public interface EvaluationTeacherPoupUtilsListener {

        void onSure(String correct, String praise);

        void onComplaintsTeacher();

    }


}
