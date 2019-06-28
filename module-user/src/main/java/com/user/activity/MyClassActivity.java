package com.user.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
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
import com.user.bean.ClassBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = ARouterPathUtils.User_MyClassActivity)
public class MyClassActivity extends BaseAppActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.springView)
    SpringView springView;


    private List<ClassBean.ObjBean.RowsBean> mData = new ArrayList<>();


    private HomeAdapter homeAdapter;

    private String token = "";

    private int PAY_STATE_N = 0;//未支付
    private int PAY_STATE_Y = 1;//已支付

    private int LIVE_STATE_N = 0;//未直播
    private int LIVE_STATE_Y = 1;//直播中

    private int CLASS_TYPE0 = 0;//晚陪课
    private int CLASS_TYPE1 = 1;//1对1
    private int CLASS_TYPE2 = 2;//1对多
    private int CLASS_TYPE3 = 3;//1对1体验课

    @Override
    protected void onCreateView() {
        token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


        initTitle();
        homeAdapter = new HomeAdapter(this, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(homeAdapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                page = 0;
                initData();

                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {

                page++;
                initData();
                springView.onFinishFreshAndLoad();
            }
        });


        initData();
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("我的课程")
                .builder();

        // navigationBar.getRightTextView()
    }


    private int page = 0;

    private void initData() {


        HttpUtils.with(this)
                .addParam("requestType", "ORDER_QUERY_LIST")
                .addParam("page", page)
                .addParam("limit", 10)
                .addParam("token", token)
                .execute(new HttpNormalCallBack<ClassBean>() {
                    @Override
                    public void onSuccess(ClassBean result) {


                        if (page == 0) {
                            mData.clear();

                            mData.addAll(result.getObj().getRows());

                            homeAdapter.notifyDataSetChanged();

                        } else {
                            mData.addAll(result.getObj().getRows());
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
        return R.layout.activity_my_class;
    }


    public class HomeAdapter extends BaseAdapter<ClassBean.ObjBean.RowsBean> {

        public HomeAdapter(Context context, List<ClassBean.ObjBean.RowsBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_my_class;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<ClassBean.ObjBean.RowsBean> mData) {


            holder.setText(R.id.tv_title, mData.get(position).getName());

            holder.setText(R.id.tv_class, "剩余课次:" + mData.get(position).getConsume_class());


            TextView tv_state = holder.getView(R.id.tv_state);
            ImageView iv_sate = holder.getView(R.id.iv_state);
            if (mData.get(position).getLive_status() == LIVE_STATE_Y) {

                tv_state.setText("正在开课");
                tv_state.setTextColor(getResources().getColor(R.color.base_purple));
                iv_sate.setImageResource(R.mipmap.icon_class_on1);

            } else {
                tv_state.setText("未开课");

                tv_state.setTextColor(getResources().getColor(R.color.base_gray));
                iv_sate.setImageResource(R.mipmap.icon_class_off1);
            }


            Button btn_go_class = holder.getView(R.id.btn_go_class);
            if (mData.get(position).getPay_status() == PAY_STATE_Y) {

                btn_go_class.setBackgroundResource(R.drawable.bg_part_circle1);
                btn_go_class.setText("去上课");

            } else {

                btn_go_class.setBackgroundResource(R.drawable.bg_part_circle1_red);
                btn_go_class.setText("待支付");
            }

            btn_go_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(position).getPay_status() == PAY_STATE_Y) {

                        //支付了去上课


                    } else {
                        //没有支付跳转网页支付

                    }
                }
            });


            holder.getView(R.id.btn_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ARouter.getInstance().build(ARouterPathUtils.User_MyClassDetailActivity).navigation();

                }
            });
        }
    }


}
