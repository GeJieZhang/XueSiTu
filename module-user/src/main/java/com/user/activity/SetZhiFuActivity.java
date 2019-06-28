package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.share.tool.StringUtil;
import com.lib.fastkit.utils.string_deal.regex.RegexUtils;
import com.lib.fastkit.utils.timer_countdown.CountDownTimer;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.bean.BaseBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_SetZhiFuActivity)
public class SetZhiFuActivity extends BaseAppActivity {


    public static final int SET_PASSWORD = 1;
    public static final int CHANGE_PASSWORD = 2;
    public static final int FORGET_PASSWORD = 3;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.et_old_password)
    EditText etOldPassword;
    @BindView(R2.id.et_new_password)
    EditText etNewPassword;
    @BindView(R2.id.et_again_password)
    EditText etAgainPassword;

    private int NOW_TYPE = 1;
    @BindView(R2.id.lin_phone)
    LinearLayout linPhone;
    @BindView(R2.id.btn_code)
    Button btnCode;
    @BindView(R2.id.lin_code)
    LinearLayout linCode;
    @BindView(R2.id.lin_old_password)
    LinearLayout linOldPassword;
    @BindView(R2.id.lin_new_password)
    LinearLayout linNewPassword;
    @BindView(R2.id.lin_again_password)
    LinearLayout linAgainPassword;
    @BindView(R2.id.tv_forget)
    TextView tvForget;
    @BindView(R2.id.btn_login)
    Button btnLogin;
    private NomalNavigationBar navigationBar;

    private String token;

    @Override
    protected void onCreateView() {
        NOW_TYPE = getIntent().getIntExtra("NOW_TYPE", SET_PASSWORD);

        token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


        initTitleAndView();


    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnCode.setEnabled(true);


            btnCode.setBackgroundResource(R.drawable.circle_button_blue_r10);
            btnCode.setTextColor(getResources().getColor(R.color.white));
            btnCode.setText("获取验证码");


        }
    };

    private void initTitleAndView() {
        initTitle();
        initView();
    }

    private void initView() {

        switch (NOW_TYPE) {
            case SET_PASSWORD: {
                initLayout();
                linNewPassword.setVisibility(View.VISIBLE);
                linAgainPassword.setVisibility(View.VISIBLE);

                break;
            }

            case CHANGE_PASSWORD: {
                initLayout();
                linOldPassword.setVisibility(View.VISIBLE);
                linNewPassword.setVisibility(View.VISIBLE);
                linAgainPassword.setVisibility(View.VISIBLE);
                tvForget.setVisibility(View.VISIBLE);
                break;
            }

            case FORGET_PASSWORD: {
                initLayout();
                linPhone.setVisibility(View.VISIBLE);
                linCode.setVisibility(View.VISIBLE);
                linNewPassword.setVisibility(View.VISIBLE);
                linAgainPassword.setVisibility(View.VISIBLE);

                break;
            }


        }


    }

    private void initLayout() {
        linPhone.setVisibility(View.GONE);
        linCode.setVisibility(View.GONE);
        linOldPassword.setVisibility(View.GONE);
        linNewPassword.setVisibility(View.GONE);
        linAgainPassword.setVisibility(View.GONE);
        tvForget.setVisibility(View.GONE);
    }


    protected void initTitle() {
        navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("设置密码")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击分享");
                    }
                })
                .builder();

        switch (NOW_TYPE) {
            case SET_PASSWORD: {

                navigationBar.getTitleTextView().setText("设置密码");
                break;
            }
            case CHANGE_PASSWORD: {
                navigationBar.getTitleTextView().setText("修改密码");
                break;
            }

            case FORGET_PASSWORD: {
                navigationBar.getTitleTextView().setText("忘记密码");
                break;
            }
        }

        // navigationBar.getRightTextView()
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_zhi_fu;
    }


    @OnClick({R2.id.btn_code, R2.id.tv_forget, R2.id.btn_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_code) {

            btnCode.setEnabled(false);

            btnCode.setBackgroundResource(R.drawable.bg_circle_hollow_r10);
            btnCode.setTextColor(getResources().getColor(R.color.base_text1));
            timer.start();

        } else if (i == R.id.tv_forget) {
            NOW_TYPE = FORGET_PASSWORD;
            initView();
            navigationBar.getTitleTextView().setText("忘记密码");

        } else if (i == R.id.btn_login) {


            switch (NOW_TYPE) {
                case SET_PASSWORD: {

                    requestSetPassword();


                    break;
                }

                case CHANGE_PASSWORD: {

                    requestChangePassword();
                    break;
                }

                case FORGET_PASSWORD: {


                    break;
                }


            }


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }


    //--------------------------------------------------------------------------------设置支付密码

    private void requestSetPassword() {
        String newPassword = etNewPassword.getText().toString().trim();
        String againPassword = etAgainPassword.getText().toString().trim();


        if (newPassword.equals("")) {
            showToast("请输入密码!");

            return;
        }

        if (againPassword.equals("")) {
            showToast("请再次输入密码!");
            return;
        }

        if (!newPassword.equals(againPassword)) {
            showToast("两次密码不一致请检查密码!");
            return;
        }

        if (!RegexUtils.checkPassWord(newPassword)) {
            showToast("密码必须是6位的数字、字符组合!");

            return;
        }


        HttpUtils.with(this).post()
                .addParam("requestType", "ACCOUNT_SET_PASSWORD")
                .addParam("token", token)
                .addParam("pay_password", newPassword)
                .execute(new HttpDialogCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            showToast(result.getMsg());
                            finish();
                        } else {
                            showToast(result.getMsg());
                        }

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    //--------------------------------------------------------------------------------修改支付密码


    private void requestChangePassword() {

        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String againPassword = etAgainPassword.getText().toString().trim();

        if (oldPassword.equals("")) {
            showToast("请输入原密码!");

            return;
        }
        if (newPassword.equals("")) {
            showToast("请输入密码!");

            return;
        }

        if (againPassword.equals("")) {
            showToast("请再次输入密码!");
            return;
        }

        if (!newPassword.equals(againPassword)) {
            showToast("两次密码不一致请检查密码!");
            return;
        }

        if (!RegexUtils.checkPassWord(newPassword)) {
            showToast("密码必须是6位的数字、字符组合!");

            return;
        }


        HttpUtils.with(this).post()
                .addParam("requestType", "ACCOUNT_UPDATE_PASSWORD")
                .addParam("token", token)
                .addParam("pay_password", oldPassword)

                .addParam("new_pay_password", newPassword)
                .execute(new HttpDialogCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            showToast(result.getMsg());
                            finish();
                        } else {
                            showToast(result.getMsg());
                        }

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }
}
