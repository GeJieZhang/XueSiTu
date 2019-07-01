package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.bean.ClassBean;
import com.user.bean.ClassDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_MyClassDetailActivity)
public class MyClassDetailActivity extends BaseAppActivity {

    @Autowired(name = "id")
    String id;
    @Autowired(name = "type")
    String type;

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_order)
    TextView tvOrder;
    @BindView(R2.id.tv_class_num)
    TextView tvClassNum;
    @BindView(R2.id.tv_price)
    TextView tvPrice;
    @BindView(R2.id.tv_discount)
    TextView tvDiscount;
    @BindView(R2.id.btn_share)
    Button btnShare;

    private String token = "";

    @Override
    protected void onCreateView() {
        ARouter.getInstance().inject(this);
        token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


        initTitle();

        initData();
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


                        tvTitle.setText(result.getObj().get(0).getCourse_name());

                        tvOrder.setText(result.getObj().get(0).getOrder_id());

                        tvClassNum.setText("课时总数:" + result.getObj().get(0).getTotal_class());

                        tvPrice.setText("课程单价:" + result.getObj().get(0).getPrice());

                        tvDiscount.setText("折扣数:" + result.getObj().get(0).getDiscount());
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

        // navigationBar.getRightTextView()
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
