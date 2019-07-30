package com.dayi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.PrivateAskBean;
import com.dayi.bean.QuestionList;
import com.dayi.utils.pop.ZoomImagePopupUtils;
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
import com.lib.utls.glide.GlideConfig;
import com.lib.view.navigationbar.NomalNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.Dayi_PrivateAskActivity)
public class PrivateAskActivity extends BaseAppActivity {
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.btn_sure)
    Button btnSure;
    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.springView)
    SpringView springView;

    @Override
    protected void onCreateView() {
        initTitle();

        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        stateView.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                initData();
            }
        });

        initView();

        initData();

    }


    private int page = 0;

    private void initData() {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_LIST")
                .addParam("page", page)
                .addParam("limit", "10")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .execute(new HttpNormalCallBack<PrivateAskBean>() {
                    @Override
                    public void onSuccess(PrivateAskBean result) {

                        springView.onFinishFreshAndLoad();
                        if (result.getCode() == CodeUtil.CODE_200) {


                            if (page == 0) {
                                list.clear();
                                list.addAll(result.getObj().getRows());
                            } else {
                                list.addAll(result.getObj().getRows());
                            }

                            homeAdapter.notifyDataSetChanged();
                            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

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

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("私问")
                .builder();

        // navigationBar.getRightTextView()
    }

    private HomeAdapter homeAdapter;
    private List<PrivateAskBean.ObjBean.RowsBean> list = new ArrayList<>();

    private void initView() {

        homeAdapter = new HomeAdapter(this, list);

        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(homeAdapter);


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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_private_ask;
    }


    @OnClick({R2.id.rv, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.rv) {


        } else if (i == R.id.btn_sure) {

            ARouter.getInstance().build(ARouterPathUtils.Dayi_AskQuestionActivity).navigation();

        }
    }


    private class HomeAdapter extends BaseAdapter<PrivateAskBean.ObjBean.RowsBean> {


        public HomeAdapter(Context context, List<PrivateAskBean.ObjBean.RowsBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_sixue;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<PrivateAskBean.ObjBean.RowsBean> mData) {


            /**
             * 问答第一张图
             */

            if (mData.get(position).getFile() != null) {

                if (mData.get(position).getFile().size() >= 1) {
                    ImageView iv_iamge1 = holder.getView(R.id.iv_image1);
                    final String url1 = mData.get(position).getFile().get(0);
                    iv_iamge1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZoomImagePopupUtils zoomImagePopupUtils = new ZoomImagePopupUtils(PrivateAskActivity.this);
                            zoomImagePopupUtils.setZoomImage(url1);
                            zoomImagePopupUtils.showAnswerPopuPopu(v);
                        }
                    });

                    if (url1 != null) {
                        Glide.with(PrivateAskActivity.this)
                                .load(url1)
                                .apply(GlideConfig.getRoundOptions(20))
                                .into(iv_iamge1);
                    }

                }


                /**
                 * 问答第二张图
                 */
                if (mData.get(position).getFile().size() >= 2) {
                    ImageView iv_iamge2 = holder.getView(R.id.iv_image1);
                    final String url2 = mData.get(position).getFile().get(1);


                    iv_iamge2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZoomImagePopupUtils zoomImagePopupUtils = new ZoomImagePopupUtils(PrivateAskActivity.this);
                            zoomImagePopupUtils.setZoomImage(url2);
                            zoomImagePopupUtils.showAnswerPopuPopu(v);
                        }
                    });
                    if (url2 != null) {
                        Glide.with(PrivateAskActivity.this)
                                .load(url2)
                                .apply(GlideConfig.getRoundOptions(20))
                                .into(iv_iamge2);
                    }
                }

            }

            TextView tv_money = holder.getView(R.id.tv_money);
            ImageView iv_lock = holder.getView(R.id.iv_lock);

            Button btn_sure = holder.getView(R.id.btn_sure);

            if (mData.get(position).getType() == 0) {

                iv_lock.setImageResource(R.mipmap.icon_lock);
                btn_sure.setText("1折付费旁听");
            } else {
                iv_lock.setImageResource(R.mipmap.icon_unlock);

                btn_sure.setText("查看详情");
            }

            tv_money.setText(mData.get(position).getCurrent_value() + "");

            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ARouter.getInstance()
                            .build(ARouterPathUtils.Dayi_StudentQuestionDetailActivity)
                            .withString("questionId", mData.get(position).getQuestion_id() + "")
                            .navigation();
                }
            });


        }
    }
}
