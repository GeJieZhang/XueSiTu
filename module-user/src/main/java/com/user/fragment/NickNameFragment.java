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

public class NickNameFragment extends BaseFragment {
    @BindView(R2.id.et_nick)
    EditText etNick;
    @BindView(R2.id.iv_nick)
    ImageView ivNick;
    @BindView(R2.id.tv_tip)
    TextView tvTip;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nick_name;
    }


    @OnClick({R2.id.iv_nick, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_nick) {

            etNick.setText("");
        } else if (i == R.id.btn_sure) {

            String nick = etNick.getText().toString().trim();

            if (nick.equals("")) {

                showToast("昵称不能为空！");
                return;
            }

            if (normalChangeListener != null) {
                normalChangeListener.onNomalChange(nick);
            }
        }
    }


    public void setNickName(String str){
        etNick.setText(str);
    }


    public interface NormalChangeListener {

        void onNomalChange(String str);
    }

    private NormalChangeListener normalChangeListener;

    public void setOnNomalChangeListener(NormalChangeListener listener) {

        this.normalChangeListener = listener;

    }
}
