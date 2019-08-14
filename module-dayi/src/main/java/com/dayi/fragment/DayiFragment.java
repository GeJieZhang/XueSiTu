package com.dayi.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.DayiBean;
import com.dayi.bean.ToLiveBeanDaYi;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.bean.CustomData;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.progress.circle_progress.CircleProgress;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.html.HtmlPathUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.lib.view.banner.MyBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;


@Route(path = ARouterPathUtils.Dayi_DayiFragment)
public class DayiFragment extends BaseAppFragment {


    @BindView(R2.id.circle_progress_bar2)
    CircleProgress circleProgressBar2;
    @BindView(R2.id.lin_message)
    LinearLayout linMessage;
    @BindView(R2.id.btn_camera)
    Button btnCamera;
    @BindView(R2.id.btn_voice)
    Button btnVoice;
    @BindView(R2.id.btn_write)
    Button btnWrite;
    @BindView(R2.id.lin_tuijian)
    LinearLayout linTuijian;
    @BindView(R2.id.lin_tuXun)
    LinearLayout linTuXun;
    @BindView(R2.id.myBanner)
    MyBannerView myBanner;

    @BindView(R2.id.myBanner2)
    MyBannerView myBanner2;
    @BindView(R2.id.indicator)
    LinearLayout indicator;
    @BindView(R2.id.lin_video)
    LinearLayout linVideo;
    @BindView(R2.id.iv_video)
    ImageView ivVideo;
    @BindView(R2.id.springView)
    SpringView springView;
    @BindView(R2.id.state_view)
    MultiStateView stateView;
    private String video[] = {"《控制脾气》", "《保护自己》", "《学会思考》"};


    private List<CustomData> mListTeacher = new ArrayList<>();
    private List<CustomData> mListClass = new ArrayList<>();


