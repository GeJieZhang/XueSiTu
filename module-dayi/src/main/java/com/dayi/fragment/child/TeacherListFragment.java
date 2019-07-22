package com.dayi.fragment.child;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.activity.StudentQuestionDetailActivity;
import com.dayi.bean.QuestionDetail;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TeacherListFragment extends BaseAppFragment {
    @BindView(R2.id.rv)
    RecyclerView rv;

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


            holder.getView(R.id.tv_see).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int replyId = mData.get(position).getReply_id();



                    ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherAnswerDetailActivity)
                            .withString("replyId", replyId + "")
                            .navigation();
                }
            });
        }
    }


    public void updateData(List<QuestionDetail.ObjBean.ReplyUserListBean> data) {

        list.addAll(data);
        homeAdapter.notifyDataSetChanged();

    }


}
