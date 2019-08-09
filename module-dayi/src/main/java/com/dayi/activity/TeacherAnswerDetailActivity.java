package com.dayi.activity;

import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.AnswerDetail;
import com.dayi.bean.BaseHttpBean;
import com.dayi.bean.UploadVoice;
import com.dayi.utils.pop.AnswerQuestionPopupUtils;
import com.dayi.utils.pop.EvaluationTeacherPoupUtils;
import com.dayi.utils.pop.ZoomImagePopupUtils;
import com.dayi.view.CommonSoundItemView;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.lib.view.player.MyJzvdStd;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;


/**
 * 老师答案详情
 */
@Route(path = ARouterPathUtils.Dayi_TeacherAnswerDetailActivity)
public class TeacherAnswerDetailActivity extends BaseAppActivity {

    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @BindView(R2.id.lin_video)
    LinearLayout linVideo;
    @BindView(R2.id.lin_image)
    LinearLayout linImage;
    @BindView(R2.id.lin_voice)
    LinearLayout linVoice;
    @BindView(R2.id.lin_word)
    LinearLayout linWord;
    @BindView(R2.id.iv_head)
    ImageView ivHead;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_state)
    TextView tvState;
    @BindView(R2.id.tv_info)
    TextView tvInfo;
    @BindView(R2.id.tv_see)
    TextView tvSee;
    @BindView(R2.id.tv_answer_question)
    TextView tvAnswerQuestion;

    @Autowired(name = "replyId")
    String replyId;

    private String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=262644851,3907824053&fm=26&gp=0.jpg";


    @Override
    protected void onCreateView() {

        ARouter.getInstance().inject(this);

        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        stateView.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                initData();
            }
        });
        initTitle();
        initData();

        initEvaluationPopup();


    }

    private void initEvaluationPopup() {
        evaluationTeacherPoupUtils = new EvaluationTeacherPoupUtils(TeacherAnswerDetailActivity.this);
        evaluationTeacherPoupUtils.setEvaluationTeacherPoupUtilsListener(new EvaluationTeacherPoupUtils.EvaluationTeacherPoupUtilsListener() {
            @Override
            public void onSure(String correct, String praise) {


                evaluationTeacher(correct, praise);

            }

            @Override
            public void onComplaintsTeacher() {


                if (is_complaint == 1) {
                    //已经投诉
                    showToast("你已经投诉过了！");
                } else {
                    showComplaintPop(TeacherAnswerDetailActivity.this.getWindow().getDecorView(), 0);
                }


            }
        });
    }

    EvaluationTeacherPoupUtils evaluationTeacherPoupUtils;

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("老师回答")

                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        if (is_evaluate == 1) {
                            //已评价
                            finish();
                        } else {
                            evaluationTeacherPoupUtils.showAnswerPopuPopu(v);
                        }

                    }
                })
                .builder();


    }


    /**
     * 评价老师
     *
     * @param correct
     * @param praise
     */
    private void evaluationTeacher(String correct, String praise) {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_REPLY_EVALUATION")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("reply_id", replyId)
                .addParam("praise", praise)
                .addParam("correct", correct)
                .execute(new HttpNormalCallBack<BaseHttpBean>() {
                    @Override
                    public void onSuccess(BaseHttpBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {


                            evaluationTeacherPoupUtils.dismiss();
                            finish();
                        } else {
                            evaluationTeacherPoupUtils.dismiss();
                            finish();
                        }

                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_answer_detail;
    }


    //0-未投诉，1-已投诉
    private int is_complaint = 0;
    // 0-未评价，1-已评价
    private int is_evaluate = 0;

    private void initData() {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_REPLY_DETAILE")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("reply_id", replyId)
                .execute(new HttpNormalCallBack<AnswerDetail>() {
                    @Override
                    public void onSuccess(AnswerDetail result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            is_complaint = result.getObj().getIs_complaint();
                            is_evaluate = result.getObj().getIs_evaluate();
                            updateUI(result);

                            updateTeacherUI(result);

                            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        } else {
                            stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }

                    }


                    @Override
                    public void onError(String e) {
                        if (stateView!=null){
                            stateView.setViewState(MultiStateView.VIEW_STATE_NETWORK_ERROR);
                        }
                    }
                });

    }

    private void updateTeacherUI(final AnswerDetail result) {


        Glide.with(this)
                .load(result.getObj().getUser_icon())
                .apply(GlideConfig.getCircleOptions())
                .into(ivHead);

        tvTitle.setText(result.getObj().getUser_name());

        if (result.getObj().getIs_check() == 1) {
            tvState.setText("已认证");
        } else {
            tvState.setText("未认证");
            tvState.setTextColor(getResources().getColor(R.color.base_gray));
        }


        tvInfo.setText("准确率" + result.getObj().getCorrect()
                + "   好评率" + result.getObj().getPraise()
                + "   答题数" + result.getObj().getReply_total());


        tvSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ARouter.getInstance().build(ARouterPathUtils.Dayi_DayiNormalDetailWebActivity)

                        .withString("urlPath", result.getObj().getTeacher_link())

                        .navigation();


            }
        });
    }


    private void updateUI(AnswerDetail result) {


        int reply_type = result.getObj().getReply_type();

        if (reply_type == 0) {

            //视频
            List<String> videoList = result.getObj().getVideo();
            if (videoList.size() > 0) {
                for (String s : videoList) {
                    insertVideo(s);
                }
            }


        } else {
            //图片语音

            List<String> imageList = result.getObj().getImage();
            if (imageList.size() > 0) {
                for (String s : imageList) {
                    insertImage(s);
                }
            }


            List<String> voiceList = result.getObj().getVoice();


            if (voiceList.size() > 0) {
                for (String s : voiceList) {
                    instertVoice(s);
                }
            }


        }


    }


    private void insertImage(final String url) {
        final View itemImage = LayoutInflater.from(this).inflate(R.layout.item_answer_image, null);

        ImageView imageView = itemImage.findViewById(R.id.iv_image);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CardView cardView = itemImage.findViewById(R.id.card);

        params.width = DisplayUtil.getScreenWidth(this) / 2 - DisplayUtil.dip2px(this, 20);
        params.height = DisplayUtil.dip2px(this, 112);
        int margin = DisplayUtil.dip2px(this, 4);
        params.setMargins(margin, margin, margin, margin);
        cardView.setLayoutParams(params);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZoomImagePopupUtils zoomImagePopupUtils = new ZoomImagePopupUtils(TeacherAnswerDetailActivity.this);
                zoomImagePopupUtils.setZoomImage(url);
                zoomImagePopupUtils.showAnswerPopuPopu(v);
            }
        });


        Glide.with(this)
                .load(url)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);

        linImage.addView(itemImage);
    }


    private void instertVoice(String url) {

        UploadVoice uploadVoice = new UploadVoice();
        uploadVoice.setPlayUrl(url);
        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(this);
        commonSoundItemView.isLocalVoice(false);
        commonSoundItemView.setAudioEntity(uploadVoice);
        commonSoundItemView.isDletedAble(false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        params.topMargin = DisplayUtil.dip2px(this, 16);
        commonSoundItemView.setLayoutParams(params);

        linVoice.addView(commonSoundItemView);

    }

    /**
     * 插入视频
     */
    MyJzvdStd jz_player;

    private void insertVideo(String url) {

        View view = LayoutInflater.from(this).inflate(R.layout.item_video_temp, null);

        jz_player = view.findViewById(R.id.jz_player);

        jz_player.setUp(url
                , "");
        Glide.with(this).load(url).into(jz_player.thumbImageView);

        linVideo.addView(view);


    }


    @OnClick({R2.id.iv_head, R2.id.tv_answer_question})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_head) {


        } else if (i == R.id.tv_answer_question) {


            if (is_complaint == 1) {
                //已投诉

                showToast("你已经提交过问题了!");
            } else {

                showComplaintPop(view, 1);
            }

        }
    }

    private void showComplaintPop(View view, final int type) {


        AnswerQuestionPopupUtils answerQuestionPopupUtils = new AnswerQuestionPopupUtils(this);

        answerQuestionPopupUtils.setAnswerQuestionPopupUtilsListener(new AnswerQuestionPopupUtils.AnswerQuestionPopupUtilsListener() {
            @Override
            public void onSure(String content) {


                complaintTeacher(content, type);

            }
        });
        answerQuestionPopupUtils.showAnswerPopuPopu(view);

        if (type == 0) {
            answerQuestionPopupUtils.setContent("");

            answerQuestionPopupUtils.setTitle("投诉老师");
        } else {
            answerQuestionPopupUtils.setTitle("答案有问题?");
        }


    }


    private void complaintTeacher(String content, int type) {

        String complaint_content = "";
        String problem_content = "";

        if (type == 0) {
            complaint_content = content;
        } else {
            problem_content = content;
        }


        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_REPLY_COMPLAINT")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("reply_id", replyId)
                .addParam("complaint_content", complaint_content)
                .addParam("problem_content", problem_content)
                .execute(new HttpDialogCallBack<BaseHttpBean>() {
                    @Override
                    public void onSuccess(BaseHttpBean result) {


                        if (result.getCode() == CodeUtil.CODE_200) {


                        }

                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }


        if (is_evaluate == 1) {
            //已评价
            finish();
        } else {
            evaluationTeacherPoupUtils.showAnswerPopuPopu(this.getWindow().getDecorView());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
