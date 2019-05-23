package com.user.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.user.R;

import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_StartActivity)
public class StartActivity extends BaseAppActivity {
    @Override
    protected void onCreateView() {

        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);


    }
}
