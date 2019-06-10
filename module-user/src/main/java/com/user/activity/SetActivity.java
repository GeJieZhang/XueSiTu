package com.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.utils.clear_cache.ClearDataUtils;
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
    @BindView(R2.id.tv_cache)
    TextView tvCache;

    @Override
    protected void onCreateView() {

        initTitle();

        initView();

    }




    private void initView() {

        setCache();

    }

    private void setCache() {
        try {
            String cacheSize = ClearDataUtils.getTotalCacheSize(this);
            tvCache.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("设置")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击分享");
                    }
                })
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }


    @OnClick({R2.id.lin_clear, R2.id.lin_zhifu, R2.id.lin_update, R2.id.lin_help, R2.id.lin_about, R2.id.lin_out})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_clear) {


            NormalDialog.getInstance()
                    .setTitle("清理缓存")
                    .setContent("你确认要缓存么?")
                    .setCancelListener(new NormalDialog.CancelListener() {
                        @Override
                        public void onCancel() {

                        }
                    })
                    .setSureListener(new NormalDialog.SurelListener() {

                        @Override
                        public void onSure() {
                            ClearDataUtils.clearAllCache(SetActivity.this);
                            setCache();


                        }


                    })
                    .show(getSupportFragmentManager());


        } else if (i == R.id.lin_zhifu) {

            ARouter.getInstance().build(ARouterPathUtils.User_SetZhiFuActivity).navigation();
        } else if (i == R.id.lin_update) {
        } else if (i == R.id.lin_help) {
        } else if (i == R.id.lin_about) {
        } else if (i == R.id.lin_out) {

            NormalDialog.getInstance()
                    .setTitle("退出登录")
                    .setContent("你确认要退出登录么?")
                    .setCancelListener(new NormalDialog.CancelListener() {
                        @Override
                        public void onCancel() {

                        }


                    })
                    .setSureListener(new NormalDialog.SurelListener() {
                        @Override
                        public void onSure() {
                            SharedPreferenceManager.getInstance(SetActivity.this).getUserCache().setUserToken("");

                        }


                    })
                    .show(getSupportFragmentManager());
        }
    }


}
