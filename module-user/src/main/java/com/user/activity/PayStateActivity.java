package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_PayStateActivity)
public class PayStateActivity extends BaseAppActivity {

    @Autowired(name = "state")
    int state;
    @BindView(R2.id.iv_bg)
    ImageView ivBg;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.btn_start)
    Button btnStart;

    @Override
    protected void onCreateView() {

        initTitle();

        ARouter.getInstance().inject(this);

        if (state == 0) {
            //失败
            if (title != null) {
                title.setText("充值失败");
            }
            ivBg.setImageResource(R.mipmap.iv_no);
            tvContent.setText("充值失败,请稍后再试!");
            btnStart.setVisibility(View.GONE);


        } else {
            if (title != null) {
                title.setText("充值成功");
            }
            //成功
            ivBg.setImageResource(R.mipmap.iv_ok);
            tvContent.setText("恭喜你支付成功，快去学习!");
            btnStart.setText("开始学习");
            btnStart.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_state;
    }

    private TextView title;

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("充值")
                .builder();
        title = navigationBar.getTitleTextView();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.btn_start)
    public void onViewClicked() {

        finish();
    }
}
