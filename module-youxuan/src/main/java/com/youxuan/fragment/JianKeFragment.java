package com.youxuan.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.fragment.BaseAppFragment;
import com.youxuan.R;

@Route(path = ARouterPathUtils.YouXuan_JianKeFragment)
public class JianKeFragment extends BaseAppFragment {


    private TextView tv;


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        tv = view.findViewById(R.id.tv);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPathUtils.User_BookChooseActivity).navigation();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment;
    }
}
