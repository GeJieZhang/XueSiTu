package com.dayi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dayi.R;
import com.lib.app.EventBusTagUtils;

import com.lib.bean.Event;
import com.lib.fastkit.utils.px_dp.DisplayUtil;


import org.simple.eventbus.EventBus;


public class MarkButton extends FrameLayout {

    private View root;
    private int markBackground;

    private boolean textShow = true;

    private ImageView iv_bg;

    private float marginLeft;
    private float marginRight;
    private float marginTop;
    private float marginBottom;

    private LinearLayout lin_text;

    private String mValue;

    private TextView tv_tubi;


    public MarkButton(@NonNull Context context) {
        this(context, null);
    }

    public MarkButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        root = inflate(context, R.layout.button_mark, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.lib.fastkit.R.styleable.MarkButton);
        textShow = typedArray.getBoolean(com.lib.fastkit.R.styleable.MarkButton_textShow, true);
        markBackground = typedArray.getResourceId(com.lib.fastkit.R.styleable.MarkButton_markBackground, R.mipmap.t1);
        marginLeft = typedArray.getDimension(com.lib.fastkit.R.styleable.MarkButton_textMarginLeft, 2);
        marginRight = typedArray.getDimension(com.lib.fastkit.R.styleable.MarkButton_textMarginRight, 2);
        marginTop = typedArray.getDimension(com.lib.fastkit.R.styleable.MarkButton_textMarginTop, 2);
        marginBottom = typedArray.getDimension(com.lib.fastkit.R.styleable.MarkButton_textMarginBottom, 2);
        mValue = typedArray.getString(com.lib.fastkit.R.styleable.MarkButton_mValue);
        typedArray.recycle();

        initView();


    }

    private void initView() {
        tv_tubi = findViewById(R.id.tv_tubi);

        iv_bg = findViewById(R.id.iv_bg);
        lin_text = findViewById(R.id.lin_text);
        iv_bg.setImageResource(markBackground);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lin_text.getLayoutParams();
        params.setMargins(DisplayUtil.dip2px(getContext(), marginLeft), DisplayUtil.dip2px(getContext(), marginTop), DisplayUtil.dip2px(getContext(), marginRight), DisplayUtil.dip2px(getContext(), marginBottom));
        lin_text.setLayoutParams(params);
        tv_tubi.setText(mValue);
        if (textShow) {
            lin_text.setVisibility(VISIBLE);
        } else {
            lin_text.setVisibility(GONE);
        }

    }

    public void setValue(int value) {
        tv_tubi.setText(value + "兔币");
    }


}
