package com.youxuan.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.fragment.BaseAppFragment;
import com.youxuan.R;

@Route(path = ARouterPathUtils.YouXuan_FengXiangFragment)

public class FengXiangFragment extends BaseAppFragment {


    private TextView tv;


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        tv = view.findViewById(R.id.tv);

        tv.setText(this.getClass().getName());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment;
    }
}
