package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alipay.sdk.app.EnvUtils;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.utls.pay.WXPayUtils;
import com.lib.utls.pay.ZhiFuBaoPayUtils;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.bean.OrderList;
import com.user.bean.PayWeiXinBean;
import com.user.bean.PayZhiFuBaoBean;
import com.user.utils.pop.WriteMoneyNumUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_RechargeActivity)
public class RechargeActivity extends BaseAppActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.lin_wei)
    LinearLayout linWei;
    @BindView(R2.id.lin_zhifu)
    LinearLayout linZhifu;
    @BindView(R2.id.btn_recharge)
    Button btnRecharge;
    @BindView(R2.id.iv_wei)
    ImageView ivWei;
    @BindView(R2.id.iv_zhifu)
    ImageView ivZhifu;
    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.tv_money)
    TextView tvMoney;

    List<OrderList.ObjBean.RechargeRecommendListBean> mData = new ArrayList<>();
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreateView() {

        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        stateView.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                initData();
            }
        });
        //沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        initTitle();
        initData();

        homeAdapter = new HomeAdapter(this, mData);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(homeAdapter);
    }

    private void initData() {
        requestOrderList();
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("充值")

                .builder();

        // navigationBar.getRightTextView()
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }


    //0微信，1支付宝
    private int PAY_TYPE = 0;

    @OnClick({R2.id.lin_wei, R2.id.lin_zhifu, R2.id.btn_recharge})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_wei) {
            ivWei.setImageResource(R.mipmap.icon_choose);
            ivZhifu.setImageResource(R.mipmap.icon_default);
            PAY_TYPE = 0;
        } else if (i == R.id.lin_zhifu) {
            ivWei.setImageResource(R.mipmap.icon_default);

            ivZhifu.setImageResource(R.mipmap.icon_choose);
            PAY_TYPE = 1;
        } else if (i == R.id.btn_recharge) {

            if (recharge_id == 0) {
                showToast("请选择充值兔币数!");
                return;
            }

            if (PAY_TYPE == 0) {
                requestOrderByWeiXin(recharge_id + "");
            }

            if (PAY_TYPE == 1) {
                requestOrderByZhiFuBao(recharge_id + "");
            }

        }
    }

    private List<View> viewList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private int recharge_id = 0;

    public class HomeAdapter extends BaseAdapter<OrderList.ObjBean.RechargeRecommendListBean> {

        public HomeAdapter(Context context, List<OrderList.ObjBean.RechargeRecommendListBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_recharge;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<OrderList.ObjBean.RechargeRecommendListBean> mData) {

            final View view = holder.getView(R.id.v_bg);
            final TextView textView = holder.getView(R.id.tv_tb);
            final Button btn_song = holder.getView(R.id.btn_song);

            textView.setText(mData.get(position).getRecharge_amount() + "兔币");

            btn_song.setText("送" + mData.get(position).getGift_amount() + "兔币");

            if (mData.get(position).getIs_gift() == 1) {
                btn_song.setVisibility(View.VISIBLE);
            } else {
                btn_song.setVisibility(View.INVISIBLE);
            }

            textViewList.add(textView);
            viewList.add(view);
            holder.getView(R.id.lin_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    initViewBg();
                    view.setBackgroundResource(R.drawable.bg_part_circle4_select);
                    textView.setTextColor(getResources().getColor(R.color.white));
                    recharge_id = mData.get(position).getRecharge_id();

                }


            });


        }
    }

    private void initViewBg() {


        for (View view : viewList) {

            view.setBackgroundResource(R.drawable.bg_part_circle4_normal);

        }


        for (TextView textView : textViewList) {
            textView.setTextColor(getResources().getColor(R.color.base_title));

        }
    }


    /**
     * 微信充值
     *
     * @param
     */
    private void requestOrderByWeiXin(String recharge_id) {
        HttpUtils.with(this)
                .addParam("requestType", "WXPAY")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("recharge_id", recharge_id)
                .execute(new HttpDialogCallBack<PayWeiXinBean>() {
                    @Override
                    public void onSuccess(PayWeiXinBean result) {

                        new WXPayUtils.WXPayBuilder()
                                .setAppId(result.getObj().getAppid())
                                .setPartnerId(result.getObj().getPartnerid())
                                .setPrepayId(result.getObj().getPrepayid())
                                .setPackageValue(result.getObj().getPackageX())
                                .setNonceStr(result.getObj().getNoncestr())
                                .setTimeStamp(result.getObj().getTimestamp())
                                .setSign(result.getObj().getSign())
                                .build()
                                .toWXPayNotSign(RechargeActivity.this);

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    /**
     * 支付宝充值
     *
     * @param
     */
    private void requestOrderByZhiFuBao(String recharge_id) {
        HttpUtils.with(this)
                .addParam("requestType", "ALIPAY")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("recharge_id", recharge_id)
                .execute(new HttpDialogCallBack<PayZhiFuBaoBean>() {
                    @Override
                    public void onSuccess(PayZhiFuBaoBean result) {

                        new ZhiFuBaoPayUtils().toALiPayService(RechargeActivity.this, result.getObj().getBody());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    /**
     * 充值列表
     *
     * @param
     */
    private void requestOrderList() {
        HttpUtils.with(this)
                .addParam("requestType", "RECHARGE_RECOMMEND_LIST")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())

                .execute(new HttpNormalCallBack<OrderList>() {
                    @Override
                    public void onSuccess(OrderList result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            tvMoney.setText(result.getObj().getAccount() + "兔币");
                            mData.clear();

                            mData.addAll(result.getObj().getRecharge_recommend_list());
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


}
