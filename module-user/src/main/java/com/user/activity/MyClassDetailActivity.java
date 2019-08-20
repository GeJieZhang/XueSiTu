package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.ui.base.control.ActivityCollector;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.bean.ClassDetailBean;
import com.user.utils.pop.ChoseDropTypePopupUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.User_MyClassDetailActivity)
public class MyClassDetailActivity extends BaseAppActivity {

    @Autowired(name = "id")
    String id;
    @Autowired(name = "type")
    String type;
    @BindView(R2.id.tv_order)
    TextView tvOrder;

    @BindView(R2.id.btn_share)
    Button btnShare;

    @BindView(R2.id.btn_pay)
    Button btnPay;

    @BindView(R2.id.btn_ttk)
    Button btnTtk;

    @BindView(R2.id.rv)
    RecyclerView rv;

    private String token = "";

    @Override
    protected void onCreateView() {
        ARouter.getInstance().inject(this);
        token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


        initTitle();

        initView();

        initData();
    }

    private HomeAdapter homeAdapter;
    private List<ClassDetailBean.ObjBean.CourseListBean> list = new ArrayList<>();

    private void initView() {
        homeAdapter = new HomeAdapter(this, list);

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rv.setAdapter(homeAdapter);


    }

    private ClassDetailBean classDetailBean;

    private void initData() {
        HttpUtils.with(this)
                .addParam("requestType", "ORDER_QUERY_DETAIL")
                .addParam("id", id)
                .addParam("type", type)
                .addParam("token", token)
                .execute(new HttpDialogCallBack<ClassDetailBean>() {
                    @Override
                    public void onSuccess(ClassDetailBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            classDetailBean = result;
                            list.clear();
                            list.addAll(result.getObj().getCourse_list());
                            homeAdapter.notifyDataSetChanged();

                            tvOrder.setText("订单号:" + result.getObj().getOrder_id());

                            btnPay.setText("已付:" + result.getObj().getTotal_price() + "兔币");

                            //btnTtk.setOnClickListe;

                            btnTtk.setVisibility(View.GONE);
                            if (result.getObj().getOrder_type() == 1 | result.getObj().getOrder_type() == 2) {
                                btnTtk.setVisibility(View.VISIBLE);
                            }

                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    @Override
    protected int getLayoutId() {


        return R.layout.activity_my_class_detail;

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("课程详情")

                .builder();


//                .setRightIcon(R.mipmap.nav_share)
//                .setRightClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("点击分享");
//                    }
//                })


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.btn_ttk, R2.id.tv_rule, R2.id.c_service, R2.id.c_news, R2.id.btn_share})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_ttk) {
            ChoseDropTypePopupUtils choseDropTypePopupUtils = new ChoseDropTypePopupUtils(MyClassDetailActivity.this);
            choseDropTypePopupUtils.showAnswerPopuPopu(btnTtk, classDetailBean.getObj().getOrder_id(), classDetailBean.getObj().getOrder_type() + "");
        } else if (i == R.id.tv_rule) {

            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", classDetailBean.getObj().getTk_protocol_link())
                    .navigation();

        } else if (i == R.id.c_service) {

            requestCallPhone();


        } else if (i == R.id.c_news) {
            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", classDetailBean.getObj().getArticle_list_link())
                    .navigation();
        } else if (i == R.id.btn_share) {
            ActivityCollector.getInstance().jumpToStackBottomActivity("com.xuesitu.activity.MainActivity");

            EventBus.getDefault().post(new Event<String>(1, "Token失效！"), EventBusTagUtils.MyClassDetailActivity);
        }
    }


    /**
     * 拨打电话
     */
    private void requestCallPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + classDetailBean.getObj().getService_hotline());
        intent.setData(data);
        startActivity(intent);

    }

//    @OnClick({R2.id.tv_order, R2.id.btn_share})
//    public void onViewClicked(View view) {
//        int i = view.getId();
//        if (i == R.id.tv_order) {
//        } else if (i == R.id.btn_share) {
//            ActivityCollector.getInstance().jumpToStackBottomActivity("com.xuesitu.activity.MainActivity");
//
//            EventBus.getDefault().post(new Event<String>(1, "Token失效！"), EventBusTagUtils.MyClassDetailActivity);
//        }
//    }


    private class HomeAdapter extends BaseAdapter<ClassDetailBean.ObjBean.CourseListBean> {

        public HomeAdapter(Context context, List<ClassDetailBean.ObjBean.CourseListBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_class_detail;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<ClassDetailBean.ObjBean.CourseListBean> mData) {


            holder.setText(R.id.tv_title, mData.get(position).getCourse_name());

            TextView tv_class_num = holder.getView(R.id.tv_class_num);
            TextView tv_price = holder.getView(R.id.tv_price);
            TextView tv_discount = holder.getView(R.id.tv_discount);
            TextView tv_time = holder.getView(R.id.tv_time);
            tv_class_num.setVisibility(View.VISIBLE);
            tv_price.setVisibility(View.VISIBLE);
            tv_discount.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);

            String totalClass = mData.get(position).getTotal_class() + "";

            String price = mData.get(position).getPrice() + "";

            String discount = mData.get(position).getDiscount() + "";

            String classTime = mData.get(position).getClass_time() + "";

            if (totalClass.equals("")) {
                tv_class_num.setVisibility(View.GONE);
            }

            if (price.equals("")) {
                tv_price.setVisibility(View.GONE);
            }

            if (discount.equals("")) {
                tv_discount.setVisibility(View.GONE);
            }

            if (tv_time.equals("")) {
                tv_time.setVisibility(View.GONE);
            }


            tv_class_num.setText("课时总数:" + totalClass);

            tv_price.setText("课时单价:" + price + "兔币");

            tv_discount.setText("折扣数:" + discount);

            tv_time.setText("课程时间:" + classTime);

        }
    }

}
