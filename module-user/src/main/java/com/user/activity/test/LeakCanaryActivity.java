package com.user.activity.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;

@Route(path= ARouterPathUtils.User_LeakCanaryActivity)
public class LeakCanaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_activity);

        ActivityPool.getActivity().addActivity(this);
    }
}