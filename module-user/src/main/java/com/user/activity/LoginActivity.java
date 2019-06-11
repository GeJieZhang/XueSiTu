package com.user.activity;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.db.shared_prefrences.interfaces.UserCacheInterface;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.views.dialog.zenhui.AlertDialog;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.fastkit.utils.string_deal.regex.RegexUtils;
import com.lib.fastkit.utils.timer_countdown.CountDownTimer;
import com.lib.ui.activity.BaseAppActivity;
import com.user.R;
import com.user.R2;
import com.user.bean.BaseLoginBean;
import com.user.bean.IdentityUserBean;
import com.user.fragment.NickNameFragment;
import com.user.fragment.XieYiFragment;
import com.user.test.LoginManager;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.User_LoginActivity)
public class LoginActivity extends BaseAppActivity {
    @BindView(R2.id.btn_code)
    Button btnCode;
    @BindView(R2.id.btn_login)
    Button btnLogin;
    @BindView(R2.id.tv_xieyi)
    TextView tvXieyi;

    @BindView(R2.id.et_phone)
    EditText etphone;

    @BindView(R2.id.et_code)
    EditText etCode;

    @BindView(R2.id.checkBox)
    CheckBox checkBox;

    @Override
    protected void onCreateView() {


        //LoginManager.getInstance(this).dealData();

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
            btnCode.setTextColor(getResources().getColor(R.color.white));
            btnCode.setText("获取验证码");


        }
    };


    @OnClick({R2.id.btn_code, R2.id.btn_login, R2.id.tv_xieyi})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_code) {
            requestCode();


        } else if (i == R.id.btn_login) {

            if (checkBox.isChecked()) {
                requestLogin();
            } else {
                showToast("请阅读注册协议");
            }


        } else if (i == R.id.tv_xieyi) {

            //ARouter.getInstance().build(ARouterPathUtils.User_XieyiActivity).navigation();
            showXieYi();

        }
    }

    private void requestLogin() {
        String phone = etphone.getText().toString().trim();
        String code = etCode.getText().toString().trim();


        if (!RegexUtils.checkCode(this, code)) {

            return;
        }


        if (!RegexUtils.checkPhone(this, phone)) {

            return;
        }

        HttpUtils.with(this)
                .addParam("requestType", "LOGIN")
                .addParam("phone", phone)
                .addParam("code", code)
                .post()
                .execute(new HttpNormalCallBack<BaseLoginBean>() {
                    @Override
                    public void onSuccess(BaseLoginBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            UserCacheInterface userCacheInterface = SharedPreferenceManager.getInstance(LoginActivity.this).getUserCache();
                            userCacheInterface.setUserToken(result.getObj().getToken());
                            userCacheInterface.setUserIdentity(result.getObj().getIdentity());
                            userCacheInterface.setUserName(result.getObj().getName());
                            userCacheInterface.setUserHeadUrl(result.getObj().getPhoto_url());
                            userCacheInterface.setUserPhone(result.getObj().getPhone());


                           // ARouter.getInstance().build(ARouterPathUtils.App_MainActivity).navigation();

                            finish();


                        }

                        if (result.getCode() == CodeUtil.CODE_402) {

                            requestCheckCode();

                        }


                        showToast(result.getMsg());
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    /**
     * 检测验证码
     */
    private void requestCheckCode() {
        String phone = etphone.getText().toString().trim();
        String code = etCode.getText().toString().trim();


        if (!RegexUtils.checkCode(this, code)) {

            return;
        }


        if (!RegexUtils.checkPhone(this, phone)) {

            return;
        }

        HttpUtils.with(this)
                .addParam("requestType", "VERIFY_CODE_CHECK")
                .addParam("phone", phone)
                .addParam("code", code)
                .post()
                .execute(new HttpNormalCallBack<BaseLoginBean>() {
                    @Override
                    public void onSuccess(BaseLoginBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            ARouter.getInstance().build(ARouterPathUtils.User_IdentityActivity).navigation();


                        }
                        showToast(result.getMsg());
                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    private void requestCode() {
        String phone = etphone.getText().toString().trim();
        if (!RegexUtils.checkPhone(this, phone)) {

            return;
        }
        HttpUtils.with(this)
                .addParam("requestType", "VERIFY_CODE_SEND")
                .addParam("phone", phone)
                .post()
                .execute(new HttpNormalCallBack<BaseLoginBean>() {
                    @Override
                    public void onSuccess(BaseLoginBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            btnCode.setEnabled(false);
                            btnCode.setBackgroundResource(R.drawable.bg_circle_hollow_r10);
                            btnCode.setTextColor(getResources().getColor(R.color.base_text1));
                            timer.start();
                        }
                        showToast(result.getMsg());


                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }


    @Subscriber(tag = EventBusTagUtils.TeacherStudentChooseFragment)
    public void fromTeacherStudentChooseFragment(Event event) {
        switch (event.getEventCode()) {
            case 1: {

                IdentityUserBean identityUserBean = (IdentityUserBean) event.getData();

                requestRegist(identityUserBean);


                break;
            }

        }

    }

    private void requestRegist(IdentityUserBean identityUserBean) {

        String phone = etphone.getText().toString().trim();
        if (!RegexUtils.checkPhone(this, phone)) {

            return;
        }
        HttpUtils.with(this)
                .addParam("requestType", "REGISTERED")
                .addParam("phone", phone)
                .addParam("identity", identityUserBean.getType())
                .addParam("grade_id", identityUserBean.getGrade_id())
                .addParam("subject_id", identityUserBean.getSubject_id())
                .post()
                .execute(new HttpNormalCallBack<BaseLoginBean>() {
                    @Override
                    public void onSuccess(BaseLoginBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            UserCacheInterface userCacheInterface = SharedPreferenceManager.getInstance(LoginActivity.this).getUserCache();
                            userCacheInterface.setUserToken(result.getObj().getToken());
                            userCacheInterface.setUserIdentity(result.getObj().getIdentity());
                            userCacheInterface.setUserName(result.getObj().getName());
                            userCacheInterface.setUserHeadUrl(result.getObj().getPhoto_url());
                            userCacheInterface.setUserPhone(result.getObj().getPhone());
                            ARouter.getInstance().build(ARouterPathUtils.App_MainActivity).navigation();


                        }
                        showToast(result.getMsg());


                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }


    XieYiFragment xieYiFragment;

    private void showXieYi() {


        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_xieyi)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (xieYiFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(xieYiFragment).commit();
                            xieYiFragment = null;
                        }

                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();

        xieYiFragment = (XieYiFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_xieyi);


    }

}