    private int lastPosition = 0;


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        stateView.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                initData();
            }
        });
        initView();

    }

    private void initView() {

        circleProgressBar2.setValue(8000);
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                initData();

            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
            }
        });

        initData();


    }

    private void initData() {

        HttpUtils.with(getContext())
                .addParam("requestType", "RABBIT_PAGE_DATA")
                .execute(new HttpNormalCallBack<DayiBean>() {
                    @Override
                    public void onSuccess(final DayiBean result) {
                        springView.onFinishFreshAndLoad();
                        if (result.getCode() == CodeUtil.CODE_200) {


                            /**
                             * 视频
                             */

                            Glide.with(getContext())
                                    .load(result.getObj().getQuestion_introduction())
                                    .apply(GlideConfig.getRoundOptions(10))
                                    .into(ivVideo);

                            ivVideo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JzvdStd.startFullscreenDirectly(getContext(), JzvdStd.class, result.getObj().getQuestion_introduction(), "");
                                }
                            });


                            /**
                             * 消息列表
                             */
                            List<DayiBean.ObjBean.RecentQuestionBean> getRecent_question = result.getObj().getRecent_question();
                            initMessageList(getRecent_question);

                            /**
                             * 兔讯
                             */
                            List<DayiBean.ObjBean.ArticleRabbitListBean> getArticle_rabbit_list = result.getObj().getArticle_rabbit_list();


                            iniTuXun(getArticle_rabbit_list);

                            /**
                             * 新闻
                             */
                            List<DayiBean.ObjBean.ArticleListBean> getArticle_list = result.getObj().getArticle_list();
                            initArticle(getArticle_list);

                            /**
                             * 名师专栏
                             */
                            List<DayiBean.ObjBean.FamousTeacherListBean> getFamous_teacher_list = result.getObj().getFamous_teacher_list();
                            initTeacherBanner(getFamous_teacher_list);

                            /**
                             * 公开课轮播
                             */
                            List<DayiBean.ObjBean.PublicClassCarouselBean> getPublic_class_carousel = result.getObj().getPublic_class_carousel();
                            initPublicClassBanner(getPublic_class_carousel);


                            /**
                             * 公开课
                             */

                            List<DayiBean.ObjBean.PublicClassListBean> getPublic_class_list = result.getObj().getPublic_class_list();

                            initPublicClass(getPublic_class_list);

                            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);


                        } else {
                            stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }


                    }

                    @Override
                    public void onError(String e) {
                        springView.onFinishFreshAndLoad();

                        if (stateView != null) {
                            stateView.setViewState(MultiStateView.VIEW_STATE_NETWORK_ERROR);
                        }
                    }
                });


    }


    /**
     * 公开课
     *
     * @param getPublic_class_list
     */
    private void initPublicClass(List<DayiBean.ObjBean.PublicClassListBean> getPublic_class_list) {
        linVideo.removeAllViews();
        for (DayiBean.ObjBean.PublicClassListBean publicClassListBean : getPublic_class_list) {
            insertVideo(publicClassListBean);
        }


    }

    private void iniTuXun(List<DayiBean.ObjBean.ArticleRabbitListBean> getArticle_rabbit_list) {
        linTuijian.removeAllViews();
        for (DayiBean.ObjBean.ArticleRabbitListBean articleRabbitListBean : getArticle_rabbit_list) {

            insertTuXun(articleRabbitListBean);

        }


    }


    /**
     * 公开课轮播
     *
     * @param getPublic_class_carousel
     */
    private void initPublicClassBanner(final List<DayiBean.ObjBean.PublicClassCarouselBean> getPublic_class_carousel) {
        mListClass.clear();
        for (DayiBean.ObjBean.PublicClassCarouselBean publicClassCarouselBean : getPublic_class_carousel) {
            CustomData customData = new CustomData(publicClassCarouselBean.getCover_url(), publicClassCarouselBean.getCover_url(), "", false);


            mListClass.add(customData);
        }

        myBanner.setupdateData(mListClass, MyBannerView.TYPE_RECTANGULAR);

        myBanner.setMyBannerViewListener(new MyBannerView.MyBannerViewListener() {
            @Override
            public void onBannerClick(int positon) {

                goToClass(getPublic_class_carousel.get(positon).getCourse_type() + "", getPublic_class_carousel.get(positon).getCourse_id() + "");


            }
        });

    }


    private void goToClass(final String course_type, final String course_id) {


        PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {


                //type:0晚陪课，1一对一，2班级课，3体验课
                //支付了去上课
                HttpUtils.with(getContext())
                        .addParam("requestType", "TO_CLASS")
                        .addParam("course_type", course_type)
                        .addParam("course_id", course_id)
                        .addParam("token", SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserToken())
                        .execute(new HttpDialogCallBack<ToLiveBeanDaYi>() {
                            @Override
                            public void onSuccess(final ToLiveBeanDaYi result) {

                                if (result.getCode() == CodeUtil.CODE_200) {


                                    String roomToken = result.getObj().getRoomtoken();
                                    String teacherPhone = result.getObj().getPhoen();
                                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                                            .withString("roomToken", roomToken)
                                            .withString("teacherPhone", teacherPhone)
                                            .withString("roomName", result.getObj().getRoomname())
                                            .withString("userPhone", SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserPhone())
                                            .withString("uuid", result.getObj().getUuid())
                                            .withString("whitetoken", result.getObj().getWhitetoken())
                                            .navigation();


                                } else {
                                    showToast(result.getMsg());
                                }

                            }

                            @Override
                            public void onError(String e) {

                            }
                        });


            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });

    }

    private void initTeacherBanner(final List<DayiBean.ObjBean.FamousTeacherListBean> getFamous_teacher_list) {
        mListTeacher.clear();
        for (DayiBean.ObjBean.FamousTeacherListBean famousTeacherListBean : getFamous_teacher_list) {
            CustomData customData = new CustomData(famousTeacherListBean.getTeacher_icon(), famousTeacherListBean.getTeacher_link(), "", false);

            mListTeacher.add(customData);
        }


        myBanner2.setupdateData(mListTeacher, MyBannerView.TYPE_RECTANGULAR);

        myBanner2.setMyBannerViewListener(new MyBannerView.MyBannerViewListener() {
            @Override
            public void onBannerClick(int positon) {

                String url = getFamous_teacher_list.get(positon).getTeacher_link();
                ARouter.getInstance().build(ARouterPathUtils.Dayi_DayiNormalDetailWebActivity)
                        .withString("urlPath", url)
                        .navigation();
            }
        });


    }

    private void initArticle(List<DayiBean.ObjBean.ArticleListBean> getArticle_list) {
        linTuXun.removeAllViews();


        for (DayiBean.ObjBean.ArticleListBean articleListBean : getArticle_list) {


            insertArticle(articleListBean);
        }


    }

    private void initMessageList(List<DayiBean.ObjBean.RecentQuestionBean> getRecent_question) {
        linMessage.removeAllViews();


        for (DayiBean.ObjBean.RecentQuestionBean recentQuestionBean : getRecent_question) {
            insertMessage(recentQuestionBean);
        }

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


    private void insertMessage(DayiBean.ObjBean.RecentQuestionBean recentQuestionBean) {


        View root = LayoutInflater.from(getContext()).inflate(R.layout.item_messge, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
        lp.gravity = Gravity.CENTER;
        root.setLayoutParams(lp);
        linMessage.addView(root);

        ImageView iv_head = root.findViewById(R.id.iv_head);
        TextView tv_content = root.findViewById(R.id.tv_content);
        TextView tv_time = root.findViewById(R.id.tv_time);

        tv_content.setText(recentQuestionBean.getMsg());
        tv_time.setText(recentQuestionBean.getCreate_date());
    }

    private void insertVideo(final DayiBean.ObjBean.PublicClassListBean publicClassListBean) {


        View root = LayoutInflater.from(getContext()).inflate(R.layout.item_video1, null);
        TextView tv_title = root.findViewById(R.id.tv_title);

        ImageView iv_cover = root.findViewById(R.id.iv_cover);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        lp.gravity = Gravity.CENTER;
        root.setLayoutParams(lp);
        tv_title.setText(publicClassListBean.getTitle());

        Glide.with(getContext())
                .load(publicClassListBean.getCover_url())
                .apply(GlideConfig.getRoundOptions(10))
                .into(iv_cover);

        linVideo.addView(root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClass(publicClassListBean.getCourse_type() + "", publicClassListBean.getCourse_id() + "");
            }
        });


    }


    private void insertArticle(final DayiBean.ObjBean.ArticleListBean articleListBean) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.item_tuxun2, null);
        TextView tv_title = root.findViewById(R.id.tv_title);
        TextView tv_watch = root.findViewById(R.id.tv_watch);
        ImageView iv_cover = root.findViewById(R.id.iv_cover);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        lp.gravity = Gravity.CENTER;
        root.setLayoutParams(lp);
        tv_title.setText(articleListBean.getTitle());
        tv_watch.setText("浏览次数:" + articleListBean.getAccess());
        Glide.with(getContext())
                .load(articleListBean.getCover())
                .apply(GlideConfig.getRoundOptions(10))
                .into(iv_cover);
        linTuXun.addView(root);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ARouter.getInstance().build(ARouterPathUtils.Dayi_DayiNormalDetailWebActivity)
                        .withString("urlPath", articleListBean.getArtic_link())
                        .navigation();

            }
        });

    }

    private void insertTuXun(final DayiBean.ObjBean.ArticleRabbitListBean tuxun) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.item_tuxun1, null);

        ImageView iv_cover = root.findViewById(R.id.iv_cover);
        TextView tv_title = root.findViewById(R.id.tv_title);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        lp.gravity = Gravity.CENTER;
        root.setLayoutParams(lp);
        tv_title.setText(tuxun.getTitle());
        Glide.with(getContext())
                .load(tuxun.getCover())
                .apply(GlideConfig.getRoundOptions(10))
                .into(iv_cover);
        linTuijian.addView(root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ARouter.getInstance().build(ARouterPathUtils.Dayi_DayiNormalDetailWebActivity)
                        .withString("urlPath", tuxun.getArtic_link())
                        .navigation();

            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dayi;
    }


