package com.xuesitu.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.xuesitu.R;

@Route(path = ARouterPathUtils.App_LaunchActivity)
public class LaunchActivity extends BaseAppActivity {
    @Override
    protected void onCreateView() {

        ARouter.getInstance().build(ARouterPathUtils.App_MainActivity).navigation();

        finish();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }
}
