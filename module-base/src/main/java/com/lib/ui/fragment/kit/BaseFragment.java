package com.lib.ui.fragment.kit;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {


    protected abstract void onCreateView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();


    private Unbinder binder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        binder = ButterKnife.bind(this, view);
        onCreateView(view, savedInstanceState);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }


    private Toast mToast;

    /**
     * Toast
     *
     * @param text
     */
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }
}
