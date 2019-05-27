package com.lib.view.navigationbar.xuesitu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lib.app.EventBusTagUtils;
import com.lib.base.R;
import com.lib.bean.Event;

import org.simple.eventbus.EventBus;


public class HomeNavigationBar extends FrameLayout {

    private View root;

    private ImageView iv_personal;

    public HomeNavigationBar(@NonNull Context context) {
        this(context, null);
    }

    public HomeNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        root = inflate(context, R.layout.home_bar, this);
        initView();


    }

    private void initView() {
        iv_personal = root.findViewById(R.id.iv_personal);

        iv_personal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event<>(1,"打开个人中心"), EventBusTagUtils.PersonalFragment);
            }
        });
    }
}
