package com.live.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.live.R;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;
import com.zyyoona7.popup.BasePopup;

/**
 * Created by zyyoona7 on 2018/3/12.
 * <p>
 * PopupWindow 中存在 EditText 隐藏键盘方法不起作用，只有 toggle 键盘方法才起作用
 * 注：建议由 EditText 需求的弹窗使用 DialogFragment
 */
public class CmmtPopup extends BasePopup<CmmtPopup> {

    private View.OnClickListener mCancelListener;
    private MyOkClickListener mOkListener;
    private ImageView iv_camera;
    private EditText et_message;
    private Button btn_send;


    public static CmmtPopup create(Context context) {
        return new CmmtPopup(context);
    }

    private Context context;

    public CmmtPopup(Context context) {
        setContext(context);

        this.context = context;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_cmmt, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void initViews(View view, CmmtPopup basePopup) {

        iv_camera = findViewById(R.id.iv_camera);
        et_message = findViewById(R.id.et_message);
        btn_send = findViewById(R.id.btn_send);

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOkListener != null) {

                    hideSoftInput();
                    dismiss();
                    mOkListener.onCameraClick();
                }
            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOkListener != null) {
                    mOkListener.onClick(v, et_message.getText().toString().trim());
                    et_message.setText("");
                }
            }
        });

    }

    public CmmtPopup setOnCancelClickListener(View.OnClickListener listener) {
        mCancelListener = listener;
        return this;
    }

    public CmmtPopup setOnOkClickListener(MyOkClickListener listener) {
        mOkListener = listener;
        return this;
    }

    public CmmtPopup showSoftInput() {
        KeyboardUtils.toggleSoftInput(context);
        return this;
    }


    public CmmtPopup hideSoftInput() {
        KeyboardUtils.toggleSoftInput(context);
        return this;
    }


    public interface MyOkClickListener {

        void onClick(View v, String content);

        void onCameraClick();
    }


}


