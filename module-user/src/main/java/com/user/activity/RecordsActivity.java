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
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.time_deal.TimeUtils;
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
import com.user.TimeChosePopUtils;
import com.user.bean.RecordsBean;

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
    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.springView)
    SpringView springView;

    private HomeAdapter homeAdapter;
    private List<RecordsBean.ObjBean.RowsBean> list = new ArrayList<>();

    private int page = 0;

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
        homeAdapter = new HomeAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(homeAdapter);

        initTimePicker();

        tvTime.setText(TimeUtils.getNowString(TimeUtils.FORMAT_4));
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
        initData();
    }

    private void initData() {


        String time = tvTime.getText().toString().trim();

        HttpUtils.with(this)
                .addParam("requestType", "ACCOUNT_EXPENSES_RECORD")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("page", page)
                .addParam("limit", 10)
                .addParam("des_date", time)
                .execute(new HttpNormalCallBack<RecordsBean>() {
                    @Override
                    public void onSuccess(RecordsBean result) {

                        springView.onFinishFreshAndLoad();
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

    private TimeChosePopUtils timeChosePopUtils;

    private void initTimePicker() {
        timeChosePopUtils = new TimeChosePopUtils(this, true, true, false, false, false, false);
        timeChosePopUtils.setTimeChosePopUtilsListener(new TimeChosePopUtils.TimeChosePopUtilsListener() {
            @Override
            public void onTimeChosed(String str) {
                tvTime.setText(str);
                page = 0;
                initData();

            }
        });
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


    private class HomeAdapter extends BaseAdapter<RecordsBean.ObjBean.RowsBean> {

        public HomeAdapter(Context context, List<RecordsBean.ObjBean.RowsBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_record;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<RecordsBean.ObjBean.RowsBean> mData) {


            TextView tv_money = holder.getView(R.id.tv_money);
            holder.setText(R.id.tv_title, mData.get(position).getMsg());
            holder.setText(R.id.tv_time, mData.get(position).getCreate_date());
            holder.setText(R.id.tv_money, mData.get(position).getAmount() + "兔币");

            if (mData.get(position).getPay_type() == 1) {
                //收入
                tv_money.setTextColor(getResources().getColor(R.color.base_blue));

            } else {
                //消费
                tv_money.setTextColor(getResources().getColor(R.color.base_money));

            }
        }
    }


}
