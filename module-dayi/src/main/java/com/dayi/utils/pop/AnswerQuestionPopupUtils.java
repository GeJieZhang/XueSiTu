package com.dayi.utils.pop;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dayi.R;
import com.dayi.view.CmmtWordPopup;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;
import com.zyyoona7.popup.EasyPopup;

public class AnswerQuestionPopupUtils {


    private Activity activity;

    public AnswerQuestionPopupUtils(Activity ac) {

        this.activity = ac;

        initAnswerPopuPopu();
    }

    private EasyPopup answerPopu;

    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_answer_question)

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

    private Button btn_sure,btn_cancel;

    private LinearLayout lin_parent;

    private CardView card;

    private TextView tv_title;



    private void initView(View view) {
        tv_title=view.findViewById(R.id.tv_title);
        lin_parent = view.findViewById(R.id.lin_parent);
        card = view.findViewById(R.id.card);
        et_cmmt = view.findViewById(R.id.et_cmmt);
        btn_sure = view.findViewById(R.id.btn_sure);
        btn_cancel=view.findViewById(R.id.btn_cancel);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lin_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPopu.dismiss();
            }
        });



        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = et_cmmt.getText().toString().trim();
                if (text.equals("")) {
                    Toast.makeText(activity, "内容不能为空", Toast.LENGTH_SHORT).show();
                }

                if (listener != null) {
                    listener.onSure(text);
                }

                answerPopu.dismiss();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPopu.dismiss();


            }
        });

    }

    public void showAnswerPopuPopu(View view) {

        answerPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private AnswerQuestionPopupUtilsListener listener;

    public void setAnswerQuestionPopupUtilsListener(AnswerQuestionPopupUtilsListener answerQuestionPopupUtilsListener) {

        this.listener = answerQuestionPopupUtilsListener;

    }

    public void dismiss() {
        answerPopu.dismiss();
    }

    public interface AnswerQuestionPopupUtilsListener {

        void onSure(String content);


    }

    public void setContent(String content) {
        et_cmmt.setText(content);
    }
    public void setTitle(String title) {
        tv_title.setText(title);
    }

}
