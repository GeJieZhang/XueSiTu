package com.dayi.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.dayi.R;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;


import com.zyyoona7.popup.BasePopup;

/**
 * Created by zyyoona7 on 2018/3/12.
 * <p>
 * PopupWindow 中存在 EditText 隐藏键盘方法不起作用，只有 toggle 键盘方法才起作用
 * 注：建议由 EditText 需求的弹窗使用 DialogFragment
 */
public class CmmtWordPopup extends BasePopup<CmmtWordPopup> {


    ImageView iv_voice;

    Button btn_send;
    AppCompatEditText mEditText;

    public static CmmtWordPopup create(Context context) {
        return new CmmtWordPopup(context);
    }

    private Context context;

    public CmmtWordPopup(Context context) {
        setContext(context);

        this.context = context;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_word, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void initViews(View view, CmmtWordPopup basePopup) {

        iv_voice = findViewById(R.id.iv_voice);
        iv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVoiceClick();
                }
            }
        });
        mEditText = findViewById(R.id.et_cmmt);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = mEditText.getText().toString().trim();
                if (content.equals("")) {

                    Toast.makeText(context, "描述不能为空！", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (listener != null) {
                    listener.onSendClick(content);
                }
            }
        });

    }


    public CmmtWordPopup showSoftInput() {

        KeyboardUtils.toggleSoftInput(context);

        return this;
    }

    public CmmtWordPopup hideSoftInput() {

        KeyboardUtils.toggleSoftInput(context);

        return this;
    }


    private WordPopupInterface listener;


    public interface WordPopupInterface {

        void onVoiceClick();

        void onSendClick(String content);

    }

    public CmmtWordPopup setWordPopupInterface(WordPopupInterface wordPopupInterface) {

        this.listener = wordPopupInterface;


        return this;

    }


}


