package com.xuesitu.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.xuesitu.R;

@Route(path = ARouterPathUtils.App_AboutActivity)
public class AboutActivity extends BaseAppActivity {


    @Override
    protected void onCreateView() {
        initTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("关于")
                .builder();


    }
}
