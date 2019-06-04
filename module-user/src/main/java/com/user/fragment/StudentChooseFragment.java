package com.user.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.user.R;
import com.user.R2;
import com.user.bean.IdentityUserBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StudentChooseFragment extends BaseAppFragment {
    @BindView(R2.id.rv_grade)
    RecyclerView rvGrade;

    @BindView(R2.id.tv_title)
    TextView tv_title;

    @BindView(R2.id.btn_sure)
    Button btnSure;

    private GradeAdapter gradeAdapter;

    private String title = "年级";
    private String[] grade = {
            "一年级", "二年级", "三年级",
            "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三"};

    IdentityUserBean identityUserBean;
    private List<Button> btnGradeList = new ArrayList<>();
    private List<String> mDataGrade = new ArrayList<>();

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        tv_title.setText(title);

        initView();
        initData();
        gradeAdapter = new GradeAdapter(getContext(), mDataGrade);
        rvGrade.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvGrade.setAdapter(gradeAdapter);


    }

    private void initView() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event<IdentityUserBean>(1, identityUserBean), EventBusTagUtils.TeacherStudentChooseFragment);
            }
        });
    }

    private void initData() {
        mDataGrade.clear();

        for (String s : grade) {

            mDataGrade.add(s);

        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment__choose_grade;
    }


    public class GradeAdapter extends BaseAdapter<String> {

        public GradeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_book;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, List<String> mData) {

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


                    if (gradeChooseListener != null) {
                        gradeChooseListener.onGradeChoose(grade[position]);
                    }


                    int id = position + 1;


                    identityUserBean = new IdentityUserBean();
                    identityUserBean.setType("1");
                    identityUserBean.setGrade_id(id + "");


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


    public interface GradeChooseListener {

        void onGradeChoose(String str);
    }

    private GradeChooseListener gradeChooseListener;

    public void setGradeChooseListener(GradeChooseListener chooseListener) {

        this.gradeChooseListener = chooseListener;

    }
}
