package com.dayi.fragment.child;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.QuestionDetail;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TeacherListFragment extends BaseAppFragment {
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.lin_pay)
    LinearLayout linPay;
    @BindView(R2.id.btn_pay)
    Button btnPay;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_teacher_list;
    }

    private HomeAdapter homeAdapter;

    private List<QuestionDetail.ObjBean.ReplyUserListBean> list = new ArrayList<>();

    private void initView() {
        homeAdapter = new HomeAdapter(getActivity(), list);


        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rv.setAdapter(homeAdapter);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPayClick(v);
                }

            }
        });

        linPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用于蒙板下层点击事件
            }
        });
    }

    private class HomeAdapter extends BaseAdapter<QuestionDetail.ObjBean.ReplyUserListBean> {

        public HomeAdapter(Context context, List<QuestionDetail.ObjBean.ReplyUserListBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_student_question_detail;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<QuestionDetail.ObjBean.ReplyUserListBean> mData) {
            QuestionDetail.ObjBean.ReplyUserListBean replyBean = mData.get(position);


            ImageView ivHead = holder.getView(R.id.iv_head);

            TextView tvTitle = holder.getView(R.id.tv_title);
            TextView tvState = holder.getView(R.id.tv_state);
            TextView tvInfo = holder.getView(R.id.tv_info);
            Glide.with(getActivity())
                    .load(replyBean.getUser_icon())
                    .apply(GlideConfig.getCircleOptions())
                    .into(ivHead);

            tvTitle.setText(replyBean.getUser_name());

            if (replyBean.getIs_check() == 1) {
                tvState.setText("已认证");
            } else {
                tvState.setText("未认证");
                tvState.setTextColor(getResources().getColor(R.color.base_gray));
            }


            tvInfo.setText("准确率" + replyBean.getCorrect()
                    + "   好评率" + replyBean.getPraise()
                    + "   答题数" + replyBean.getReply_total());


            TextView tv_see = holder.getView(R.id.tv_see);

            if (mData.get(position).getIs_respondent() == 0) {
                tv_see.setVisibility(View.VISIBLE);
            } else {
                tv_see.setVisibility(View.GONE);
            }

            tv_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int replyId = mData.get(position).getReply_id();
                    ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherAnswerDetailActivity)
                            .withString("replyId", replyId + "")
                            .navigation();
                }
            });
        }

        @Override
        protected void toBindEmptyViewHolder(ViewHolder holder) {
            super.toBindEmptyViewHolder(holder);

            holder.setText(R.id.tv_empty, "暂无老师回答");
        }
    }


    public void updateData(List<QuestionDetail.ObjBean.ReplyUserListBean> data) {
        list.clear();
        list.addAll(data);
        homeAdapter.notifyDataSetChanged();

    }


    public void isShowPayPage(boolean b) {

        if (b) {
            linPay.setVisibility(View.VISIBLE);
        } else {
            linPay.setVisibility(View.GONE);
        }

    }


    private TeacherListFragmentListener listener;

    public void setTeacherListFragmentListener(TeacherListFragmentListener teacherListFragmentListener) {


        this.listener = teacherListFragmentListener;

    }

    public interface TeacherListFragmentListener {
        void onPayClick(View v);
    }


}
