package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.user.R;
import com.user.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_BookChooseActivity)
public class BookChooseActivity extends BaseAppActivity {


    @BindView(R2.id.rv_grade)
    RecyclerView rvGrade;
    @BindView(R2.id.rv_discipline)
    RecyclerView rvDiscipline;


    private GradeAdapter gradeAdapter;

    private DisciplineAdapter disciplineAdapter;

    private String[] grade = {
            "一年级", "二年级", "三年级",
            "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三"};


    private String[] discipline = {
            "语文", "数学", "英语",
            "物理", "化学", "生物",
            "政治", "历史", "地理",
    };


    @Override
    protected void onCreateView() {

        setContentView(R.layout.activity_book_choose);
        ButterKnife.bind(this);


        initData();


        initView();


    }

    private void initView() {
        gradeAdapter = new GradeAdapter(this, mDataGrade);
        rvGrade.setLayoutManager(new GridLayoutManager(this, 3));
        rvGrade.setAdapter(gradeAdapter);


        disciplineAdapter = new DisciplineAdapter(this, mDataDiscipline);
        rvDiscipline.setLayoutManager(new GridLayoutManager(this, 3));
        rvDiscipline.setAdapter(disciplineAdapter);

    }

    private void initData() {
        mDataGrade.clear();

        for (String s : grade) {

            mDataGrade.add(s);

        }

        mDataDiscipline.clear();

        for (String s : discipline) {

            mDataDiscipline.add(s);

        }
    }


    private List<String> mDataGrade = new ArrayList<>();

    private List<String> mDataDiscipline = new ArrayList<>();

    public class GradeAdapter extends BaseAdapter<String> {

        public GradeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_book;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

            holder.setText(R.id.btn_text, mData.get(position));

        }
    }

    public class DisciplineAdapter extends BaseAdapter<String> {

        public DisciplineAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_book;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

            holder.setText(R.id.btn_text, mData.get(position));

        }
    }
}
