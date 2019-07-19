package com.dayi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.fragment.QuestionFragment;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentTag;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 学生问题详情
 */
@Route(path = ARouterPathUtils.Dayi_StudentQuestionDetailActivity)
public class StudentQuestionDetailActivity extends BaseAppActivity {
    @BindView(R2.id.f_question_student)
    FrameLayout fQuestionStudent;
    @BindView(R2.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreateView() {
        initTitle();
        initFragment();

        initView();


    }

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("我的提问")
                .builder();


    }

    private HomeAdapter homeAdapter;

    private List<String> list = new ArrayList<>();

    private void initView() {
        list.add("");
        list.add("");
        list.add("");
        homeAdapter = new HomeAdapter(this, list);


        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rv.setAdapter(homeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_question_detail;
    }

    private void initFragment() {
        QuestionFragment questionFragment = new QuestionFragment();


        FragmentCustomUtils.setFragment(this, R.id.f_question_student, questionFragment, FragmentTag.QuestionFragment);

    }


    private class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_student_question_detail;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

        }
    }

}
