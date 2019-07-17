package com.dayi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dayi.R;


public class MyRecordAudioView extends LinearLayout {

    private Animation anim;
    private View root;

    private ImageView iv_bg;

    public MyRecordAudioView(Context context) {
        this(context, null);
    }

    public MyRecordAudioView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecordAudioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {


        root = LayoutInflater.from(context).inflate(R.layout.view_record_audio, this);
        iv_bg = (ImageView) root.findViewById(R.id.iv_bg);
        iv_bg.setVisibility(GONE);

        initRotateAnim();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSelected(true);
                iv_bg.setVisibility(VISIBLE);
                iv_bg.startAnimation(anim);
                Log.e("======", "ACTION_DOWN");

                if (listener != null) {
                    listener.onRecordViewStart();
                }


                break;
            case MotionEvent.ACTION_UP:
                setSelected(false);
                Log.e("======", "ACTION_UP");
                iv_bg.setVisibility(GONE);
                iv_bg.clearAnimation();
                if (listener != null) {
                    listener.onRecordViewStop();
                }

                break;

            default:

                break;
        }

        return true;
    }


    public void initRotateAnim() {
        anim = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(3000); // 设置动画时间
        anim.setRepeatCount(-1);
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器

    }

    private RecordAudioViewListener listener;

    public interface RecordAudioViewListener {

        void onRecordViewStart();

        void onRecordViewStop();
    }


    public void setRecordAudioViewListener(RecordAudioViewListener recordAudioViewListener) {

        this.listener = recordAudioViewListener;

    }


}
