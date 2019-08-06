package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.bean.MessageBean;
import com.user.view.MessageTopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_MessageActivity)
public class MessageActivity extends BaseAppActivity {
    @BindView(R2.id.mv_top)
    MessageTopView mvTop;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.springView)
    SpringView springView;
    private HomeAdapter homeAdapter;
    private List<MessageBean.ObjBean.OfficialDataBean> list = new ArrayList<>();

    @Override
    protected void onCreateView() {

        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        stateView.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                initData();
            }
        });

        initTitle();
        initView();


    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private int type = 0;
    private int page = 0;

    private void initData() {

        HttpUtils.with(this)
                .addParam("requestType", "GET_NEWS_BY_USERID_AND_TYPE")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("type", type)
                .addParam("page", page)
                .addParam("limit", "10")
                .execute(new HttpNormalCallBack<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {

                        springView.onFinishFreshAndLoad();
                        if (result.getCode() == CodeUtil.CODE_200) {
                            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);


                            setMessageTop(result);


                            if (page == 0) {
                                list.clear();

                                list.addAll(result.getObj().getOfficial_data());
                            } else {
                                list.addAll(result.getObj().getOfficial_data());
                            }


                            homeAdapter.notifyDataSetChanged();

                        } else {
                            stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }

                    }

                    @Override
                    public void onError(String e) {
                        stateView.setViewState(MultiStateView.VIEW_STATE_NETWORK_ERROR);
                    }
                });


    }

    private void setMessageTop(MessageBean result) {
        mvTop.hidePoint(MessageTopView.GF);
        mvTop.hidePoint(MessageTopView.GL);
        mvTop.hidePoint(MessageTopView.DD);
        mvTop.hidePoint(MessageTopView.PL);
        if (result.getObj().isOfficial_msg()) {
            mvTop.showPoint(MessageTopView.GF);


        }

        if (result.getObj().isAssociation_msg()) {
            mvTop.showPoint(MessageTopView.GL);


        }
        if (result.getObj().isOrder_msg()) {
            mvTop.showPoint(MessageTopView.DD);


        }
        if (result.getObj().isComment_msg()) {
            mvTop.showPoint(MessageTopView.PL);


        }

    }

    private void initView() {

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                initData();


            }

            @Override
            public void onLoadmore() {

                page++;
                initData();

            }
        });
        mvTop.setMessageTopViewListener(new MessageTopView.MessageTopViewListener() {
            @Override
            public void onClickTS() {
                page = 0;
                type = 0;
                initData();
            }

            @Override
            public void onClickSH() {
                page = 0;
                type = 1;
                initData();
            }

            @Override
            public void onClickDD() {
                page = 0;
                type = 2;
                initData();
            }

            @Override
            public void onClickPL() {
                page = 0;
                type = 3;
                initData();
            }
        });

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
        return R.layout.activity_message;
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("消息")
                .builder();


    }


    private class HomeAdapter extends BaseAdapter<MessageBean.ObjBean.OfficialDataBean> {

        public HomeAdapter(Context context, List<MessageBean.ObjBean.OfficialDataBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_message;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<MessageBean.ObjBean.OfficialDataBean> mData) {

            if (mData.get(position).getRead_status() == 0) {
                holder.getView(R.id.v_point).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.v_point).setVisibility(View.GONE);
            }

            holder.setText(R.id.tv_title, mData.get(position).getTitle());
            holder.setText(R.id.tv_content, mData.get(position).getContent());
            holder.setText(R.id.tv_time, mData.get(position).getPublish_time());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPathUtils.User_MessageDetailActivity)
                            .withString("id", mData.get(position).getId() + "")
                            .navigation();
                }
            });

        }
    }
}
