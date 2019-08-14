package com.lib.view.navigationbar.xuesitu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.app.EventBusTagUtils;
import com.lib.base.R;
import com.lib.bean.Event;

import org.simple.eventbus.EventBus;


public class HomeNavigationBar extends FrameLayout {

    private View root;

    private ImageView iv_personal, iv_logo, iv_message;

    private TextView tv_title;


    public static final int YOU_XUAN = 0;

    public static final int YOU_WEN_DA = 1;

    public static final int YOU_TU_ZI = 2;

    public static final int YOU_JIAN_KE = 3;
    public static final int YOU_FENG_XIANG = 4;

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
        tv_title = root.findViewById(R.id.tv_title);
        iv_logo = root.findViewById(R.id.iv_logo);
        iv_message = root.findViewById(R.id.iv_message);
        iv_personal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event<>(1, "打开个人中心"), EventBusTagUtils.HomeNavigationBar);
            }
        });

        iv_message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event<>(2, "打开消息列表"), EventBusTagUtils.HomeNavigationBar);
            }
        });


    }


    public void setViewType(int type) {
        iv_message.setVisibility(GONE);
        iv_logo.setVisibility(GONE);
        iv_personal.setVisibility(VISIBLE);

        switch (type) {
            case YOU_XUAN: {

                tv_title.setText("优选");

                break;
            }

            case YOU_WEN_DA: {

                tv_title.setText("问答");
                iv_personal.setVisibility(GONE);
                break;
            }
            case YOU_TU_ZI: {
                iv_logo.setVisibility(VISIBLE);
                tv_title.setText("在线作业答疑");

                break;
            }
            case YOU_JIAN_KE: {

                tv_title.setText("尖课");
                iv_personal.setVisibility(GONE);

                break;
            }
            case YOU_FENG_XIANG: {
                iv_message.setVisibility(VISIBLE);
                iv_personal.setVisibility(GONE);
                tv_title.setText("纷享");

                break;
            }

        }

    }


}
