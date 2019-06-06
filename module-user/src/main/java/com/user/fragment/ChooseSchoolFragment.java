package com.user.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.ui.fragment.kit.BaseFragment;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSchoolFragment extends BaseFragment {
    @BindView(R2.id.et_school)
    EditText etSchool;
    @BindView(R2.id.iv_school)
    ImageView ivSchool;
    @BindView(R2.id.tv_tip)
    TextView tvTip;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choose_school;
    }


    @OnClick({R2.id.iv_school, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_school) {
            etSchool.setText("");

        } else if (i == R.id.btn_sure) {

            String school = etSchool.getText().toString().trim();
            if (school.equals("")) {

                showToast("学校不能为空！");
                return;
            }

            if (normalChangeListener != null) {
                normalChangeListener.onNomalChange(school);
            }


        }
    }


    public interface NormalChangeListener {

        void onNomalChange(String str);
    }

    private NormalChangeListener normalChangeListener;

    public void setOnNomalChangeListener(NormalChangeListener listener) {

        this.normalChangeListener = listener;

    }
}
