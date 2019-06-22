package com.live.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.live.R;
import com.live.utils.KeyboardUtils;
import com.zyyoona7.popup.BasePopup;

/**
 * Created by zyyoona7 on 2018/3/12.
 * <p>
 * PopupWindow 中存在 EditText 隐藏键盘方法不起作用，只有 toggle 键盘方法才起作用
 * 注：建议由 EditText 需求的弹窗使用 DialogFragment
 */
public class CmmtPopup extends BasePopup<CmmtPopup> {

    private View.OnClickListener mCancelListener;
    private View.OnClickListener mOkListener;
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
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOkListener != null) {
                    mOkListener.onClick(v);
                }
            }
        });

    }

    public CmmtPopup setOnCancelClickListener(View.OnClickListener listener) {
        mCancelListener = listener;
        return this;
    }

    public CmmtPopup setOnOkClickListener(View.OnClickListener listener) {
        mOkListener = listener;
        return this;
    }

    public CmmtPopup showSoftInput() {
        if (et_message != null) {
            et_message.post(new Runnable() {
                @Override
                public void run() {
                    KeyboardUtils.showSoftInput(et_message, context);
                }
            });
        }
        return this;
    }

    public CmmtPopup hideSoftInput() {
        if (et_message != null) {
            et_message.post(new Runnable() {
                @Override
                public void run() {
                    KeyboardUtils.hideSoftInput(et_message, context);
                }
            });
        }
        return this;
    }
}


