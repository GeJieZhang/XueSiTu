package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.TimeChosePopUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.User_RecordsActivity)
public class RecordsActivity extends BaseAppActivity {
    @BindView(R2.id.lin_more)
    LinearLayout linMore;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.tv_time)
    TextView tvTime;


    private HomeAdapter homeAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreateView() {
        initTitle();
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        homeAdapter = new HomeAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(homeAdapter);

        initTimePicker();

        tvTime.setText(TimeUtils.getNowString(TimeUtils.FORMAT_4));

    }

    private TimeChosePopUtils timeChosePopUtils;

    private void initTimePicker() {
        timeChosePopUtils = new TimeChosePopUtils(this, true, true, false, false, false, false);

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("消费记录")
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_records;
    }


    @OnClick({R2.id.lin_more})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_more) {

            timeChosePopUtils.show();
        }
    }


    private class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_record;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

        }
    }


}
