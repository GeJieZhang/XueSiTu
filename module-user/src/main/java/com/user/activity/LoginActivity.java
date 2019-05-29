package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.timer_countdown.CountDownTimer;
import com.lib.ui.activity.BaseAppActivity;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.User_LoginActivity)
public class LoginActivity extends BaseAppActivity {
    @BindView(R2.id.btn_code)
    Button btnCode;
    @BindView(R2.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreateView() {

//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnCode.setEnabled(true);


            btnCode.setBackgroundResource(R.drawable.bg_circle_gradient_r10);
            btnCode.setTextColor(getResources().getColor(R.color.base_text1));
            btnCode.setText("获取验证码");


        }
    };


    @OnClick({R2.id.btn_code, R2.id.btn_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_code) {

            btnCode.setEnabled(false);

            btnCode.setBackgroundResource(R.drawable.bg_circle_hollow_r10);
            btnCode.setTextColor(getResources().getColor(R.color.base_text1));
            timer.start();

        } else if (i == R.id.btn_login) {


            ARouter.getInstance().build(ARouterPathUtils.User_IdentityActivity).navigation();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
