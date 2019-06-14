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
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
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
    @BindView(R2.id.iv_head)
    ImageView ivHead;
    @BindView(R2.id.tv_name)
    TextView tvName;

    @BindView(R2.id.lin_userInfo)
    LinearLayout linUserInfo;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {


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
            , R2.id.lin_userInfo
    })
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
        } else if (i == R.id.lin_userInfo) {

            String identity = SharedPreferenceManager.getInstance(getContext()).getUserCache().getUserIdentity();

            if (identity.equals("1")) {
                ARouter.getInstance().build(ARouterPathUtils.User_TeacherUserInfoActivity).navigation();
            } else {
                ARouter.getInstance().build(ARouterPathUtils.User_StudentUserInfoActivity).navigation();
            }

        } else if (i == R.id.btn_recharge) {

            ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
        }
    }


}
