package com.lib.view.banner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lib.base.R;
import com.lib.bean.CustomData;
import com.lib.fastkit.utils.audio.AudioPlayManager;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.view.banner.holder.CustomViewHolder2;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;
import com.ms.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyBannerView extends LinearLayout {

    private View root;
    private int mIndicatorSelectedResId0 = R.mipmap.wheelbar_selected;
    private int mIndicatorUnselectedResId0 = R.mipmap.wheelbar_default;

    private int mIndicatorSelectedResId1 = R.mipmap.wheelpoint_selected;
    private int mIndicatorUnselectedResId1 = R.mipmap.wheelpoint_default;
    private List<ImageView> indicatorImages = new ArrayList<>();
    private List<CustomData> mList = new ArrayList<>();
    private int lastPosition = 0;

    public static int TYPE_CIRCLE = 1;
    public static int TYPE_RECTANGULAR = 0;
    private int TYPE = 0;

    private Context context;

    public MyBannerView(Context context) {
        this(context, null);
    }

    public MyBannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }


    private Banner banner;
    private LinearLayout indicator;

    private void initView(Context context) {
        this.context = context;

        root = LayoutInflater.from(context).inflate(R.layout.zj_banner, this);
        banner = root.findViewById(R.id.banner);
        indicator = root.findViewById(R.id.indicator);

    }


    public void setupdateData(List<CustomData> list, int style) {
        this.mList = list;
        this.TYPE = style;
        lastPosition = 0;
        initBanner();
        initIndicator();
    }


    private void initBanner() {

        banner.setAutoPlay(true)
                .setPages(mList, new CustomViewHolder2())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setBannerAnimation(Transformer.Scale)
                .setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(List datas, int position) {

                        if (listener != null) {
                            listener.onBannerClick(position);
                        }


                    }
                })
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if (TYPE == 0) {
                    indicatorImages.get((lastPosition + mList.size()) % mList.size()).setImageResource(mIndicatorUnselectedResId0);
                    indicatorImages.get((position + mList.size()) % mList.size()).setImageResource(mIndicatorSelectedResId0);
                } else {
                    indicatorImages.get((lastPosition + mList.size()) % mList.size()).setImageResource(mIndicatorUnselectedResId1);
                    indicatorImages.get((position + mList.size()) % mList.size()).setImageResource(mIndicatorSelectedResId1);
                }


                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {

        indicatorImages.clear();
        indicator.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams custom_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            custom_params.leftMargin = DisplayUtil.dip2px(context, 5);
            custom_params.rightMargin = DisplayUtil.dip2px(context, 5);
            if (i == 0) {

                if (TYPE == 0) {
                    imageView.setImageResource(mIndicatorSelectedResId0);
                } else {
                    imageView.setImageResource(mIndicatorSelectedResId1);
                }

            } else {

                if (TYPE == 0) {
                    imageView.setImageResource(mIndicatorUnselectedResId0);
                } else {
                    imageView.setImageResource(mIndicatorUnselectedResId1);
                }

            }
            indicatorImages.add(imageView);
            indicator.addView(imageView, custom_params);
        }
    }

    private MyBannerViewListener listener;

    public interface MyBannerViewListener {
        void onBannerClick(int positon);
    }

    public void setMyBannerViewListener(MyBannerViewListener myBannerViewListener) {
        this.listener = myBannerViewListener;
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if (visibility == View.VISIBLE) {
            //开始某些任务(可见)
            if (banner != null) {
                banner.startAutoPlay();
            }

        } else if (visibility == INVISIBLE || visibility == GONE) {
            //停止某些任务(不可见)

            if (banner != null) {
                banner.stopAutoPlay();
            }


        }
    }


}
