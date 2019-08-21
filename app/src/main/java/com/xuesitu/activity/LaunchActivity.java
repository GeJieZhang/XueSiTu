package com.xuesitu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.status_bar.StatusBarUtil;
import com.lib.fastkit.views.viewpager.my.MyViewPager;
import com.lib.ui.activity.BaseAppActivity;
import com.xuesitu.R;
import com.xuesitu.activity.fragment.FragmentGuide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.App_LaunchActivity)
public class LaunchActivity extends BaseAppActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.btn_start)
    Button btnStart;
    private DemandAdapter mDemandAdapter;

    @Override
    protected void onCreateView() {
        StatusBarUtil.statusBarTintColor(this, getResources().getColor(R.color.white));


        for (int i = 0; i < 3; i++) {

            FragmentGuide fragmentGuide = new FragmentGuide(i + 1);

            fragments.add(fragmentGuide);

        }

        mDemandAdapter = new DemandAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mDemandAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setOffscreenPageLimit(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    /**
     * 适配器
     */
    private List<Fragment> fragments = new ArrayList<>();

    public class DemandAdapter extends FragmentPagerAdapter {


        public DemandAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        ARouter.getInstance().build(ARouterPathUtils.App_MainActivity).navigation();
        finish();

    }
}
