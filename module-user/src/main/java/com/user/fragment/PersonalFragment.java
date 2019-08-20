package com.user.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.db.shared_prefrences.interfaces.UserCacheInterface;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.html.HtmlPathUtils;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.user.R;
import com.user.R2;
import com.user.bean.PersonInfoBean;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalFragment extends BaseAppFragment {

    String identity = "1";//1为老师
    @BindView(R2.id.iv_setting)
    ImageView ivSetting;
    @BindView(R2.id.iv_head)
    ImageView ivHead;
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_money)
    TextView tvMoney;

    @BindView(R2.id.iv_name)
    ImageView ivName;
    @BindView(R2.id.btn_recharge)
    Button btnRecharge;
    @BindView(R2.id.lin_userInfo)
    LinearLayout linUserInfo;
    @BindView(R2.id.lin_class)
    LinearLayout linClass;
    @BindView(R2.id.lin_question)
    LinearLayout linQuestion;
    @BindView(R2.id.lin_evaluation)
    LinearLayout linEvaluation;
    @BindView(R2.id.lin_teacher)
    LinearLayout linTeacher;
    @BindView(R2.id.lin_study)
    LinearLayout linStudy;
    @BindView(R2.id.lin_sign)
    LinearLayout linSign;
    @BindView(R2.id.lin_student_parent)
    LinearLayout linStudentParent;
    @BindView(R2.id.lin_teacher_question)
    LinearLayout linTeacherQuestion;
    @BindView(R2.id.lin_teacher_assistants)
    LinearLayout linTeacherAssistants;
    @BindView(R2.id.lin_teacher_write)
    LinearLayout linTeacherWrite;
    @BindView(R2.id.lin_teacher_class)
    LinearLayout linTeacherClass;
    @BindView(R2.id.lin_teacher_student)
    LinearLayout linTeacherStudent;
    @BindView(R2.id.lin_teacher_video)
    LinearLayout linTeacherVideo;
    @BindView(R2.id.lin_teacher_parent)
    LinearLayout linTeacherParent;
    @BindView(R2.id.lin_transparent)
    LinearLayout linTransparent;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {


        initView();


    }


    private void initView() {


    }

    PersonInfoBean personInfoBean;

    /**
     * 通过不通的身份显示页面
     */
    public void initUserInfo() {

        String token = SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserToken();

        if (!token.equals("")) {

            HttpUtils.with(getContext())
                    .addParam("requestType", "PERSONAL_INFO")
                    .addParam("token", token)
                    .execute(new HttpNormalCallBack<PersonInfoBean>() {
                        @Override
                        public void onSuccess(PersonInfoBean result) {


                            if (result.getCode() == CodeUtil.CODE_200) {
                                personInfoBean = result;
                                UserCacheInterface userCacheInterface = SharedPreferenceManager.getInstance(getContext()).getUserCache();
                                userCacheInterface.setUserIdentity(result.getObj().getIdentity() + "");
                                userCacheInterface.setUserMoney(result.getObj().getTotal() + "");
                                userCacheInterface.setUserHeadUrl(result.getObj().getPhoto_url());
                                userCacheInterface.setUserName(result.getObj().getName());
                                setIdentity();


                            }


                        }

                        @Override
                        public void onError(String e) {

                        }
                    });

        }


        setIdentity();


    }

    private void setIdentity() {
        UserCacheInterface userCacheInterface = SharedPreferenceManager.getInstance(getContext()).getUserCache();
        identity = userCacheInterface.getUserIdentity();

        tvName.setText(userCacheInterface.getUserName());

        tvMoney.setText("兔币:" + userCacheInterface.getUserMoney());
        Glide.with(this)
                .load(userCacheInterface.getUserHeadUrl())
                .apply(GlideConfig.getCircleOptions())
                .into(ivHead);


        if (identity.equals("1")) {
            //老师
            linTeacherParent.setVisibility(View.VISIBLE);
            linStudentParent.setVisibility(View.GONE);
            btnRecharge.setText("提现");

        } else {

            //学生
            linTeacherParent.setVisibility(View.GONE);
            linStudentParent.setVisibility(View.VISIBLE);
            btnRecharge.setText("充值");
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }


    @OnClick({R2.id.lin_transparent, R2.id.iv_setting, R2.id.iv_name, R2.id.lin_class, R2.id.lin_question, R2.id.lin_evaluation, R2.id.lin_teacher, R2.id.lin_study, R2.id.lin_sign, R2.id.btn_recharge
            , R2.id.lin_userInfo, R2.id.lin_teacher_class, R2.id.lin_teacher_question, R2.id.iv_work_desk
            , R2.id.iv_msg, R2.id.tv_money, R2.id.lin_teacher_assistants, R2.id.lin_teacher_video
    })
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_transparent) {
            EventBus.getDefault().post(new Event<>(1, "隐藏个人中心"), EventBusTagUtils.PersonalFragment);

        } else if (i == R.id.iv_setting) {
            //设置

            ARouter.getInstance().build(ARouterPathUtils.User_SetActivity).navigation();

        } else if (i == R.id.iv_name) {


        } else if (i == R.id.lin_class) {

            //学生-课程订单

            ARouter.getInstance().build(ARouterPathUtils.User_MyClassActivity).navigation();
        } else if (i == R.id.lin_question) {

            //学生-我的提问


            ARouter.getInstance().build(ARouterPathUtils.Dayi_StudentQuestionListActivity).navigation();

        } else if (i == R.id.lin_evaluation) {
            //学生-我的测评


        } else if (i == R.id.lin_teacher) {
            //学生-我的老师
            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.S_MyTeacher)
                    .navigation();

        } else if (i == R.id.lin_study) {

            //学生-学习历程


            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.S_Ourse)
                    .navigation();
        } else if (i == R.id.lin_sign) {
            //学生-签到有礼

            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.S_Sign_In)
                    .navigation();

        } else if (i == R.id.lin_userInfo) {


            //用户信息
            if (identity.equals("1")) {
                //老师-个人信息
                ARouter.getInstance().build(ARouterPathUtils.User_TeacherUserInfoActivity).navigation();
            } else {
                //学生-个人信息
                ARouter.getInstance().build(ARouterPathUtils.User_StudentUserInfoActivity).navigation();
            }

        } else if (i == R.id.btn_recharge) {

            if (identity.equals("1")) {
                //老师-提现
                ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                        .withString("urlPath", HtmlPathUtils.T_Withdrawal)
                        .navigation();
            } else {
                //学生-充值
                ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
            }

        } else if (i == R.id.iv_msg) {
            //系统消息
            ARouter.getInstance().build(ARouterPathUtils.User_MessageActivity).navigation();

        } else if (i == R.id.tv_money) {
            //消费记录
            ARouter.getInstance().build(ARouterPathUtils.User_RecordsActivity).navigation();

        } else if (i == R.id.lin_teacher_assistants) {
            //老师-教辅
            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.T_Assistant)
                    .navigation();


        } else if (i == R.id.lin_teacher_video) {
            //老师-录播课
            ARouter.getInstance().build(ARouterPathUtils.App_NormalDetailWebActivity)
                    .withString("urlPath", HtmlPathUtils.T_Video)
                    .navigation();


        } else if (i == R.id.lin_teacher_question) {
            //老师-答疑

            ARouter.getInstance().build(ARouterPathUtils.Dayi_TeacherQuestionListActivity).navigation();


        } else if (i == R.id.lin_teacher_class) {
            //老师-课程订单

            ARouter.getInstance().build(ARouterPathUtils.User_MyClassActivity).navigation();
        } else if (i == R.id.iv_work_desk) {
            //老师-工作台
            ARouter.getInstance().build(ARouterPathUtils.User_TeacherWorkbenchActivity).navigation();

        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            initUserInfo();

        }

    }

}
