package com.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.fragment.BaseAppFragment;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserFragment extends BaseAppFragment {


    @BindView(R2.id.tv)
    TextView tv;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        tv.setText("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
