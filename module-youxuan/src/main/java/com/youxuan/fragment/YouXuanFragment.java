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
import com.bumptech.glide.Glide;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.FragmentUtils;
import com.lib.bean.CustomData;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.status_bar.QMUI.QMUIDisplayHelper;
import com.lib.fastkit.utils.system.SystemUtil;
import com.lib.fastkit.views.recyclerview.tool.MyLinearLayoutManager;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.fastkit.views.viewpager.my.ViewPagerForScrollView;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;
import com.ms.banner.listener.OnBannerClickListener;
import com.youxuan.R;
import com.youxuan.R2;
import com.youxuan.bean.YouXuanBean;
import com.youxuan.fragment.banner.CustomViewHolder2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = ARouterPathUtils.YouXuan_YouXuanFragment)
public class YouXuanFragment extends BaseAppFragment {


    @BindView(R2.id.rv_hudong)
    RecyclerView rvHudong;
    @BindView(R2.id.rv_wanfu)
    RecyclerView rvWanfu;
    @BindView(R2.id.contentViewPager)
    ViewPagerForScrollView contentViewPager;

    @BindView(R2.id.springView)
    SpringView springView;


    @BindView(R2.id.lin_title)
    LinearLayout linTitle;

    @BindView(R2.id.banner)
    Banner banner;

    @BindView(R2.id.indicator)
    LinearLayout indicator;
    @BindView(R2.id.indicator2)
    LinearLayout indicator2;
    private HomeAdapter homeAdapter;

    private WanFuAdapter wanFuAdapter;
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

        initData();

        initView();


        initBottomViewPager();
    }

    private void initData() {
        HttpUtils.with(getActivity())
                .post()
                .addParam("requestType", "OPTIMAL_PAGE_DATA")
                .execute(new HttpNormalCallBack<YouXuanBean>() {
                    @Override
                    public void onSuccess(YouXuanBean youXuanBean) {

                        if (youXuanBean.getCode() == CodeUtil.CODE_200) {
                            //设置Banner
                            setBannerData(youXuanBean);


                            setWanfuData(youXuanBean);


                        } else {
                            showToast(youXuanBean.getMsg());
                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    private void setWanfuData(YouXuanBean youXuanBean) {


        wanfuList.clear();
        wanfuList.addAll(youXuanBean.getObj().getEvening_course());

        wanFuAdapter.updateData(wanfuList);
        wanFuAdapter.notifyDataSetChanged();

    }


    public void setBannerData(YouXuanBean youXuanBean) {

        mList.clear();
        List<YouXuanBean.ObjBean.CarouselBean> list = youXuanBean.getObj().getCarousel();

        for (YouXuanBean.ObjBean.CarouselBean carouselBean : list) {
            mList.add(new CustomData(carouselBean.getHash(), carouselBean.getGroup(), false));
        }

      initBannerView();


    }

    private void initView() {


        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                initData();
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
            }
        });



        initWanFuView();
        //第一个互动

        carList.add("");
        carList.add("");
        homeAdapter = new HomeAdapter(getActivity(), carList);
        rvHudong.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        rvHudong.setNestedScrollingEnabled(false);
        rvHudong.setAdapter(homeAdapter);


    }

    private void initWanFuView() {


        wanFuAdapter = new WanFuAdapter(getActivity(), wanfuList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWanfu.setLayoutManager(linearLayoutManager);
        rvWanfu.setAdapter(wanFuAdapter);


    }

    private void initBannerView() {
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


    //-------------------------------------------------------------------------------------1对1互动课
    private List<String> carList = new ArrayList<>();

    class HomeAdapter extends BaseAdapter<String> {


        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_hudong1;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

        }

    }


    //-------------------------------------------------------------------------------------晚辅

    List<YouXuanBean.ObjBean.EveningCourseBean> wanfuList = new ArrayList<>();

    class WanFuAdapter extends BaseAdapter<YouXuanBean.ObjBean.EveningCourseBean> {


        public WanFuAdapter(Context context, List<YouXuanBean.ObjBean.EveningCourseBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_wanfu;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<YouXuanBean.ObjBean.EveningCourseBean> mData) {
            int screenWidth = QMUIDisplayHelper.getScreenWidth(getContext());

            LinearLayout linearLayout = holder.getView(R.id.lin_parent);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            params.width = screenWidth / 3;

            holder.setText(R.id.tv_name, mData.get(position).getName());
            holder.setText(R.id.tv_time, mData.get(position).getStart_date() + "-" + mData.get(position).getEnd_date());

            ImageView imageView = holder.getView(R.id.iv_image);

            Glide.with(getActivity())
                    .load(mData.get(position).getIcon())
                    .apply(GlideConfig.getRectangleOptions())
                    .into(imageView);


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
        View view = listTitle.get(position);
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
