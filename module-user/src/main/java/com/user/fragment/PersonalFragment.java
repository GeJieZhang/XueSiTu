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
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.db.shared_prefrences.interfaces.UserCacheInterface;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.user.R;
import com.user.R2;

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

        identity = SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserIdentity();
        initView();

    }

    private void initView() {



    }


    private void initUserInfo() {


        UserCacheInterface userCacheInterface = SharedPreferenceManager.getInstance(getContext()).getUserCache();


        tvName.setText(userCacheInterface.getUserName());


        Glide.with(this)
                .load(userCacheInterface.getUserHeadUrl())
                .apply(GlideConfig.getCircleOptions())
                .into(ivHead);


        if (identity.equals("1")) {
            linTeacherParent.setVisibility(View.VISIBLE);
            linStudentParent.setVisibility(View.GONE);
            btnRecharge.setText("提现");

        } else {

            linTeacherParent.setVisibility(View.GONE);
            linStudentParent.setVisibility(View.VISIBLE);
            btnRecharge.setText("充值");
        }




    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }


    @OnClick({R2.id.lin_transparent, R2.id.iv_setting, R2.id.iv_name, R2.id.lin_class, R2.id.lin_question, R2.id.lin_evaluation, R2.id.lin_teacher, R2.id.lin_study, R2.id.lin_sign, R2.id.btn_recharge
            , R2.id.lin_userInfo, R2.id.lin_teacher_class
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

            //学生课程订单

            ARouter.getInstance().build(ARouterPathUtils.User_MyClassActivity).navigation();

        } else if (i == R.id.lin_teacher_class) {
            //老师的课程订单


        } else if (i == R.id.lin_question) {

            //学生我的提问


        } else if (i == R.id.lin_evaluation) {
            //学生我的测评


        } else if (i == R.id.lin_teacher) {
            //我的老师


        } else if (i == R.id.lin_study) {

            //学习历程
        } else if (i == R.id.lin_sign) {
            //签到有礼
        } else if (i == R.id.lin_userInfo) {


            //用户信息
            if (identity.equals("1")) {
                ARouter.getInstance().build(ARouterPathUtils.User_TeacherUserInfoActivity).navigation();
            } else {
                ARouter.getInstance().build(ARouterPathUtils.User_StudentUserInfoActivity).navigation();
            }

        } else if (i == R.id.btn_recharge) {


            if (identity.equals("1")) {
                showToast("提现功能开发中");
            } else {
                ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
            }

        }
    }


}
