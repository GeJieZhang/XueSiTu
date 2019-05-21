package com.xuesitu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lib.fastkit.utils.log.LogUtil;

public class TestView extends View {
    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST ) {
            LogUtil.e("widthMode:"+"AT_MOST");
            LogUtil.e("AT_MOST:"+MeasureSpec.AT_MOST);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            LogUtil.e("widthMode:"+"EXACTLY");
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            LogUtil.e("widthMode:"+"UNSPECIFIED");
        }



        if (heightMode == MeasureSpec.AT_MOST ) {
            LogUtil.e("heightMode:"+"AT_MOST");

        } else if (heightMode == MeasureSpec.EXACTLY) {
            LogUtil.e("heightMode:"+"EXACTLY");
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            LogUtil.e("heightMode:"+"UNSPECIFIED");
        }




        LogUtil.e("width:"+width);
        LogUtil.e("height:"+height);


        LogUtil.e("widthMode:"+widthMode);
        LogUtil.e("heightMode:"+heightMode);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

//        LogUtil.e("left:"+left);
//        LogUtil.e("top:"+top);
//
//        LogUtil.e("right:"+right);
//        LogUtil.e("right:"+right);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        LogUtil.e("w:"+w);
//        LogUtil.e("h:"+h);
//
//        LogUtil.e("oldw:"+oldw);
//        LogUtil.e("oldh:"+oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);





    }
}
