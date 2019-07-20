package com.dayi.utils.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.lib.app.ARouterPathUtils;
import com.zyyoona7.popup.EasyPopup;

public class EvaluationTeacherPoupUtils {

    private Activity activity;

    public EvaluationTeacherPoupUtils(Activity ac) {


        this.activity = ac;


    }


    private EasyPopup answerPopu;



    private LinearLayout lin_parent;

    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_answer)

                .setWidth(WindowManager.LayoutParams.FILL_PARENT)

                .setHeight(WindowManager.LayoutParams.FILL_PARENT)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {



                        lin_parent = view.findViewById(R.id.lin_parent);
                        lin_parent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                answerPopu.dismiss();
                            }
                        });


                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    public void showAnswerPopuPopu(View view) {

        answerPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


















}
