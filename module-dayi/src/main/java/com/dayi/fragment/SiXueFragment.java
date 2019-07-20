package com.dayi.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;

import butterknife.BindView;


@Route(path = ARouterPathUtils.Dayi_SiXueFragment)
public class SiXueFragment extends BaseAppFragment {


    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.iv_ad)
    ImageView ivAd;
    @BindView(R2.id.iv_process)
    ImageView ivProcess;


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


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sixue;


    }


}
