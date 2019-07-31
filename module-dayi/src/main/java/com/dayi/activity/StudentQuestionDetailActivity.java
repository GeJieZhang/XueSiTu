package com.dayi.activity;

import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.BaseHttpBean;
import com.dayi.bean.PayAnswerBean;
import com.dayi.bean.PayStateBean;
import com.dayi.bean.QuestionDetail;
import com.dayi.bean.UploadVoice;
import com.dayi.fragment.child.QuestionFragment;
import com.dayi.fragment.child.TeacherListFragment;
import com.dayi.utils.pop.PayPopupUtils;
import com.dayi.utils.pop.RecordVoicePopupUtils;
import com.dayi.utils.pop.WriteWordPopupUtils;
import com.dayi.view.CommonSoundItemView;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.FragmentTag;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.lib.view.navigationbar.NomalNavigationBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 学生问题详情
 */
@Route(path = ARouterPathUtils.Dayi_StudentQuestionDetailActivity)
public class StudentQuestionDetailActivity extends BaseAppActivity {


    @BindView(R2.id.f_question_student)
    FrameLayout fQuestionStudent;
    @BindView(R2.id.f_teacher_list)
    FrameLayout fTeacherList;
    @BindView(R2.id.lin_add_describe)
    LinearLayout linAddDescribe;

    @BindView(R2.id.tv_add_describe)
    TextView tvAddDescribe;

    @BindView(R2.id.tv_money)
    TextView tvMoney;

    @BindView(R2.id.iv_add_describe)
    ImageView ivAddDescribe;

    @BindView(R2.id.state_view)
    MultiStateView stateView;
    @Autowired(name = "questionId")
    String questionId;


    @Override
    protected void onCreateView() {

        ARouter.getInstance().inject(this);
        initTitle();
        initQuestionFragment();
        initTeacherListFragment();
        initPayMoneyPopupUtils();
        initData();

        iniPayState();


    }

