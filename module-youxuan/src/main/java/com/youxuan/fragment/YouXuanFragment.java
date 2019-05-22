package com.youxuan.fragment;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.tool.MyLinearLayoutManager;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.viewpager.my.ViewPagerForScrollView;
import com.lib.ui.fragment.BaseAppFragment;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;
import com.ms.banner.listener.OnBannerClickListener;
import com.youxuan.R;
import com.youxuan.R2;
import com.youxuan.fragment.banner.CustomData;
import com.youxuan.fragment.banner.CustomViewHolder2;

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

    @BindView(R2.id.banner)
    Banner banner;

    @BindView(R2.id.indicator)
    LinearLayout indicator;
    @BindView(R2.id.indicator2)
    LinearLayout indicator2;
    private HomeAdapter homeAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    private List<CustomData> mList = new ArrayList<>();
    private List<ImageView> indicatorImages = new ArrayList<>();
    private List<ImageView> indicatorImages2 = new ArrayList<>();
    private int mIndicatorSelectedResId = R.mipmap.wheelpoint_selected;
    private int mIndicatorUnselectedResId = R.mipmap.wheelpoint_default;

    private int mIndicatorSelectedResId2 = R.mipmap.wheelbar_selected;
    private int mIndicatorUnselectedResId2 = R.mipmap.wheelbar_default;
    private int lastPosition = 0;
    private int lastPosition2 = 0;
    private String schoolList[] = {"小学", "初中", "高中"};

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {


        initView();

        initBottomViewPager();
    }


    private void initView() {
        //banner

        mList.add(new CustomData("", "CustomLayout", false));
        mList.add(new CustomData("", "Transformer", false));
        mList.add(new CustomData("", "Viewpager", false));

        initIndicator();
        banner.setAutoPlay(true)
                .setPages(mList, new CustomViewHolder2())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setBannerAnimation(Transformer.Scale)
                .setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(List datas, int position) {
                        showToast(position + "");

                    }
                })
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorImages.get((lastPosition + mList.size()) % mList.size()).setImageResource(mIndicatorUnselectedResId);
                indicatorImages.get((position + mList.size()) % mList.size()).setImageResource(mIndicatorSelectedResId);
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //第一个互动

        carList.add("");
        carList.add("");
        homeAdapter = new HomeAdapter();
        rvHudong.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        rvHudong.setNestedScrollingEnabled(false);
        rvHudong.setAdapter(homeAdapter);


    }

    private void initIndicator() {
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams custom_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            custom_params.leftMargin = DisplayUtil.dip2px(getActivity(), 5);
            custom_params.rightMargin = DisplayUtil.dip2px(getActivity(), 5);
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            indicator.addView(imageView, custom_params);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_youxuan;
    }


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
        initIndicator2();
        contentViewPager.setOffscreenPageLimit(2);
        mDemandAdapter = new DemandAdapter(getActivity().getSupportFragmentManager());
        contentViewPager.setAdapter(mDemandAdapter);
        contentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int size = schoolList.length;
                indicatorImages2.get((lastPosition2 + size) % size).setImageResource(mIndicatorUnselectedResId2);
                indicatorImages2.get((position + size) % size).setImageResource(mIndicatorSelectedResId2);
                lastPosition2 = position;


                initTitle(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initTitle(0);

    }


    private List<View> listTitle = new ArrayList<>();

    private void initBottomTitle() {
        listTitle.clear();
        for (int i = 0; i < schoolList.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_title, null);
            listTitle.add(view);
            final TextView tv_title = view.findViewById(R.id.tv_title);

            final View v_circle = view.findViewById(R.id.v_circle);
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



                    initTitle(finalI);





                }


            });


        }


    }

    private void initTitle(int position) {

        if (listTitle.size() > 0) {
            for (View view : listTitle) {

                TextView tv_title = view.findViewById(R.id.tv_title);
                View v_circle = view.findViewById(R.id.v_circle);
                v_circle.setVisibility(View.GONE);
                tv_title.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            }
        }



        contentViewPager.setCurrentItem(position);
        View view=listTitle.get(position);
        TextView tv_title = view.findViewById(R.id.tv_title);
        View v_circle = view.findViewById(R.id.v_circle);
        v_circle.setVisibility(View.VISIBLE);
        tv_title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);


    }

    private void initIndicator2() {
        for (int i = 0; i < schoolList.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams custom_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            custom_params.leftMargin = DisplayUtil.dip2px(getActivity(), 5);
            custom_params.rightMargin = DisplayUtil.dip2px(getActivity(), 5);
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId2);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId2);
            }
            indicatorImages2.add(imageView);
            indicator2.addView(imageView, custom_params);
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

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
