package com.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_SetActivity)
public class SetActivity extends BaseAppActivity {
    @BindView(R2.id.lin_clear)
    LinearLayout linClear;
    @BindView(R2.id.lin_zhifu)
    LinearLayout linZhifu;
    @BindView(R2.id.lin_update)
    LinearLayout linUpdate;
    @BindView(R2.id.lin_help)
    LinearLayout linHelp;
    @BindView(R2.id.lin_about)
    LinearLayout linAbout;
    @BindView(R2.id.lin_out)
    LinearLayout linOut;

    @Override
    protected void onCreateView() {

        initTitle();

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("设置")
                .setRightIcon(R.mipmap.nav_share)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击分享");
                    }
                })
                .builder();

        // navigationBar.getRightTextView()
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }


    @OnClick({R2.id.lin_clear, R2.id.lin_zhifu, R2.id.lin_update, R2.id.lin_help, R2.id.lin_about, R2.id.lin_out})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_clear) {
        } else if (i == R.id.lin_zhifu) {

            ARouter.getInstance().build(ARouterPathUtils.User_SetZhiFuActivity).navigation();
        } else if (i == R.id.lin_update) {
        } else if (i == R.id.lin_help) {
        } else if (i == R.id.lin_about) {
        } else if (i == R.id.lin_out) {

            NormalDialog.getInstance(this)
                    .setTitle("退出登录")
                    .setContent("你确认要退出登录么?")
                    .setCancelListener(new NormalDialog.CancelListener() {
                        @Override
                        public void onCancel(NormalDialog normalDialog) {

                        }


                    })
                    .setSureListener(new NormalDialog.SurelListener() {
                        @Override
                        public void onSure(NormalDialog normalDialog) {
                            SharedPreferenceManager.getInstance(SetActivity.this).getUserCache().setUserToken("");
                            normalDialog.dismiss();
                        }


                    })
                    .show();
        }
    }
}
