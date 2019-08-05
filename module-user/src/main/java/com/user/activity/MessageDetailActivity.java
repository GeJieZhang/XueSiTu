package com.user.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_MessageDetailActivity)
public class MessageDetailActivity extends BaseAppActivity {


    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.tv_time)
    TextView tvTime;

    @Override
    protected void onCreateView() {
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }


    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("官方推送")
                .builder();


    }

}
