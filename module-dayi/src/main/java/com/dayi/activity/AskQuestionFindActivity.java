package com.dayi.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dayi.R;
import com.dayi.R2;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学生提问后的搜索页面
 */
@Route(path = ARouterPathUtils.Dayi_AskQuestionFindActivity)
public class AskQuestionFindActivity extends BaseAppActivity {


    @BindView(R2.id.iv_gif)
    ImageView ivGif;
    @BindView(R2.id.tv_cut_time)
    TextView tvCutTime;
    @BindView(R2.id.btn_back)
    Button btnBack;
    @BindView(R2.id.btn_share)
    Button btnShare;
    @BindView(R2.id.rv)
    RecyclerView rv;


    @Override
    protected void onCreateView() {
        initTitle();
        initGif();

        initView();
        startTimer();

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("我的提问")
                .builder();


    }

    private List<String> list = new ArrayList<>();
    private HomeAdapter homeAdapter;

    private void initView() {

        list.add("");
        list.add("");
        list.add("");
        homeAdapter = new HomeAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(homeAdapter);


    }

    private void initGif() {
        Glide.with(this).load(R.drawable.find).listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof GifDrawable) {
                    //加载一次
                    ((GifDrawable) resource).setLoopCount(-1);
                }

                return false;
            }


        }).into(ivGif);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask_question_find;
    }


    @OnClick({R2.id.btn_back, R2.id.btn_share})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_back) {

            ARouter.getInstance().build(ARouterPathUtils.Dayi_StudentQuestionListActivity).navigation();

            finish();
        } else if (i == R.id.btn_share) {
        }
    }


    private class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_ask_question_find;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, List<String> mData) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {

                        ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherAnswerQuestionDetailActivity).navigation();
                    } else {

                        ARouter.getInstance().build(ARouterPathUtils.Dayi_StudentQuestionDetailActivity).navigation();
                    }

                }
            });

        }
    }


    //-----------------------------------------------------------------------------记时器

    /**
     * 初始化计时器用来更新倒计时
     */
    private Handler mainHandler = new Handler();


    private long recordTotalTime = 1000 * 60 * 10;

    private void startTimer() {
        mainHandler.postDelayed(runnable, 1000);
    }

    private void stopTimer() {
        mainHandler.removeCallbacks(runnable);

        recordTotalTime = 1000 * 60 * 10;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recordTotalTime -= 1000;
            updateTimerUI();

            mainHandler.postDelayed(runnable, 1000);
        }
    };

    private void updateTimerUI() {

        String string = TimeUtils.converLongTimeToStr(recordTotalTime);

        tvCutTime.setText("等待老师抢单:" + string);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}

