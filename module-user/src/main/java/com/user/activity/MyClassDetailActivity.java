package com.user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;


@Route(path = ARouterPathUtils.User_MyClassDetailActivity)
public class MyClassDetailActivity extends BaseAppActivity {
    @Override
    protected void onCreateView() {
        initTitle();
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
}
