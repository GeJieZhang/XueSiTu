package com.user.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lib.ui.fragment.kit.BaseFragment;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class SexFragment extends BaseFragment {


    @BindView(R2.id.iv_men)
    ImageView ivMen;
    @BindView(R2.id.lin_men)
    LinearLayout linMen;
    @BindView(R2.id.iv_woman)
    ImageView ivWoman;
    @BindView(R2.id.lin_woman)
    LinearLayout linWoman;

    //1男
    private final int MEN = 1;
    //2女
    private final int WOMAN = 2;
    //默认为学生
    private int TYPE = 1;


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        initSex();

    }

    private void initSex() {

        switch (TYPE) {
            case MEN: {
                ivMen.setImageResource(R.mipmap.icon_sex_man);
                ivWoman.setImageResource(R.mipmap.icon_sex_default);
                if (sexChooseListener != null) {
                    sexChooseListener.onSexChoose("男");
                }

                break;
            }
            case WOMAN: {
                ivWoman.setImageResource(R.mipmap.icon_sex_woman);
                ivMen.setImageResource(R.mipmap.icon_sex_default);
                if (sexChooseListener != null) {
                    sexChooseListener.onSexChoose("女");
                }
                break;
            }
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sex;
    }


    @OnClick({R2.id.lin_men, R2.id.lin_woman})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_men) {

            TYPE = MEN;

            initSex();

        } else if (i == R.id.lin_woman) {
            TYPE = WOMAN;
            initSex();

        }
    }


    public interface SexChooseListener {

        void onSexChoose(String str);
    }

    private SexChooseListener sexChooseListener;

    public void setSexChooseListener(SexChooseListener chooseListener) {

        this.sexChooseListener = chooseListener;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
