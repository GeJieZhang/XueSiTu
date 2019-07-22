package com.dayi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.QuestionList;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
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

    @Override
    protected void onCreateView() {

        initTitle();

        initView();

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("私问")
                .builder();

        // navigationBar.getRightTextView()
    }

    private HomeAdapter homeAdapter;
    private List<String> list = new ArrayList<>();

    private void initView() {
        list.add("");
        list.add("");
        list.add("");
        list.add("");

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
        return R.layout.activity_private_ask;
    }


    @OnClick({R2.id.rv, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.rv) {


        } else if (i == R.id.btn_sure) {


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
