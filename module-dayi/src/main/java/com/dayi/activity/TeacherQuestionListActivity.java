package com.dayi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.QuestionList;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.utls.glide.GlideConfig;
import com.lib.view.navigationbar.NomalNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 老师的答疑列表
 */
@Route(path = ARouterPathUtils.Dayi_TeacherQuestionListActivity)
public class TeacherQuestionListActivity extends BaseAppActivity {
    @BindView(R2.id.rv1)
    RecyclerView rv1;
    @BindView(R2.id.rv2)
    RecyclerView rv2;

    private HomeAdapter1 homeAdapter1;
    private HomeAdapter2 homeAdapter2;


    private List<QuestionList.ObjBean.QuestionListBean> list1 = new ArrayList<>();
    private List<QuestionList.ObjBean.HistoryListBean> list2 = new ArrayList<>();

    @Override
    protected void onCreateView() {
        initTitle();
        initView();
        initData();
    }


    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("我的提问")
                .builder();


    }


    private void initData() {
        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_USER_LIST")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .execute(new HttpDialogCallBack<QuestionList>() {
                    @Override
                    public void onSuccess(QuestionList result) {


                        if (result.getCode() == CodeUtil.CODE_200) {

                            list1.addAll(result.getObj().getQuestion_List());

                            list2.addAll(result.getObj().getHistory_list());

                            homeAdapter1.notifyDataSetChanged();

                            homeAdapter2.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    private void initView() {

        rv1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        homeAdapter1 = new HomeAdapter1(this, list1);
        homeAdapter2 = new HomeAdapter2(this, list2);

        rv1.setAdapter(homeAdapter1);
        rv2.setAdapter(homeAdapter2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_question_list;
    }


    /**
     * 最新发布
     */
    private class HomeAdapter1 extends BaseAdapter<QuestionList.ObjBean.QuestionListBean> {


        public HomeAdapter1(Context context, List<QuestionList.ObjBean.QuestionListBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_teacher_question_list;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<QuestionList.ObjBean.QuestionListBean> mData) {
/**
 * 问答第一张图
 */

            if (mData.get(position).getFile() != null) {

                if (mData.get(position).getFile().size() >= 1) {
                    ImageView iv_iamge1 = holder.getView(R.id.iv_image1);
                    String url1 = mData.get(position).getFile().get(0);
                    if (url1 != null) {
                        Glide.with(TeacherQuestionListActivity.this)
                                .load(url1)
                                .apply(GlideConfig.getRoundOptions(10))
                                .into(iv_iamge1);
                    }

                }


                /**
                 * 问答第二张图
                 */
                if (mData.get(position).getFile().size() >= 2) {
                    ImageView iv_iamge2 = holder.getView(R.id.iv_image1);
                    String url2 = mData.get(position).getFile().get(1);
                    if (url2 != null) {
                        Glide.with(TeacherQuestionListActivity.this)
                                .load(url2)
                                .apply(GlideConfig.getRoundOptions(10))
                                .into(iv_iamge2);
                    }
                }

            }
            holder.getView(R.id.btn_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ARouter.getInstance()
                            .build(ARouterPathUtils.Dayi_TeacherAnswerQuestionDetailActivity)
                            .withString("questionId", mData.get(position).getQuestion_id() + "")
                            .navigation();
                }
            });
        }
    }


    /**
     * 历史问答
     */
    private class HomeAdapter2 extends BaseAdapter<QuestionList.ObjBean.HistoryListBean> {


        public HomeAdapter2(Context context, List<QuestionList.ObjBean.HistoryListBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_teacher_question_list;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<QuestionList.ObjBean.HistoryListBean> mData) {
            holder.getView(R.id.btn_sure).setVisibility(View.GONE);
            /**
             * 问答第一张图
             */

            if (mData.get(position).getFile() != null) {

                if (mData.get(position).getFile().size() >= 1) {
                    ImageView iv_iamge1 = holder.getView(R.id.iv_image1);
                    String url1 = mData.get(position).getFile().get(0);
                    if (url1 != null) {
                        Glide.with(TeacherQuestionListActivity.this)
                                .load(url1)
                                .apply(GlideConfig.getRoundOptions(10))
                                .into(iv_iamge1);
                    }

                }


                /**
                 * 问答第二张图
                 */
                if (mData.get(position).getFile().size() >= 2) {
                    ImageView iv_iamge2 = holder.getView(R.id.iv_image1);
                    String url2 = mData.get(position).getFile().get(1);
                    if (url2 != null) {
                        Glide.with(TeacherQuestionListActivity.this)
                                .load(url2)
                                .apply(GlideConfig.getRoundOptions(10))
                                .into(iv_iamge2);
                    }
                }

            }

            holder.getView(R.id.btn_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ARouter.getInstance()
                            .build(ARouterPathUtils.Dayi_StudentQuestionDetailActivity)
                            .withString("questionId", mData.get(position).getQuestion_id() + "")
                            .navigation();
                }
            });
        }

        @Override
        protected void toBindEmptyViewHolder(ViewHolder holder) {
            super.toBindEmptyViewHolder(holder);

            holder.setText(R.id.tv_empty, "暂无历史");
        }
    }


}
