package com.user.activity;

import android.app.Activity;
import android.content.Context;
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
    private List<ClassDetailBean.ObjBean> list = new ArrayList<>();

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
                            list.clear();
                            list.addAll(result.getObj());
                            homeAdapter.notifyDataSetChanged();
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
                .setRightIcon(R.mipmap.nav_share)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击分享");
                    }
                })
                .builder();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.tv_order, R2.id.btn_share})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_order) {
        } else if (i == R.id.btn_share) {
            ActivityCollector.getInstance().jumpToStackBottomActivity("com.xuesitu.activity.MainActivity");

            EventBus.getDefault().post(new Event<String>(1, "Token失效！"), EventBusTagUtils.MyClassDetailActivity);
        }
    }


    private class HomeAdapter extends BaseAdapter<ClassDetailBean.ObjBean> {

        public HomeAdapter(Context context, List<ClassDetailBean.ObjBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_class_detail;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<ClassDetailBean.ObjBean> mData) {


            holder.setText(R.id.tv_title, mData.get(position).getCourse_name());

            holder.setText(R.id.tv_class_num, "课时总数:" + mData.get(position).getTotal_class());

            holder.setText(R.id.tv_price, "课时单价:" + mData.get(position).getTotal_price() + "兔币");

            holder.setText(R.id.tv_discount, "折扣数:" + mData.get(position).getDiscount());
            holder.setText(R.id.tv_time, "课程时间:" + mData.get(position).getClass_time());

        }
    }

}