    private void iniPayState() {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_VISIT_STATUS")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("question_id", questionId)
                .execute(new HttpNormalCallBack<PayStateBean>() {
                    @Override
                    public void onSuccess(PayStateBean result) {


                        if (result.getCode() == CodeUtil.CODE_200) {
                            payPopupUtils.setPopupValue(result.getObj().getAccount() + "", result.getObj().getTotal() + "");
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }


    PayPopupUtils payPopupUtils;

    private void initPayMoneyPopupUtils() {
        payPopupUtils = new PayPopupUtils(this);
        payPopupUtils.setPayPopupUtilsListener(new PayPopupUtils.PayPopupUtilsListener() {
            @Override
            public void onSure() {

                requestPay();


            }

            @Override
            public void onRechargeClick() {
                //去充值页面

                ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
            }
        });

    }


    /**
     * 付费旁听
     */
    private void requestPay() {
        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_BUY_VISIT")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("question_id", questionId)
                .execute(new HttpDialogCallBack<PayAnswerBean>() {
                    @Override
                    public void onSuccess(PayAnswerBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            initData();
                        }
                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    //判断时补充描述true，还是确认提交false
    private boolean isAddDiscribe = true;

    private void isAddDiscribe(boolean b) {

        if (b) {
            ivAddDescribe.setImageResource(R.mipmap.icon_add_selected);
            tvAddDescribe.setText("补充描述");
            isAddDiscribe = true;

        } else {
            ivAddDescribe.setImageResource(R.mipmap.icon_submit);
            tvAddDescribe.setText("确认提交");
            isAddDiscribe = false;
        }

    }


    @OnClick({R2.id.lin_add_describe})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_add_describe) {
            if (isAddDiscribe) {
                String description = questionLocal.getDescription();
                if (!description.equals("")) {

                    addDescribeWord(view, description);

                } else {

                    addDescribeVoice(view);
                }
            } else {


                uploadDescrible();
            }


        }
    }

    private void addDescribeWord(View view, String description) {


        WriteWordPopupUtils writeWordPopupUtils = new WriteWordPopupUtils(this);

        writeWordPopupUtils.setWriteWordPopupUtilsListener(new WriteWordPopupUtils.WriteWordPopupUtilsListener() {
            @Override
            public void onVoiceIconClick() {

            }

            @Override
            public void onSendClick(String content) {

                wordContent = content;


                questionFragment.addWord(wordContent);

                isAddDiscribe(false);


            }
        });

        writeWordPopupUtils.setText(description);
        writeWordPopupUtils.showCmmtWordPop(view);


    }


    QuestionDetail.ObjBean.QuestionBean questionLocal;

    private void initData() {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_DETAILE")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("question_id", questionId)
                .execute(new HttpNormalCallBack<QuestionDetail>() {
                    @Override
                    public void onSuccess(QuestionDetail result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                            questionLocal = result.getObj().getQuestion();
                            wordContent = questionLocal.getDescription();
                            insertQuestionData(result.getObj().getQuestion());

                            insertAnserData(result.getObj().getReply_user_list());

                            initUIState(result.getObj().getQuestion());

                        } else {
                            stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }

                    }


                    @Override
                    public void onError(String e) {
                        stateView.setViewState(MultiStateView.VIEW_STATE_NETWORK_ERROR);
                    }
                });

    }

    private void initUIState(QuestionDetail.ObjBean.QuestionBean question) {

        tvMoney.setText(question.getCurrent_value() + "兔币");
        if (question.getIs_supplement() == 1) {
            //不能补充问题

            linAddDescribe.setVisibility(View.GONE);

        } else {
            //可以补充问题
            linAddDescribe.setVisibility(View.VISIBLE);
        }


        if (question.getIs_visit() == 1) {

            //显示支付蒙板

            teacherListFragment.isShowPayPage(true);

        } else {
            teacherListFragment.isShowPayPage(false);
        }


    }

    private void insertQuestionData(QuestionDetail.ObjBean.QuestionBean question) {

        if (questionFragment != null) {
            questionFragment.updateData(question);
        }


    }

    private void insertAnserData(List<QuestionDetail.ObjBean.ReplyUserListBean> reply_user_list) {

        if (teacherListFragment != null) {
            teacherListFragment.updateData(reply_user_list);

        }
    }

    TeacherListFragment teacherListFragment;

    private void initTeacherListFragment() {

        teacherListFragment = new TeacherListFragment();
        teacherListFragment.setTeacherListFragmentListener(new TeacherListFragment.TeacherListFragmentListener() {
            @Override
            public void onPayClick(View v) {
                payPopupUtils.showAnswerPopuPopu(v);
            }
        });

        FragmentCustomUtils.setFragment(this, R.id.f_teacher_list, teacherListFragment, FragmentTag.TeacherListFragment);
    }

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("我的提问")
                .builder();


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_question_detail;
    }


    /**
     * 初始化问题UI
     */
    private QuestionFragment questionFragment;

    private void initQuestionFragment() {
        questionFragment = new QuestionFragment();
        questionFragment.setQuestionFragmentListener(new QuestionFragment.QuestionFragmentListener() {
            @Override
            public void onWordClick() {

                if (!isAddDiscribe) {
                    addDescribeWord(StudentQuestionDetailActivity.this.getWindow().getDecorView(), wordContent);
                }


            }
        });
        FragmentCustomUtils.setFragment(this, R.id.f_question_student, questionFragment, FragmentTag.QuestionFragment);

    }


    //----------------------------------------------------------------------------补充问题

    private Map<String, UploadVoice> uploadVoiceMap = new HashMap<>();

    private String wordContent = "";
    private final int TYPE_VOICE = 2;

    private void uploadFile(final String compressPath, final int type) {

        showLog("上传文件路径" + compressPath);

        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(final String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (type == TYPE_VOICE) {
                            UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);
                            if (uploadVoice == null) {
                                uploadVoice = new UploadVoice();
                            }

                            uploadVoice.setUrl(s);
                            uploadVoice.setPlayUrl(SharedPreferenceManager.getInstance(StudentQuestionDetailActivity.this).getUserCache().getQiNiuUrl() + s);
                            uploadVoiceMap.put(compressPath, uploadVoice);

                            /**
                             * 成功之后显示确认提交按钮
                             */

                            isAddDiscribe(false);
                        }


                    }
                });
            }