//    @OnClick({R2.id.btn_camera, R2.id.btn_voice, R2.id.btn_write})
//    public void onViewClicked(View view) {
//        int i = view.getId();
//        if (i == R.id.btn_camera) {
//            goAskQuestion();
//        } else if (i == R.id.btn_voice) {
//            goAskQuestion();
//        } else if (i == R.id.btn_write) {
//            goAskQuestion();
//        }
//    }

    private void goAskQuestion() {

        PermissionUtil.getInstance(getActivity()).externalAudio(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {

                ARouter.getInstance().build(ARouterPathUtils.Dayi_AskQuestionActivity, GroupUtils.NEED_LOGIN).navigation();


            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });


    }


    @OnClick({R2.id.btn_camera, R2.id.btn_voice, R2.id.btn_write, R2.id.btn_fun, R2.id.btn_difficult
            , R2.id.btn_paper, R2.id.btn_analysis, R2.id.btn_mistake
            , R2.id.lin_search
    })
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_camera) {
            goAskQuestion();
        } else if (i == R.id.btn_voice) {
            goAskQuestion();
        } else if (i == R.id.btn_write) {
            goAskQuestion();
        } else if (i == R.id.btn_fun) {
            ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.APP_Fun_Topic)
                    .navigation();

        } else if (i == R.id.btn_difficult) {
            ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.APP_Difficult_Topic)
                    .navigation();
        } else if (i == R.id.btn_paper) {
            ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.APP_Paper_Topic)
                    .navigation();
        } else if (i == R.id.btn_analysis) {
        } else if (i == R.id.btn_mistake) {

            ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.APP_Mistake_Topic)
                    .navigation();
        } else if (i == R.id.lin_search) {

            ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.APP_Article_Search)
                    .navigation();
        }


    }
}
