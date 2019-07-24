package com.dayi.activity;


import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.BaseHttpBean;
import com.dayi.bean.QuestionDetail;
import com.dayi.fragment.child.QuestionFragment;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.FragmentTag;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.zyyoona7.popup.EasyPopup;

import butterknife.BindView;
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

    @BindView(R2.id.tv_tubi)
    TextView tvTubi;

    @BindView(R2.id.f_question_teacher)
    FrameLayout fQuestionTeacher;
    @Autowired(name = "questionId")
    String questionId;

    @Override
    protected void onCreateView() {
        ARouter.getInstance().inject(this);
        initTitle();


        initFragment();

        initAnswerPopuPopu();


        initData();


    }


    private void initData() {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_DETAILE")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("question_id", questionId)
                .execute(new HttpDialogCallBack<QuestionDetail>() {
                    @Override
                    public void onSuccess(QuestionDetail result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            insertQuestionData(result.getObj().getQuestion());

                        }

                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }

    private void insertQuestionData(QuestionDetail.ObjBean.QuestionBean question) {
        tvTubi.setText(question.getCurrent_value() + "兔币");
        if (questionFragment != null) {
            questionFragment.updateData(question);
        }


    }

    private QuestionFragment questionFragment;

    private void initFragment() {

        questionFragment = new QuestionFragment();

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

    private LinearLayout lin_parent;

    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(this)
                .setContentView(R.layout.popup_answer)

                .setWidth(WindowManager.LayoutParams.FILL_PARENT)

                .setHeight(WindowManager.LayoutParams.FILL_PARENT)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {


                        lin_video = view.findViewById(R.id.lin_video);
                        lin_voice = view.findViewById(R.id.lin_voice);
                        lin_parent = view.findViewById(R.id.lin_parent);
                        lin_parent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                answerPopu.dismiss();
                            }
                        });
                        lin_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                teacherViesAnswer(1);


                            }
                        });

                        lin_voice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                teacherViesAnswer(2);

                            }
                        });

                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }


    /**
     * 老师抢答
     */
    private void teacherViesAnswer(final int type) {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_RESPONDER_REPLY")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("question_id", questionId)
                .execute(new HttpDialogCallBack<BaseHttpBean>() {
                    @Override
                    public void onSuccess(BaseHttpBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            if (type == 1) {
                                //视频回答

                                ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherWriteAnswerActivity)
                                        .withString("type", "1")

                                        .withString("questionId", questionId)
                                        .navigation();

                                answerPopu.dismiss();


                            } else {
                                //图片文字回答
                                ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherWriteAnswerActivity)
                                        .withString("type", "2")
                                        .withString("questionId", questionId)
                                        .navigation();
                                answerPopu.dismiss();

                            }

                        }

                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    public void showAnswerPopuPopu(View view) {

        answerPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


}
