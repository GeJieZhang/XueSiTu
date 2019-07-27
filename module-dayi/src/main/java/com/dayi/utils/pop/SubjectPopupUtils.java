package com.dayi.utils.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dayi.R;
import com.dayi.bean.SubjectBean;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.zyyoona7.popup.EasyPopup;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SubjectPopupUtils {


    private Activity activity;


    private GradeAdapter gradeAdapter;

    private String title = "学科";
    private String[] grade = {
            "语文", "数学", "英语",
            "化学", "地理", "生物", "历史", "物理",
            "政治"
    };
    SubjectBean subjectBean;
    private List<Button> btnGradeList = new ArrayList<>();
    private List<String> mDataGrade = new ArrayList<>();

    public SubjectPopupUtils(Activity ac) {

        this.activity = ac;

        initPopuPopu();
    }

    private EasyPopup popu;

    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup__choose_grade)
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


    private LinearLayout lin_parent;
    private CardView card;

    RecyclerView rvGrade;
    TextView tv_title;
    Button btnSure;

    private void initView(View view) {

        lin_parent = view.findViewById(R.id.lin_parent);
        card = view.findViewById(R.id.card);

        rvGrade = view.findViewById(R.id.rv_grade);
        tv_title = view.findViewById(R.id.tv_title);
        btnSure = view.findViewById(R.id.btn_sure);


        lin_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popu.dismiss();

            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        tv_title.setText(title);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (subjectId == 0) {
                    Toast.makeText(activity, "请选择学科！", Toast.LENGTH_SHORT).show();
                }else {

                    if (gradeChooseListener != null) {
                        gradeChooseListener.onSure(subjectId);
                    }
                }


            }
        });
        initData();
        gradeAdapter = new GradeAdapter(activity, mDataGrade);
        rvGrade.setLayoutManager(new GridLayoutManager(activity, 3));
        rvGrade.setAdapter(gradeAdapter);

    }

    private void initData() {
        mDataGrade.clear();

        for (String s : grade) {

            mDataGrade.add(s);

        }


    }

    public void dismiss() {
        popu.dismiss();
    }


    private int subjectId = 0;

    public class GradeAdapter extends BaseAdapter<String> {

        public GradeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_subject;
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

                    btn_text.setBackgroundResource(R.drawable.bg_circle_bule_r22_1);
                    btn_text.setTextColor(activity.getResources().getColor(R.color.white));


                    int id = position + 1;
                    subjectBean = new SubjectBean();
                    subjectBean.setType("1");
                    subjectBean.setSubject_id(id + "");
                    subjectId = id;

                }


            });

        }
    }

    private void initGradeButton() {
        for (Button button : btnGradeList) {
            button.setBackgroundResource(R.drawable.bg_circle_hollow_r22_1);
            button.setTextColor(activity.getResources().getColor(R.color.base_text1));
        }

    }


    public interface GradeChooseListener {


        void onSure(int str);


    }

    private GradeChooseListener gradeChooseListener;

    public void setGradeChooseListener(GradeChooseListener chooseListener) {

        this.gradeChooseListener = chooseListener;

    }

    public void showAnswerPopuPopu(View view) {

        popu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

}
