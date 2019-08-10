package com.dayi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.activity.PrivateAskActivity;
import com.dayi.bean.SiXueBean;
import com.dayi.utils.pop.ZoomImagePopupUtils;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.share.ShareUtils;
import com.lib.view.player.MyJzvdStd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.Dayi_SiXueFragment)
public class SiXueFragment extends BaseAppFragment {

    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.iv_ad)
    ImageView ivAd;
    @BindView(R2.id.iv_process)
    ImageView ivProcess;
    @BindView(R2.id.iv_ask)
    ImageView ivAsk;
    @BindView(R2.id.lin_more)
    LinearLayout linMore;
    @BindView(R2.id.lin_video)
    LinearLayout linVideo;
    @BindView(R2.id.springView)
    SpringView springView;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        stateView.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                initData();
            }
        });


        initView();


    }

    private void initData() {
        HttpUtils.with(getContext())
                .addParam("requestType", "QUESTION_PAGE_DATA")
                .addParam("token", SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserToken())
                .execute(new HttpNormalCallBack<SiXueBean>() {
                    @Override
                    public void onSuccess(SiXueBean result) {
                        springView.onFinishFreshAndLoad();
                        if (result.getCode() == CodeUtil.CODE_200) {


                            updateUI(result);
                            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

                        } else {
                            stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }
                    }

                    @Override
                    public void onError(String e) {
                        springView.onFinishFreshAndLoad();
                        if (stateView != null) {
                            stateView.setViewState(MultiStateView.VIEW_STATE_NETWORK_ERROR);
                        }
                    }
                });

    }

    private void updateUI(SiXueBean result) {

        List<SiXueBean.ObjBean.QuestionFileBean> fileBeanList = result.getObj().getQuestion_file();


        if (fileBeanList != null && fileBeanList.size() > 0) {
            for (SiXueBean.ObjBean.QuestionFileBean questionFileBean : fileBeanList) {

                switch (questionFileBean.getCode()) {
                    case "video_1": {

                        String url = questionFileBean.getHash();

                        insertVideo(url);
                        break;
                    }

                    case "img_1": {
                        String url1 = questionFileBean.getHash();
                        Glide.with(this)
                                .load(url1)
                                .apply(GlideConfig.getRectangleOptions())
                                .into(ivAd);

                        break;
                    }

                    case "img_2": {
                        String url2 = questionFileBean.getHash();
                        Glide.with(this)
                                .load(url2)
                                .apply(GlideConfig.getRectangleOptions())
                                .into(ivProcess);

                        break;
                    }
                }

            }
        }

        list.clear();
        list.addAll(result.getObj().getQuestion_list());
        homeAdapter.notifyDataSetChanged();


    }

    /**
     * 插入视频
     */
    MyJzvdStd jz_player;

    private void insertVideo(String url) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_video_temp, null);

        jz_player = view.findViewById(R.id.jz_player);

        jz_player.setUp(url
                , "");
        Glide.with(this).load(url).into(jz_player.thumbImageView);

        linVideo.addView(view);


    }


    private HomeAdapter homeAdapter;
    private List<SiXueBean.ObjBean.QuestionListBean> list = new ArrayList<>();

    private void initView() {

        homeAdapter = new HomeAdapter(getContext(), list);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rv.setAdapter(homeAdapter);

        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                initData();

            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sixue;


    }


    @OnClick({R2.id.iv_ask, R2.id.lin_more})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_ask) {
            ARouter.getInstance().build(ARouterPathUtils.Dayi_AskQuestionActivity, GroupUtils.NEED_LOGIN).navigation();
        } else if (i == R.id.lin_more) {
            ARouter.getInstance().build(ARouterPathUtils.Dayi_PrivateAskActivity).navigation();
        }
    }


    private class HomeAdapter extends BaseAdapter<SiXueBean.ObjBean.QuestionListBean> {


        public HomeAdapter(Context context, List<SiXueBean.ObjBean.QuestionListBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_sixue;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<SiXueBean.ObjBean.QuestionListBean> mData) {
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
                            ZoomImagePopupUtils zoomImagePopupUtils = new ZoomImagePopupUtils(getActivity());
                            zoomImagePopupUtils.setZoomImage(url1);
                            zoomImagePopupUtils.showAnswerPopuPopu(v);
                        }
                    });

                    if (url1 != null) {
                        Glide.with(getActivity())
                                .load(url1)
                                .apply(GlideConfig.getRoundOptions(20))
                                .into(iv_iamge1);
                        LogUtil.e("问答第一张图");
                    }

                }


                /**
                 * 问答第二张图
                 */
                if (mData.get(position).getFile().size() >= 2) {
                    ImageView iv_iamge2 = holder.getView(R.id.iv_image2);
                    final String url2 = mData.get(position).getFile().get(1);


                    iv_iamge2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZoomImagePopupUtils zoomImagePopupUtils = new ZoomImagePopupUtils(getActivity());
                            zoomImagePopupUtils.setZoomImage(url2);
                            zoomImagePopupUtils.showAnswerPopuPopu(v);
                        }
                    });
                    if (url2 != null) {
                        Glide.with(getActivity())
                                .load(url2)
                                .apply(GlideConfig.getRoundOptions(20))
                                .into(iv_iamge2);
                        LogUtil.e("问答二张图");
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
                            .build(ARouterPathUtils.Dayi_StudentQuestionDetailActivity, GroupUtils.NEED_LOGIN)
                            .withString("questionId", mData.get(position).getQuestion_id() + "")
                            .navigation();
                }
            });


            holder.getView(R.id.lin_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = mData.get(position).getFile().get(0);
                    String question_id = mData.get(position).getQuestion_id() + "";


                    ShareUtils.getInstance(getActivity())
                            .setShareWebUrl("https://www.baidu.com/", "免费分享旁听", url, "分享后和可免费旁听")
                            .setShareId(question_id)
                            .onPenCoustomShareBorad();

                }
            });

        }
    }


    private boolean isVisibleToUser = false;

    /**
     * 懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.isVisibleToUser = isVisibleToUser;

        lazyLoad();


    }

    private void lazyLoad() {

        if (isVisibleToUser && isOnResume) {
            initData();
        }

    }


    private boolean isOnResume = false;

    @Override
    public void onResume() {
        super.onResume();
        isOnResume = true;

        lazyLoad();

    }


    @Override
    public void onStop() {
        super.onStop();
        isOnResume = false;

    }
}
