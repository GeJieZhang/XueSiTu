package com.lib.utls.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.base.R;
import com.lib.bean.PushDetailBean;
import com.lib.bean.RobQuestionBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.utls.share.ShareUtils;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class SharePopupBottomUtils implements View.OnClickListener {


    private Context context;


    public SharePopupBottomUtils(Context context) {

        this.context = context;

        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(context)
                .setContentView(R.layout.popup_share_bottom)
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


    private LinearLayout lin_share, lin_QQ, lin_weiXin, lin_weiBo;

    private Button btn_cancel;

    private View v_bg;

    private void initView(View view) {

        lin_share = view.findViewById(R.id.lin_share);
        lin_QQ = view.findViewById(R.id.lin_QQ);
        lin_weiXin = view.findViewById(R.id.lin_weiXin);
        lin_weiBo = view.findViewById(R.id.lin_weiBo);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        v_bg = view.findViewById(R.id.v_bg);

        v_bg.setOnClickListener(this);
        lin_share.setOnClickListener(this);
        lin_QQ.setOnClickListener(this);
        lin_weiXin.setOnClickListener(this);
        lin_weiBo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    private void showToast(String a) {
        Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
    }


    public void showAnswerPopuPopu(View view) {


        popu.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {


        popu.dismiss();
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.lin_share) {

            if (listener != null) {
                listener.onShareClick();
            }


        } else if (i == R.id.lin_QQ) {
            if (listener != null) {
                listener.onQQClick();
            }

        } else if (i == R.id.lin_weiXin) {
            if (listener != null) {
                listener.onWeiXinClick();
            }

        } else if (i == R.id.lin_weiBo) {
            if (listener != null) {
                listener.onWeiBoClick();
            }

        } else if (i == R.id.btn_cancel) {
            dismiss();
        } else if (i == R.id.v_bg) {
            dismiss();
        }

    }


    private SharePopupBottomUtilsListener listener;


    public void setSharePopupBottomUtilsListener(SharePopupBottomUtilsListener sharePopupBottomUtilsListener) {

        this.listener = sharePopupBottomUtilsListener;

    }


    public interface SharePopupBottomUtilsListener {
        void onShareClick();

        void onQQClick();

        void onWeiXinClick();

        void onWeiBoClick();
    }


}
