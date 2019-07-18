package com.dayi.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dayi.R;
import com.dayi.activity.AskQuestionActivity;
import com.dayi.bean.AudioEntity;
import com.dayi.bean.UploadVoice;
import com.lib.fastkit.utils.audio.AudioPlayManager;
import com.lib.fastkit.utils.audio.IAudioPlayListener;
import com.lib.fastkit.utils.time_deal.TimeUtils;

import static com.dayi.R2.id.tv_sound_duration;


/**
 * 展示语音feed,支持点击播放
 * v8.7.5 因发布器也使用SoundItemView，但不需要，也不能依赖PPVideoPlayerManager，所以实现一个父类CommonSoundItemView
 * 依赖PPVideoPlayerManager的，再由SoundItemView实现
 *
 * @author Kevin
 * @version V1.0
 * @Date 6/28/16
 * @Description
 */
public class CommonSoundItemView extends RelativeLayout {

    protected static final String TAG = "SoundItemView";
    //默认的阈值
    protected static final long DEFAULT_THRESHOLD = 8;

    protected ImageView ivSoundItemHorn;
    protected AnimationDrawable animationDrawable;
    protected RelativeLayout layoutSoundItem;
    protected TextView tvSoundDuration;
    protected ImageView iv_delete;

    protected LinearLayout lin_delete;

    protected UploadVoice audioInfo;
    protected Context context;
    private int maxLength;

    public CommonSoundItemView(Context context) {
        super(context);
        initView(context);
    }

    public CommonSoundItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CommonSoundItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.sound_item, this);
        rootView.findViewById(R.id.layout_sound_item).setBackgroundDrawable(null);
        lin_delete = (LinearLayout) findViewById(R.id.lin_delete);
        lin_delete.setVisibility(GONE);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        ivSoundItemHorn = (ImageView) findViewById(R.id.iv_sound_horn);
        animationDrawable = (AnimationDrawable) ivSoundItemHorn.getDrawable();
        animationDrawable.setOneShot(false);
        layoutSoundItem = (RelativeLayout) findViewById(R.id.layout_sound_item);
        tvSoundDuration = (TextView) findViewById(R.id.tv_sound_duration);
        maxLength = context.getResources().getDimensionPixelSize(R.dimen.pp_sound_item_length);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_delete.setVisibility(GONE);
                playSound();
            }
        });

        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lin_delete.setVisibility(VISIBLE);

                return true;
            }
        });
        iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (listener != null) {
                    listener.onDelete(audioInfo);
                }
            }
        });


    }


    /**
     * 播放语音
     */
    private void playSound() {

        if (audioInfo != null) {


            Uri uri = Uri.parse(audioInfo.getUrl());
            AudioPlayManager.getInstance().startPlay(context, uri, iAudioPlayListener);
        }

    }

    public void setAudioEntity(UploadVoice audioInfo) {
        this.audioInfo = audioInfo;
        tvSoundDuration.setText(TimeUtils.formatTime(audioInfo.getDuration()));
    }


    public void onStartUI() {
        resetDrawable();
        animationDrawable.start();
        invalidate();
    }


    public void onStopUI() {
        animationDrawable.stop();
        resetDrawable();
        invalidate();
    }

    protected void resetDrawable() {
        ivSoundItemHorn.clearAnimation();
        animationDrawable = (AnimationDrawable) context.getResources().getDrawable(
                R.drawable.ar_sound_play_animation);
        ivSoundItemHorn.setImageDrawable(animationDrawable);
        animationDrawable.stop();
        animationDrawable.setOneShot(false);
    }


    public void onCompleteUI() {
        animationDrawable.stop();
        resetDrawable();
        invalidate();
    }


    //---------------------------------------------------------播放回调

    private IAudioPlayListener iAudioPlayListener = new IAudioPlayListener() {

        @Override
        public void onStart(Uri var1) {
            //开播（一般是开始语音消息动画）
            onStartUI();

        }

        @Override
        public void onStop(Uri var1) {
            //停播（一般是停止语音消息动画）

            onStopUI();
        }

        @Override
        public void onComplete(Uri var1) {
            //播完（一般是停止语音消息动画）
            onCompleteUI();

        }
    };


    private CommonSoundItemViewListener listener;

    public interface CommonSoundItemViewListener {
        void onDelete(UploadVoice uploadVoice);
    }

    public void setCommonSoundItemViewListener(CommonSoundItemViewListener listener) {
        this.listener = listener;

    }
}
