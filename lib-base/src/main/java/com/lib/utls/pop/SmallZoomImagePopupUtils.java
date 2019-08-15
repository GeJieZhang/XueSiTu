package com.lib.utls.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.base.R;
import com.lib.bean.PushDetailBean;
import com.lib.bean.RobQuestionBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.utls.glide.GlideConfig;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class SmallZoomImagePopupUtils {


    private Context context;


    public SmallZoomImagePopupUtils(Context context) {

        this.context = context;

        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(context)
                .setContentView(R.layout.popup_small_zoom_image)
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

    public void setZoomImage(String url) {
        Glide.with(context)
                .load(url)
                .apply(GlideConfig.getRectangleOptions())
                .into(iv_photo);
    }


    private ImageView iv_photo;

    private ImageView iv_delete;

    private void initView(View view) {
        iv_photo = view.findViewById(R.id.iv_photo);
        iv_delete = view.findViewById(R.id.iv_delete);

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    private void showToast(String a) {
        Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
    }


    public void dismiss() {
        popu.dismiss();

    }


    public void showAnswerPopuPopu(View view) {


        popu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


}
