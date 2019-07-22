package com.dayi.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.QuestionDetail;
import com.dayi.fragment.child.QuestionFragment;
import com.dayi.fragment.child.TeacherListFragment;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.FragmentTag;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 学生问题详情
 */
@Route(path = ARouterPathUtils.Dayi_StudentQuestionDetailActivity)
public class StudentQuestionDetailActivity extends BaseAppActivity {
    @BindView(R2.id.f_question_student)
    FrameLayout fQuestionStudent;
    @BindView(R2.id.f_teacher_list)
    FrameLayout fTeacherList;

    @Autowired(name = "questionId")
    String questionId;


    @Override
    protected void onCreateView() {

        ARouter.getInstance().inject(this);
        initTitle();
        initQuestionFragment();
        initTeacherListFragment();


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

        if (questionFragment!=null){
            questionFragment.updateData(question);
        }


    }

    private void initTeacherListFragment() {

        TeacherListFragment teacherListFragment = new TeacherListFragment();
        FragmentCustomUtils.setFragment(this, R.id.f_teacher_list, teacherListFragment, FragmentTag.TeacherListFragment);
    }

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("我的提问")
                .builder();


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_question_detail;
    }


    /**
     * 初始化问题UI
     */
    private QuestionFragment questionFragment;

    private void initQuestionFragment() {
        questionFragment = new QuestionFragment();


        FragmentCustomUtils.setFragment(this, R.id.f_question_student, questionFragment, FragmentTag.QuestionFragment);

    }


}
