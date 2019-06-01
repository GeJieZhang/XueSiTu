package com.user.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.ui.fragment.BaseAppFragment;
import com.user.R;
import com.user.R2;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalFragment extends BaseAppFragment {
    @BindView(R2.id.lin_transparent)
    LinearLayout linTransparent;
    @BindView(R2.id.iv_setting)
    ImageView ivSetting;
    @BindView(R2.id.iv_name)
    ImageView ivName;
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

    @BindView(R2.id.btn_recharge)
    Button btnRecharge;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }


    @OnClick({R2.id.lin_transparent, R2.id.iv_setting, R2.id.iv_name, R2.id.lin_class, R2.id.lin_question, R2.id.lin_evaluation, R2.id.lin_teacher, R2.id.lin_study, R2.id.lin_sign, R2.id.btn_recharge})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_transparent) {
            EventBus.getDefault().post(new Event<>(1, "隐藏个人中心"), EventBusTagUtils.PersonalFragment);

        } else if (i == R.id.iv_setting) {

            ARouter.getInstance().build(ARouterPathUtils.User_SetActivity).navigation();

        } else if (i == R.id.iv_name) {
        } else if (i == R.id.lin_class) {

            ARouter.getInstance().build(ARouterPathUtils.User_MyClassActivity).navigation();


        } else if (i == R.id.lin_question) {
        } else if (i == R.id.lin_evaluation) {
        } else if (i == R.id.lin_teacher) {
        } else if (i == R.id.lin_study) {
        } else if (i == R.id.lin_sign) {
        } else if (i == R.id.btn_recharge) {

            ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
        }
    }
}
