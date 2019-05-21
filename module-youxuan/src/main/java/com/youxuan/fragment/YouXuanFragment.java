package com.youxuan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.viewpager.my.ViewPagerForScrollView;
import com.lib.ui.fragment.BaseAppFragment;
import com.youxuan.R;
import com.youxuan.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.YouXuan_YouXuanFragment)
public class YouXuanFragment extends BaseAppFragment {


    @BindView(R2.id.rv_hudong)
    RecyclerView rvHudong;

    @BindView(R2.id.contentViewPager)
    ViewPagerForScrollView contentViewPager;


    @BindView(R2.id.lin_title)
    LinearLayout linTitle;

    private HomeAdapter homeAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {


        initView();

        initBottomViewPager();
    }

    private void initView() {


        //第一个互动

        carList.add("");
        carList.add("");
        homeAdapter = new HomeAdapter();
        rvHudong.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHudong.setAdapter(homeAdapter);




    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_youxuan;
    }

    private String schoolList[] = {"小学", "初中", "高中"};
    private static final int VIEW_TYPE = -1;
    private List<String> carList = new ArrayList<>();




    class HomeAdapter extends RecyclerView.Adapter<ViewHolder> {


        public HomeAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (viewType == VIEW_TYPE) {
                holder = ViewHolder.createViewHolder(getActivity(), parent, R.layout.item_hudong1);
            } else {
                holder = ViewHolder.createViewHolder(getActivity(), parent, R.layout.item_hudong1);
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            if (getItemViewType(position) == VIEW_TYPE) {
                return;
            } else {


            }
        }


        @Override
        public int getItemCount() {
            //返回集合的长度
            return carList.size() > 0 ? carList.size() : 1;
        }


        /**
         * 获取条目 View填充的类型
         * 默认返回0
         * 将lists为空返回-1
         *
         * @param position
         * @return
         */
        public int getItemViewType(int position) {
            if (carList.size() <= 0) {
                return VIEW_TYPE;
            }
            return super.getItemViewType(position);
        }

    }


    private DemandAdapter mDemandAdapter;

    private void initBottomViewPager() {
        //下方ViewPager
        fragments.clear();
        fragments.add(FragmentUtils.getSchoolFragment());
        fragments.add(FragmentUtils.getSchoolFragment());
        fragments.add(FragmentUtils.getSchoolFragment());

        initBottomTitle();


        contentViewPager.setOffscreenPageLimit(3);
        mDemandAdapter = new DemandAdapter(getActivity().getSupportFragmentManager());
        contentViewPager.setAdapter(mDemandAdapter);
        contentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    }

    private void initBottomTitle() {

        for (int i = 0; i < schoolList.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_title, null);

            final TextView tv_title=view.findViewById(R.id.tv_title);
            tv_title.setText(schoolList[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(DisplayUtil.dip2px(getActivity(), 16), 0, 0, 0);
            view.setLayoutParams(params);
            linTitle.addView(view);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showToast(finalI +"");

                }
            });


        }


    }


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


}
