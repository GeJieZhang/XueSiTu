package com.dayi.utils.pop;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dayi.R;
import com.dayi.activity.AskQuestionActivity;
import com.dayi.bean.UploadVoice;
import com.dayi.view.CommonSoundItemView;
import com.dayi.view.LineWaveVoiceView;
import com.dayi.view.MyRecordAudioView;
import com.lib.fastkit.utils.audio.AudioRecordManager;
import com.lib.fastkit.utils.audio.IAudioRecordListener;
import com.lib.fastkit.utils.file.FileUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.zyyoona7.popup.EasyPopup;

import java.io.File;

public class RecordVoicePopupUtils {


    private Activity activity;


    public RecordVoicePopupUtils(Activity ac) {
        this.activity = ac;

        initView();


    }

    private void initView() {
        mainHandler = new Handler();
        initRecordManager();
        initVoicePopu();
    }

    //-----------------------------------------------------------------------------------------录音
    private File mAudioDir;

    private void initRecordManager() {
        //默认时长60秒
        AudioRecordManager.getInstance(activity).setMaxVoiceDuration(120);
        mAudioDir = new File(Environment.getExternalStorageDirectory(), "LQR_AUDIO");
        if (!mAudioDir.exists()) {
            mAudioDir.mkdirs();
        }
        AudioRecordManager.getInstance(activity).setAudioSavePath(mAudioDir.getAbsolutePath());

        AudioRecordManager.getInstance(activity).setAudioRecordListener(iAudioRecordListener);
    }


    private IAudioRecordListener iAudioRecordListener = new IAudioRecordListener() {
        @Override
        public void initTipView() {
            //初始化提示视图

        }

        @Override
        public void setTimeoutTipView(int counter) {
            //设置倒计时提示视图
        }

        @Override
        public void setRecordingTipView() {
            //设置正在录制提示视图

        }

        @Override
        public void setAudioShortTipView() {
            //设置语音长度太短提示视图

        }

        @Override
        public void setCancelTipView() {
            //设置取消提示视图

        }

        @Override
        public void destroyTipView() {
            //销毁提示视图

        }

        @Override
        public void onStartRecord() {
            //开始录制
            startTimer();

            lineWaveVoiceView.startRecord();
        }

        @Override
        public void onFinish(final Uri audioPath, int duration) {

            lineWaveVoiceView.stopRecord();
            lineWaveVoiceView.setText("00:00");
            if (listener != null) {
                listener.onRecordFinish(audioPath, duration * 1000);
            }
            voicePopu.dismiss();

        }

        @Override
        public void onAudioDBChanged(float db) {

            LogUtil.e("分贝大小1:" + db);


            //分贝改变
            lineWaveVoiceView.refreshVoiceElement(db);
        }
    };


    //------------------------------------------------------------------------------------------popup

    private EasyPopup voicePopu;

    private void initVoicePopu() {
        voicePopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_voice)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {

                        initRecordView(view);


                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }


    private ImageView iv_voice;
    private MyRecordAudioView recordAudioView;

    private TextView tv_tips;

    private LineWaveVoiceView lineWaveVoiceView;

    private void initRecordView(View view) {

        lineWaveVoiceView = view.findViewById(R.id.horvoiceview);
        tv_tips = view.findViewById(R.id.tv_tips);
        iv_voice = view.findViewById(R.id.iv_voice);
        iv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onVoiceIconClick();

                }
                voicePopu.dismiss();
            }
        });


        recordAudioView = view.findViewById(R.id.record_view);
        recordAudioView.setRecordAudioViewListener(new MyRecordAudioView.RecordAudioViewListener() {
            @Override
            public void onRecordViewStart() {
                //点击开始录音

                AudioRecordManager.getInstance(activity).startRecord();
                tv_tips.setText("松开发送");
            }

            @Override
            public void onRecordViewStop() {

                //停止录音
                AudioRecordManager.getInstance(activity).stopRecord();

                stopTimer();

                tv_tips.setText("长按说话");
            }
        });


    }

    public void showVoicePopu(View view) {


        voicePopu.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //-----------------------------------------------------------------------------记时器

    /**
     * 初始化计时器用来更新倒计时
     */
    private Handler mainHandler;

    private long recordTotalTime = 0;

    private void startTimer() {
        mainHandler.postDelayed(runnable, 1000);
    }

    private void stopTimer() {
        mainHandler.removeCallbacks(runnable);

        recordTotalTime = 0;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recordTotalTime += 1000;
            updateTimerUI();

            mainHandler.postDelayed(runnable, 1000);
        }
    };

    private void updateTimerUI() {

        String string = TimeUtils.converLongTimeToStr(recordTotalTime);
        lineWaveVoiceView.setText(" " + string + " ");

        LogUtil.e(string);


    }


    private RecordVoicePopupUtilsListener listener;


    public void setRecordVoicePopupUtilsListener(RecordVoicePopupUtilsListener recordVoicePopupUtilsListener) {

        this.listener = recordVoicePopupUtilsListener;

    }

    public interface RecordVoicePopupUtilsListener {
        void onVoiceIconClick();

        void onRecordFinish(final Uri audioPath, int duration);

    }


}
