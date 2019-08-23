package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lib.app.ARouterPathUtils;
import com.lib.bean.ConfigBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.html.HtmlPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.utils.pop.TeacherWriteQustionPopupUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_TeacherWorkbenchActivity)
public class TeacherWorkbenchActivity extends BaseAppActivity {
    @BindView(R2.id.rv)
    RecyclerView rv;

    private String str[] = {"答疑", "编题", "我的课程", "我的学生", "教辅", "录播课"};

    private int icon[] = {R.mipmap.icon_answer, R.mipmap.icon_problem, R.mipmap.icon_course_teacher
            , R.mipmap.icon_student_2, R.mipmap.icon_book, R.mipmap.icon_class_video};

    private List<String> list = new ArrayList<>();

    private ConfigBean configBean;

    @Override
    protected void onCreateView() {
        String json = SharedPreferenceManager.getInstance(this).getUserCache().getConfigJson();
        configBean = new Gson().fromJson(json, ConfigBean.class);
        initTitle();
        initView();
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("工作台")
                .builder();


    }

    private HomeAdapter homeAdapter;

    private void initView() {
        for (String s : str) {
            list.add(s);
        }


        rv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        homeAdapter = new HomeAdapter(this, list);
        rv.setAdapter(homeAdapter);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_workbench;
    }


    private class HomeAdapter extends BaseAdapter<String> {


        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_teacher_workbench;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, List<String> mData) {

            final ImageView iv_image = holder.getView(R.id.iv_image);
            TextView tv_text = holder.getView(R.id.tv_text);
            iv_image.setImageResource(icon[position]);
            tv_text.setText(mData.get(position));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0: {
                            //老师-答疑

                            ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherQuestionListActivity).navigation();


                            break;
                        }
                        case 1: {
                            //编题
                            TeacherWriteQustionPopupUtils teacherWriteQustionPopupUtils = new TeacherWriteQustionPopupUtils(TeacherWorkbenchActivity.this);
                            teacherWriteQustionPopupUtils.setUrl(configBean.getObj().getTeacher_main_url());
                            teacherWriteQustionPopupUtils.showAnswerPopuPopu(iv_image);

                            break;
                        }
                        case 2: {

                            //老师-课程订单

                            ARouter.getInstance().build(ARouterPathUtils.User_MyClassActivity).navigation();
                            break;
                        }
                        case 3: {
                            //我的学生
                            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                                    .withString("urlPath", configBean.getObj().getStudent_list_url())
                                    .navigation();
                            break;
                        }
                        case 4: {
                            //老师-教辅
                            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                                    .withString("urlPath", configBean.getObj().getStu_assisiant_url())
                                    .navigation();

                            break;
                        }
                        case 5: {

                            //老师-录播课
                            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                                    .withString("urlPath", configBean.getObj().getStu_videocourse_url())
                                    .navigation();
                            break;
                        }
                    }
                }
            });


        }


    }

}
