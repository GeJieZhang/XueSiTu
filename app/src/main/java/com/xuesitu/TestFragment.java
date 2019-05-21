package com.xuesitu;

import android.os.Bundle;
import android.view.View;

import com.lib.ui.fragment.BaseAppFragment;

import butterknife.ButterKnife;


public class TestFragment extends BaseAppFragment {




    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }



}
