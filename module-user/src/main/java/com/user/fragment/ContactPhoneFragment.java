package com.user.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lib.ui.fragment.BaseAppFragment;
import com.user.R;
import com.user.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactPhoneFragment extends BaseAppFragment {
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_nick)
    EditText etNick;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_phone;
    }


    @OnClick(R2.id.btn_sure)
    public void onViewClicked() {

        String phone = etPhone.getText().toString().trim();
        String nick = etNick.getText().toString().trim();

        if (normalChangeListener != null) {
            normalChangeListener.onNomalChange(nick, phone);
        }


    }


    public void setContactInfo(String name, String phone) {
        etNick.setText(name);
        etPhone.setText(phone);
    }


    public interface NormalChangeListener {

        void onNomalChange(String name, String phone);
    }

    private NormalChangeListener normalChangeListener;

    public void setOnNomalChangeListener(NormalChangeListener listener) {

        this.normalChangeListener = listener;

    }
}
