package com.xuesitu;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;

@Route(path = ARouterPathUtils.App_LaunchActivity)
public class LaunchActivity extends BaseAppActivity {
    @Override
    protected void onCreateView() {

        ARouter.getInstance().build(ARouterPathUtils.App_MainActivity).navigation();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }
}
