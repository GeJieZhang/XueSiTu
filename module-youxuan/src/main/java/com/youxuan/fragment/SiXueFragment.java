package com.youxuan.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.extension.HttpNormalCallBack;
import com.lib.ui.fragment.BaseAppFragment;
import com.youxuan.R;

@Route(path = ARouterPathUtils.YouXuan_SiXueFragment)
public class SiXueFragment extends BaseAppFragment {


    private TextView tv;


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        tv = view.findViewById(R.id.tv);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtils.with(getContext())
                        .url("http://www.ifeng.com")
                        .get()
                        .execute(new HttpNormalCallBack<String>() {
                            @Override
                            public void onSuccess(String result) {


                            }

                            @Override
                            public void onError(String e) {

                                showToast(e);

                            }
                        });

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment;
    }
}
