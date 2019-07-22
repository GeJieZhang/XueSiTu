package com.dayi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.Dayi_SiXueFragment)
public class SiXueFragment extends BaseAppFragment {


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

    //广告图

    String url1 = "http://pu00k0ssj.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190720155403.png";


    //流程图
    String url2 = "http://pu00k0ssj.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20190720155835.png";



    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        Glide.with(this)
                .load(url1)
                .apply(GlideConfig.getRectangleOptions())
                .into(ivAd);

        Glide.with(this)
                .load(url2)
                .apply(GlideConfig.getRectangleOptions())
                .into(ivProcess);

        initView();


    }

    private HomeAdapter homeAdapter;
    private List<String> list = new ArrayList<>();

    private void initView() {
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        homeAdapter = new HomeAdapter(getContext(), list);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rv.setAdapter(homeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sixue;


    }


    @OnClick({R2.id.iv_ask, R2.id.lin_more})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_ask) {

            ARouter.getInstance().build(ARouterPathUtils.Dayi_PrivateAskActivity).navigation();
        } else if (i == R.id.lin_more) {
        }
    }


    private class HomeAdapter extends BaseAdapter<String> {


        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_sixue;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

        }
    }


}
