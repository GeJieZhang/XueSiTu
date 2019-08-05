package com.lib.utls.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.base.R;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class PushPopupUtils {


    private Activity activity;

    public PushPopupUtils(Activity ac) {

        this.activity = ac;

        initPopuPopu();
    }

    private EasyPopup popu;

    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_push)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setHeight(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {

                        initView(view);


                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    private void initView(View view) {

    }


    public void dismiss() {
        popu.dismiss();
    }


    public void showAnswerPopuPopu(View view) {

        popu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

}
