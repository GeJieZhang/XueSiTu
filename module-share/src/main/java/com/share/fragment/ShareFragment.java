package com.share.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.bean.CustomData;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.view.banner.MyBannerView;
import com.share.R;
import com.share.R2;
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

            }
        });

        initView();

    }

    private List<String> list = new ArrayList<>();

    private List<CustomData> bannerList = new ArrayList<>();

    private void initView() {
        commentsPopupUtils = new CommentsPopupUtils(getActivity());

        bannerList.add(new CustomData("http://pvjdparam.bkt.clouddn.com/fenshare_banner_1.png", "你好", false));
        bannerList.add(new CustomData("http://pvjdparam.bkt.clouddn.com/fenshare_banner_1.png", "你好", false));

        myBanner.setupdateData(bannerList, TYPE_CIRCLE);

        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
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
        springView.setEnableFooter(false);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {


                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {

                springView.onFinishFreshAndLoad();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }


    //----------------------------------------------------------------------------------HomeAdapter

    CommentsPopupUtils commentsPopupUtils;
    private class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_share;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

            CoustomShareView coustomShareView = holder.getView(R.id.c_share);

            coustomShareView.setCoustomShareViewListener(new CoustomShareView.CoustomShareViewListener() {
                @Override
                public void onCommentsClick() {

                    commentsPopupUtils.showVoicePopu(getActivity().getWindow().getDecorView());
                }
            });
            switch (position) {
                case 0: {
                    coustomShareView.setViewType(TYPE_ASK);
                    break;
                }
                case 1: {
                    coustomShareView.setViewType(TYPE_CONTENT_1);
                    break;
                }
                case 2: {
                    coustomShareView.setViewType(TYPE_CONTENT_2);
                    break;
                }
                case 3: {
                    coustomShareView.setViewType(TYPE_CONTENT_3);
                    break;
                }

                case 4: {
                    coustomShareView.setViewType(TYPE_NEWS);
                    break;
                }
            }


        }
    }


}
