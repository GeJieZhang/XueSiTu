package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
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

    private HomeAdapter homeAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreateView() {

        initTitle();
        initView();

        initData();

    }

    private void initData() {
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        homeAdapter.notifyDataSetChanged();

    }

    private void initView() {


        mvTop.setMessageTopViewListener(new MessageTopView.MessageTopViewListener() {
            @Override
            public void onClickTS() {

            }

            @Override
            public void onClickSH() {

            }

            @Override
            public void onClickDD() {

            }

            @Override
            public void onClickPL() {

            }
        });

        homeAdapter = new HomeAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
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


    private class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_message;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPathUtils.User_MessageDetailActivity).navigation();
                }
            });

        }
    }
}
