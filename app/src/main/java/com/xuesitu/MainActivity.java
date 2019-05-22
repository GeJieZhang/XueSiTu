package com.xuesitu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentUtils;
import com.lib.fastkit.ui.base.control.ActivityCollector;
import com.lib.fastkit.views.viewpager.my.MyViewPager;
import com.lib.ui.activity.BaseAppActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.App_MainActivity)
public class MainActivity extends BaseAppActivity {

    @BindView(R.id.mViewPager)
    MyViewPager mViewPager;
    @BindView(R.id.iv_youxuan)
    ImageView ivYouxuan;
    @BindView(R.id.tv_youxuan)
    TextView tvYouxuan;
    @BindView(R.id.lin_youxuan)
    LinearLayout linYouxuan;
    @BindView(R.id.iv_sixue)
    ImageView ivSixue;
    @BindView(R.id.tv_sixue)
    TextView tvSixue;
    @BindView(R.id.lin_sixue)
    LinearLayout linSixue;
    @BindView(R.id.iv_jianke)
    ImageView ivJianke;
    @BindView(R.id.tv_jianke)
    TextView tvJianke;
    @BindView(R.id.lin_jianke)
    LinearLayout linJianke;
    @BindView(R.id.iv_fengxiang)
    ImageView ivFengxiang;
    @BindView(R.id.tv_fengxiang)
    TextView tvFengxiang;
    @BindView(R.id.lin_fengxiang)
    LinearLayout linFengxiang;
    @BindView(R.id.iv_center)
    ImageView ivCenter;


    private DemandAdapter mDemandAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreateView() {

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();


    }

    private void initData() {

        fragments.clear();
        fragments.add(FragmentUtils.getYouXuanFragment());
        fragments.add(FragmentUtils.getSiXueFragment());
        fragments.add(FragmentUtils.getCenterFragment());
        fragments.add(FragmentUtils.getJianKeFragment());
        fragments.add(FragmentUtils.getFengXiangFragment());


    }


    private void initView() {

        //mViewPager=findViewById(R.id.mViewPager);
        mViewPager.setOffscreenPageLimit(4);
        mDemandAdapter = new DemandAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mDemandAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        initSelected(0);
    }


    @OnClick({R.id.lin_youxuan, R.id.lin_sixue, R.id.lin_jianke, R.id.lin_fengxiang, R.id.iv_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_youxuan:
                mViewPager.setCurrentItem(0);

                initSelected(0);
                break;
            case R.id.lin_sixue:
                mViewPager.setCurrentItem(1);
                initSelected(1);
                break;
            case R.id.iv_center:
                mViewPager.setCurrentItem(2);
                initSelected(2);
                break;
            case R.id.lin_jianke:
                mViewPager.setCurrentItem(3);
                initSelected(3);
                break;
            case R.id.lin_fengxiang:
                mViewPager.setCurrentItem(4);
                initSelected(4);
                break;

        }
    }


    private void initSelected(int position) {

        initBottomImage();

        switch (position) {
            case 0: {
                ivYouxuan.setImageResource(R.mipmap.icon_preferably_selected);
                tvYouxuan.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }

            case 1: {
                ivSixue.setImageResource(R.mipmap.icon_study_selected);
                tvSixue.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }

            case 2: {
                ivCenter.setImageResource(R.mipmap.look_selected);

                break;
            }

            case 3: {
                ivJianke.setImageResource(R.mipmap.icon_course_selected);
                tvJianke.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }
            case 4: {
                ivFengxiang.setImageResource(R.mipmap.icon_fenshare_selected);
                tvFengxiang.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }
        }


    }

    private void initBottomImage() {
        ivYouxuan.setImageResource(R.mipmap.icon_preferably_default);
        ivSixue.setImageResource(R.mipmap.icon_study_default);
        ivCenter.setImageResource(R.mipmap.look_default);
        ivJianke.setImageResource(R.mipmap.icon_course_default);
        ivFengxiang.setImageResource(R.mipmap.icon_fenshare_default);

        tvYouxuan.setTextColor(getResources().getColor(R.color.base_gray));
        tvSixue.setTextColor(getResources().getColor(R.color.base_gray));
        tvJianke.setTextColor(getResources().getColor(R.color.base_gray));
        tvFengxiang.setTextColor(getResources().getColor(R.color.base_gray));


    }


//    private void setItem() {
//        /**
//         * 一定要在设置适配器之后设置Icon
//         */
//        for (int i = 0; i < mToolbarTab.getTabCount(); i++) {
//            mToolbarTab.getTabAt(i).setCustomView(getTabView(i));
//        }
//    }
//
//    public View getTabView(int position) {
//        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
//        ImageView tab_image = view.findViewById(R.id.tab_image);
//        TextView tab_text = view.findViewById(R.id.tab_text);
//        tab_image.setImageResource(tabIconsDefault[position]);
//        tab_text.setText(tab_array[position]);
//        return view;
//    }


    /**
     * 适配器
     */
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

    private int count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


            count++;

            if (count > 1) {
                //  showFinishDialog();
                ActivityCollector.finishAll();
            } else {
                showToast("再按一次退出应用");

            }

            return true;
        }
        return true;
    }

}
