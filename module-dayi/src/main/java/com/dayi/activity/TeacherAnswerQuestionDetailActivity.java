package com.dayi.activity;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.fragment.QuestionFragment;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentTag;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.zyyoona7.popup.EasyPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 老师解答问题详情
 */
@Route(path = ARouterPathUtils.Dayi_TeacherAnswerQuestionDetailActivity)
public class TeacherAnswerQuestionDetailActivity extends BaseAppActivity {


    @BindView(R2.id.btn_sure)
    Button btnSure;
    @BindView(R2.id.tv_action)
    TextView tvAction;
    @BindView(R2.id.f_question_teacher)
    FrameLayout fQuestionTeacher;


    @Override
    protected void onCreateView() {
        initTitle();


        initFragment();

        initAnswerPopuPopu();
    }

    private void initFragment() {
        QuestionFragment questionFragment = new QuestionFragment();


        FragmentCustomUtils.setFragment(this, R.id.f_question_teacher, questionFragment, FragmentTag.QuestionFragment);

    }

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("问答")
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_qustion_detail;
    }


    @OnClick({R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_sure) {

            showAnswerPopuPopu(view);
        }
    }


    //--------------------------------------------------------------------------------分享
    private EasyPopup answerPopu;

    private LinearLayout lin_video;
    private LinearLayout lin_voice;

    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(this)
                .setContentView(R.layout.popup_answer)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {


                        lin_video = view.findViewById(R.id.lin_video);
                        lin_voice = view.findViewById(R.id.lin_voice);
                        lin_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherWriteAnswerActivity)
                                        .withString("type", "1")
                                        .navigation();


                            }
                        });

                        lin_voice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherWriteAnswerActivity)
                                        .withString("type", "2")
                                        .navigation();
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
