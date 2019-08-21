package com.share.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.bean.CustomData;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.view.banner.MyBannerView;
import com.share.R;
import com.share.R2;
import com.share.bean.ShareCircleBean;
import com.share.bean.ZanBean;
import com.share.utils.CommentsPopupUtils;
import com.share.view.CoustomShareView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.lib.view.banner.MyBannerView.TYPE_CIRCLE;
import static com.share.view.CoustomShareView.TYPE_ASK;
import static com.share.view.CoustomShareView.TYPE_CONTENT_1;
import static com.share.view.CoustomShareView.TYPE_CONTENT_2;
import static com.share.view.CoustomShareView.TYPE_CONTENT_3;
import static com.share.view.CoustomShareView.TYPE_NEWS;

@Route(path = ARouterPathUtils.Share_ShareFragment)
public class ShareFragment extends BaseAppFragment {
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.springView)
    SpringView springView;
    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.myBanner)
    MyBannerView myBanner;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
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
        HttpUtils.with(getContext())
                .addParam("requestType", "SHARE_CIRCLE_LIST")
                .addParam("page", page)
                .addParam("limit", 10)
                .addParam("token", SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserToken())
                .execute(new HttpNormalCallBack<ShareCircleBean>() {
                    @Override
                    public void onSuccess(ShareCircleBean result) {
                        springView.onFinishFreshAndLoad();

                        showLog("CODE:" + result.getCode());
                        if (result.getCode() == CodeUtil.CODE_200) {

                            if (page == 0) {
                                list.clear();
                            }

                            list.addAll(result.getObj().getRows());
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

    private List<ShareCircleBean.ObjBean.RowsBean> list = new ArrayList<>();

    private List<CustomData> bannerList = new ArrayList<>();

    private void initView() {


        bannerList.add(new CustomData("http://pvjdparam.bkt.clouddn.com/fenshare_banner_1.png", "你好", false));
        bannerList.add(new CustomData("http://pvjdparam.bkt.clouddn.com/fenshare_banner_1.png", "你好", false));

        myBanner.setupdateData(bannerList, TYPE_CIRCLE);


        homeAdapter = new HomeAdapter(getContext(), list);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        rv.setAdapter(homeAdapter);


        /**
         * springView
         */
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(true);
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
        return R.layout.fragment_share;
    }


    //----------------------------------------------------------------------------------HomeAdapter


    private class HomeAdapter extends BaseAdapter<ShareCircleBean.ObjBean.RowsBean> {

        public HomeAdapter(Context context, List<ShareCircleBean.ObjBean.RowsBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_share;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<ShareCircleBean.ObjBean.RowsBean> mData) {
            final int type = mData.get(position).getBusiness_type();
            final CoustomShareView coustomShareView = holder.getView(R.id.c_share);
            final ShareCircleBean.ObjBean.RowsBean rowsBean = mData.get(position);
            coustomShareView.setCoustomShareViewListener(new CoustomShareView.CoustomShareViewListener() {
                @Override
                public void onCommentsClick() {
                    CommentsPopupUtils commentsPopupUtils = new CommentsPopupUtils(getActivity());
                    //弹出评论框
                    commentsPopupUtils.showVoicePopu(getActivity().getWindow().getDecorView(), mData.get(position).getShare_circle_id());

                    commentsPopupUtils.setCommentsPopupUtilsListenerr(new CommentsPopupUtils.CommentsPopupUtilsListener() {
                        @Override
                        public void onCommetntCountChange(int count) {
                            coustomShareView.setTv_comments(count + "");


                        }
                    });

                }

                @Override
                public void onZanClick() {

                    requestZan(mData.get(position).getShare_circle_id(), coustomShareView);

                }

                @Override
                public void onItemClick() {


                    switch (type) {
                        case 0: {

                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);

                            break;
                        }
                        case 1: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }
                        case 2: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }
                        case 3: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }

                        case 4: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }

                        case 5: {
                            ARouter.getInstance()
                                    .build(ARouterPathUtils.Dayi_StudentQuestionDetailActivity, GroupUtils.NEED_LOGIN)
                                    .withString("questionId", rowsBean.getQuestion_id())
                                    .navigation();


                            break;
                        }

                        case 6: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }
                        case 7: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }
                        case 8: {
                            String url = rowsBean.getBusiness_link();
                            goToWebView(url);
                            break;
                        }
                    }


                }
            });


            switch (type) {
                case 0: {
                    coustomShareView.setViewType(TYPE_CONTENT_3);
                    break;
                }
                case 1: {
                    coustomShareView.setViewType(TYPE_CONTENT_3);
                    break;
                }
                case 2: {
                    coustomShareView.setViewType(TYPE_CONTENT_3);
                    break;
                }
                case 3: {
                    coustomShareView.setViewType(TYPE_CONTENT_3);
                    break;
                }

                case 4: {
                    coustomShareView.setViewType(TYPE_CONTENT_1);
                    break;
                }

                case 5: {
                    coustomShareView.setViewType(TYPE_ASK);
                    break;
                }

                case 6: {
                    coustomShareView.setViewType(TYPE_NEWS);
                    break;
                }
                case 7: {
                    coustomShareView.setViewType(TYPE_CONTENT_1);
                    break;
                }
                case 8: {
                    coustomShareView.setViewType(TYPE_CONTENT_2);
                    break;
                }
            }


            coustomShareView.setIv_head(rowsBean.getUser_icon());
            coustomShareView.setTv_title(rowsBean.getUser_name());
            if (rowsBean.getType().equals("0")) {
                //没有权限
                coustomShareView.setIv_lock_Open(false);
            } else {
                coustomShareView.setIv_lock_Open(true);
            }
            coustomShareView.setTv_subject(rowsBean.getTitle());


            List<String> iamgeFile = rowsBean.getFile();

            if (iamgeFile.size() > 0) {

                if (iamgeFile.size() == 1) {
                    coustomShareView.setIv_image1(iamgeFile.get(0));
                }
                if (iamgeFile.size() == 2) {
                    coustomShareView.setIv_image2(iamgeFile.get(1));
                }
            }


            coustomShareView.setTv_money(rowsBean.getCurrent_value());
            coustomShareView.setTv_content_text1(rowsBean.getContent());
            coustomShareView.setTv_content_text2(rowsBean.getTheme_content());
            coustomShareView.setIv_newImage(rowsBean.getArtic_cover());

            coustomShareView.setTv_news_content(rowsBean.getArtic_title());


            String is_rabbit_training = rowsBean.getIs_rabbit_training();
            if (is_rabbit_training.equals("0")) {
                //是兔讯
                coustomShareView.setTv_tip_Show(true);
            } else {
                coustomShareView.setTv_tip_Show(false);
            }


            coustomShareView.setTv_Time(rowsBean.getArtic_create_date());

            coustomShareView.setTv_zan(rowsBean.getLike_count() + "");
            coustomShareView.setTv_comments(rowsBean.getComment_count() + "");
        }
    }

    private void goToWebView(String url) {
        ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                .withString("urlPath", url)
                .navigation();
    }


    /**
     * 点赞
     *
     * @param id
     * @param coustomShareView
     */
    private void requestZan(int id, final CoustomShareView coustomShareView) {


        HttpUtils.with(getContext())
                .addParam("requestType", "SHARE_LIKE")
                .addParam("token", SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserToken())
                .addParam("share_circle_id", id)
                .execute(new HttpDialogCallBack<ZanBean>() {


                    @Override
                    public void onSuccess(ZanBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            int zan = result.getObj().getLike_status();

                            if (zan == 1) {
                                coustomShareView.setIv_zan_Show(true);
                            } else {
                                coustomShareView.setIv_zan_Show(false);
                            }


                            coustomShareView.setTv_zan(result.getObj().getLike_count() + "");

                        }

                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }

}
