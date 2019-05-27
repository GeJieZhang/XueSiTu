package com.dayi.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;


@Route(path = ARouterPathUtils.Dayi_DayiFragment)
public class DayiFragment extends BaseAppFragment {


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dayi;
    }
}
