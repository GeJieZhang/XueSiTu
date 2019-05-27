package com.user.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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

//        setContentView(R.layout.activity_book_choose);
//        ButterKnife.bind(this);


        initData();


        initView();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_choose;
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


    private List<Button> btnGradeList = new ArrayList<>();
    private List<Button> btnDisciplineList = new ArrayList<>();

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

            final Button btn_text = holder.getView(R.id.btn_text);
            btnGradeList.add(btn_text);
            btn_text.setText(mData.get(position));
            btn_text.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    initGradeButton();


                    btn_text.setBackgroundResource(R.drawable.bg_circle_red_r22_1);
                    btn_text.setTextColor(getResources().getColor(R.color.white));
                }


            });

        }
    }

    private void initGradeButton() {
        for (Button button : btnGradeList) {
            button.setBackgroundResource(R.drawable.bg_circle_hollow_r22_1);
            button.setTextColor(getResources().getColor(R.color.base_text1));
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

            final Button btn_text = holder.getView(R.id.btn_text);
            btnDisciplineList.add(btn_text);
            btn_text.setText(mData.get(position));
            btn_text.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    initDisciplineButton();


                    btn_text.setBackgroundResource(R.drawable.bg_circle_red_r22_1);
                    btn_text.setTextColor(getResources().getColor(R.color.white));
                }


            });

        }
    }

    private void initDisciplineButton() {
        for (Button button : btnDisciplineList) {
            button.setBackgroundResource(R.drawable.bg_circle_hollow_r22_1);
            button.setTextColor(getResources().getColor(R.color.base_text1));
        }
    }
}
