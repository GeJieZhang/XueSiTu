package com.user.utils.pop;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lib.app.CodeUtil;
import com.lib.bean.base.MyBaseBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.user.R;
import com.zyyoona7.popup.EasyPopup;

public class ChoseDropTypePopupUtils {


    private Activity activity;

    public ChoseDropTypePopupUtils(Activity ac) {

        this.activity = ac;

        initAnswerPopuPopu();
    }

    private EasyPopup answerPopu;

    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_chose_drops)

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

    private AppCompatEditText et_cmmt;

    private Button btn_sure;

    private CheckBox checkBox1, checkBox2;

    private ImageView iv_delete;


    private TextView tv_title;


    private void initView(View view) {
        iv_delete = view.findViewById(R.id.iv_delete);
        tv_title = view.findViewById(R.id.tv_title);
        checkBox1 = view.findViewById(R.id.checkBox1);
        checkBox2 = view.findViewById(R.id.checkBox2);
        et_cmmt = view.findViewById(R.id.et_cmmt);
        btn_sure = view.findViewById(R.id.btn_sure);


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    type = 0;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    type = 1;
                }
            }
        });


        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dropClass();


            }
        });


    }


    /***
     * 调退课
     */
    private void dropClass() {


        if (checkBox1.isChecked() | checkBox2.isChecked()) {

        } else {
            Toast.makeText(activity, "请选择调退课方式", Toast.LENGTH_SHORT).show();
            return;

        }


        String text = et_cmmt.getText().toString().trim();
        if (text.equals("")) {
            Toast.makeText(activity, "内容不能为空", Toast.LENGTH_SHORT).show();

            return;
        }

        HttpUtils.with(activity)
                .addParam("requestType", "ORDER_APPLY")
                .addParam("token", SharedPreferenceManager.getInstance(activity).getUserCache().getUserToken())
                .addParam("order_id", order_id)
                .addParam("order_type", order_type)
                .addParam("type", type)
                .execute(new HttpDialogCallBack<MyBaseBean>() {
                    @Override
                    public void onSuccess(MyBaseBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {


                            dismiss();


                        }

                        Toast.makeText(activity, result.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    private int type = 0;

    private String order_id;

    private String order_type;

    public void showAnswerPopuPopu(View view, String order_id, String order_type) {

        this.order_id = order_id;
        this.order_type = order_type;

        answerPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private ChoseDropTypePopupUtilsListener listener;

    public void setChoseDropTypePopupUtilsListener(ChoseDropTypePopupUtilsListener choseDropTypePopupUtilsListener) {

        this.listener = choseDropTypePopupUtilsListener;

    }

    public void dismiss() {
        answerPopu.dismiss();
    }

    public interface ChoseDropTypePopupUtilsListener {

        void onSure(String content);


    }

    public void setContent(String content) {
        et_cmmt.setText(content);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

}
