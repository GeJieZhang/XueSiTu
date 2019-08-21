package com.xuesitu.activity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lib.ui.fragment.BaseAppFragment;
import com.xuesitu.R;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class FragmentGuide extends BaseAppFragment {


    @BindView(R.id.iv_bg)
    ImageView ivBg;
//    @BindView(R.id.btn_start)
//    Button btnStart;


    private int page = 1;

    @SuppressLint("ValidFragment")
    public FragmentGuide(int page) {

        this.page = page;
    }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        switch (page) {
            case 1: {
                ivBg.setImageResource(R.mipmap.page1);

                break;
            }
            case 2: {
                ivBg.setImageResource(R.mipmap.page2);

                break;
            }
            case 3: {
                ivBg.setImageResource(R.mipmap.page3);

                break;
            }
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide;
    }


//    @OnClick(R.id.btn_start)
//    public void onViewClicked() {
//        if (listener != null) {
//            listener.onStartClick();
//
//        }
//
//    }
//
//
//    private FragmentGuideListener listener;
//
//    public void setFragmentGuideListener(FragmentGuideListener fragmentGuideListener) {
//        this.listener = fragmentGuideListener;
//
//    }
//
//    public interface FragmentGuideListener {
//
//        void onStartClick();
//
//    }


}
