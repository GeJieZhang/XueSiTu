package com.dayi.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.fragment.banner.CustomViewHolder2;
import com.lib.bean.CustomData;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.progress.circle_progress.CircleProgress;
import com.lib.ui.fragment.BaseAppFragment;


import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;
import com.ms.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@Route(path = ARouterPathUtils.Dayi_DayiFragment)
public class DayiFragment extends BaseAppFragment {


    @BindView(R2.id.circle_progress_bar2)
    CircleProgress circleProgressBar2;

    @BindView(R2.id.lin_tuijian)
    LinearLayout linTuijian;
    @BindView(R2.id.lin_tuXun)
    LinearLayout linTuXun;
    @BindView(R2.id.banner)
    Banner banner;

    @BindView(R2.id.indicator)
    LinearLayout indicator;

    @BindView(R2.id.lin_video)
    LinearLayout linVideo;


    @BindView(R2.id.lin_message)
    LinearLayout linMessage;
    private String messge[] = {"推荐1", "推荐2", "推荐3"};
    private String tuJian[] = {"推荐1", "推荐2", "推荐3"};

    private String tuXun[] = {"孩子犯错家长只知道吼?别只痛快了嘴巴!", "孩子犯错家长只知道吼?别只痛快了嘴巴!", "孩子犯错家长只知道吼?别只痛快了嘴巴!"};
    private String video[] = {"《控制脾气》", "《保护自己》", "《学会思考》"};

    private int mIndicatorSelectedResId = R.mipmap.wheelbar_selected;
    private int mIndicatorUnselectedResId = R.mipmap.wheelbar_default;
    private List<ImageView> indicatorImages = new ArrayList<>();
    private List<CustomData> mList = new ArrayList<>();
    private int lastPosition = 0;


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        initView();

    }

    private void initView() {

        circleProgressBar2.setValue(8000);

        initMessge();


        initTuiJian();
        initTuxun();

        //轮播
        initBannerData();
        initIndicator();
        initBanner();

        //视频

        initVideo();


    }


//
//    private void setAnimation(final CircleProgress circleProgress, final int mProgressBar) {
//        ValueAnimator animator = ValueAnimator.ofInt(0, mProgressBar).setDuration(1000);
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                circleProgress.setProgress((int) valueAnimator.getAnimatedValue());
//
//
//            }
//        });
//        animator.start();
//    }


    private void initMessge() {
        linMessage.removeAllViews();
        for (String s : messge) {
            View root = LayoutInflater.from(getContext()).inflate(R.layout.item_messge, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
            lp.gravity = Gravity.CENTER;
            root.setLayoutParams(lp);
            linMessage.addView(root);
        }
    }

    private void initVideo() {
        linVideo.removeAllViews();
        for (String s : video) {
            View root = LayoutInflater.from(getContext()).inflate(R.layout.item_video1, null);
            TextView tv_title = root.findViewById(R.id.tv_title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.gravity = Gravity.CENTER;
            root.setLayoutParams(lp);

            tv_title.setText(s);


            linVideo.addView(root);
        }
    }

    private void initBannerData() {
        mList.add(new CustomData("", "CustomLayout", false));
        mList.add(new CustomData("", "Transformer", false));
        mList.add(new CustomData("", "Viewpager", false));
    }

    private void initBanner() {
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

    private void initTuxun() {
        linTuXun.removeAllViews();
        for (String s : tuXun) {
            View root = LayoutInflater.from(getContext()).inflate(R.layout.item_tuxun2, null);
            TextView tv_title = root.findViewById(R.id.tv_title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.gravity = Gravity.CENTER;
            root.setLayoutParams(lp);

            tv_title.setText(s);


            linTuXun.addView(root);
        }
    }

    private void initTuiJian() {
        linTuijian.removeAllViews();
        for (String s : tuJian) {
            View root = LayoutInflater.from(getContext()).inflate(R.layout.item_tuxun1, null);
            TextView tv_title = root.findViewById(R.id.tv_title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.gravity = Gravity.CENTER;
            root.setLayoutParams(lp);
            tv_title.setText(s);
            linTuijian.addView(root);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dayi;
    }


}