            @Override
            public void onError(String e) {

                showLog("文件上传失败:" + e);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (type == TYPE_VOICE) {

                            showToast("语音录制失败！");
                            removeVoice(compressPath);

                            isAddDiscribe(true);
                        }
                    }
                });

            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(this).getUserCache().getQiNiuToken());


    }

    private void uploadDescrible() {


        String voice_description = "";
        int voiceI = 0;
        for (Map.Entry<String, UploadVoice> entry : uploadVoiceMap.entrySet()) {
            if (voiceI != 0) {
                voice_description += ",";
            }
            voice_description += entry.getValue().getUrl();
            voiceI++;
        }

        HttpUtils.with(StudentQuestionDetailActivity.this)
                .addParam("requestType", "QUESTION_SUPPLEMENT_QUESTION")
                .addParam("token", SharedPreferenceManager.getInstance(StudentQuestionDetailActivity.this).getUserCache().getUserToken())
                .addParam("question_id", questionId)
                .addParam("voice_supplement", voice_description)
                .addParam("text_supplement", wordContent)
                .execute(new HttpDialogCallBack<BaseHttpBean>() {
                    @Override
                    public void onSuccess(BaseHttpBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                        }
                        isAddDiscribe(true);
                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    /**
     * 补充语音说明
     *
     * @param view
     */
    private void addDescribeVoice(View view) {

        RecordVoicePopupUtils recordVoicePopupUtils = new RecordVoicePopupUtils(this);
        recordVoicePopupUtils.setRecordVoicePopupUtilsListener(new RecordVoicePopupUtils.RecordVoicePopupUtilsListener() {
            @Override
            public void onVoiceIconClick() {

            }

            @Override
            public void onRecordFinish(Uri audioPath, int duration) {

                insertVoice(audioPath.getPath(), duration);


            }
        });

        recordVoicePopupUtils.showVoicePopu(view);

    }


    private void insertVoice(String path, int duration) {
        UploadVoice uploadVoice = uploadVoiceMap.get(path);

        if (uploadVoice == null) {
            uploadVoice = new UploadVoice();
        }
        uploadVoice.setPath(path);
        uploadVoice.setDuration(duration);
        /**
         * 填充布局
         */
        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(this);

        commonSoundItemView.setCommonSoundItemViewListener(new CommonSoundItemView.CommonSoundItemViewListener() {
            @Override
            public void onDelete(UploadVoice uploadVoice) {

                removeVoice(uploadVoice.getPath());

                isAddDiscribe(true);

            }
        });
        commonSoundItemView.isLocalVoice(true);
        commonSoundItemView.isDletedAble(true);
        commonSoundItemView.setAudioEntity(uploadVoice);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = DisplayUtil.dip2px(this, 16);
        commonSoundItemView.setLayoutParams(params);


        //linVoice.addView(commonSoundItemView);

        questionFragment.addVoice(commonSoundItemView);

        /**
         * 填充数据到Map
         */
        uploadVoice.setView(commonSoundItemView);
        uploadVoiceMap.put(path, uploadVoice);


        /**
         * 上传文件
         */
        uploadFile(path, TYPE_VOICE);
    }


    /**
     * 通知QuestionFragment删除语音
     *
     * @param compressPath
     */
    private void removeVoice(String compressPath) {
        /**
         *上传失败后需要删除之前的布局
         */
        UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);
        if (uploadVoice == null) {
            uploadVoice = new UploadVoice();
        }

        View view = uploadVoice.getView();
        questionFragment.removeVoice(view);
        uploadVoiceMap.remove(compressPath);

    }

}
