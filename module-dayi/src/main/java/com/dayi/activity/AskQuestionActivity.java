package com.dayi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.AudioEntity;
import com.dayi.bean.UploadImage;
import com.dayi.bean.UploadVoice;
import com.dayi.view.CmmtWordPopup;
import com.dayi.view.CommonSoundItemView;
import com.dayi.view.MyRecordAudioView;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.utils.audio.AudioPlayManager;
import com.lib.fastkit.utils.audio.AudioRecordManager;
import com.lib.fastkit.utils.audio.IAudioPlayListener;
import com.lib.fastkit.utils.audio.IAudioRecordListener;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.zyyoona7.popup.EasyPopup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 学生提问
 */


@Route(path = ARouterPathUtils.Dayi_AskQuestionActivity)
public class AskQuestionActivity extends BaseAppActivity {
    @BindView(R2.id.lin_Image)
    LinearLayout linImage;

    @BindView(R2.id.lin_word)
    LinearLayout linWord;

    @BindView(R2.id.lin_voice)
    LinearLayout linVoice;
    @BindView(R2.id.iv_take_Photo)
    ImageView ivTakePhoto;

    @BindView(R2.id.lin_voice_write)
    LinearLayout linVoiceWrite;
    @BindView(R2.id.iv_voice)
    ImageView ivVoice;

    @BindView(R2.id.v_line)
    View vLine;
    @BindView(R2.id.iv_write)
    ImageView ivWrite;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @BindView(R2.id.et_cmmt)
    AppCompatEditText etCmmt;
    @BindView(R2.id.tv_num)
    TextView tvNum;

    private MyRecordAudioView recordAudioView;

    @Override
    protected void onCreateView() {
        mainHandler = new Handler();

        initView();
        initVoicePopu();
        initCmmtWordPop();
        initRecordManager();

    }

    private void initView() {
        etCmmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCmmtWordPop(etCmmt);
            }
        });
    }

    private File mAudioDir;

    private void initRecordManager() {
        //默认时长60秒
        AudioRecordManager.getInstance(this).setMaxVoiceDuration(120);
        mAudioDir = new File(Environment.getExternalStorageDirectory(), "LQR_AUDIO");
        if (!mAudioDir.exists()) {
            mAudioDir.mkdirs();
        }
        AudioRecordManager.getInstance(this).setAudioSavePath(mAudioDir.getAbsolutePath());

        AudioRecordManager.getInstance(this).setAudioRecordListener(iAudioRecordListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask_question;
    }


    private EasyPopup voicePopu;

    private void initVoicePopu() {
        voicePopu = EasyPopup.create()
                .setContext(this)
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


    private TextView tv_time;

    private ImageView iv_voice;

    private void initRecordView(View view) {
        iv_voice = view.findViewById(R.id.iv_voice);
        iv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initWirteAndVoiceUI();
                voicePopu.dismiss();
            }
        });

        tv_time = view.findViewById(R.id.tv_time);
        recordAudioView = view.findViewById(R.id.record_view);
        recordAudioView.setRecordAudioViewListener(new MyRecordAudioView.RecordAudioViewListener() {
            @Override
            public void onRecordViewStart() {
                //点击开始录音

                AudioRecordManager.getInstance(AskQuestionActivity.this).startRecord();

            }

            @Override
            public void onRecordViewStop() {

                //停止录音
                AudioRecordManager.getInstance(AskQuestionActivity.this).stopRecord();
            }
        });


    }

    public void showVoicePopu(View view) {
        // int offsetX = 0;
        // int offsetY = 0;
        //requestPopu.showAtAnchorView(view, YGravity.CENTER, XGravity.CENTER, offsetX, offsetY);

        voicePopu.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    private CmmtWordPopup cmmtWordPopup;

    private String contentWord;

    private void initCmmtWordPop() {


        cmmtWordPopup = CmmtWordPopup.create(this)

                .setWordPopupInterface(new CmmtWordPopup.WordPopupInterface() {
                    @Override
                    public void onVoiceClick() {

                        contentWord = "";
                        initWirteAndVoiceUI();
                        cmmtWordPopup.dismiss();
                    }

                    @Override
                    public void onSendClick(String content) {

                        etCmmt.setText(content);
                        tvNum.setText(content.length() + "/" + 500);
                        contentWord = content;
                        initWordUI();
                        cmmtWordPopup.dismiss();
                    }
                })

                .setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        KeyboardUtils.toggleSoftInput(AskQuestionActivity.this);
                    }
                })
                .apply();

    }

    private void initWirteAndVoiceUI() {

        linVoiceWrite.setVisibility(View.VISIBLE);

        ivVoice.setVisibility(View.VISIBLE);
        vLine.setVisibility(View.VISIBLE);
        ivWrite.setVisibility(View.VISIBLE);

        audioBeanList.clear();
        linVoice.removeAllViews();
        linVoice.setVisibility(View.GONE);
        etCmmt.setText("");
        linWord.setVisibility(View.GONE);
        cmmtWordPopup.setText("");

    }

    private void initWordUI() {
        if (contentWord.length() > 0) {

            linVoiceWrite.setVisibility(View.GONE);
            linWord.setVisibility(View.VISIBLE);
        } else {
            linWord.setVisibility(View.GONE);
            linVoiceWrite.setVisibility(View.VISIBLE);
        }
    }


    private void showCmmtWordPop(View view) {
        cmmtWordPopup.showSoftInput().showAtLocation(view, Gravity.BOTTOM, 0, 0);


    }

    //private List<LocalMedia> selectMedia = new ArrayList<>();

    @OnClick({R2.id.iv_take_Photo, R2.id.iv_voice, R2.id.iv_write, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_take_Photo) {

            PhotoUtil.normalSelectPictureByCode(this, new ArrayList<LocalMedia>(), 1, PhotoUtil.ASK_IMAGE);


        } else if (i == R.id.iv_voice) {
            showVoicePopu(view);


        } else if (i == R.id.iv_write) {
            showCmmtWordPop(view);

        } else if (i == R.id.btn_sure) {

        }
    }


    @Override
    protected void onStop() {
        super.onStop();


        cmmtWordPopup.dismiss();
    }

    //-----------------------------------------------------------------------------------图片选择回调


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.ASK_IMAGE:

                    // 图片、视频、音频选择结果回调


                    LocalMedia media = PictureSelector.obtainMultipleResult(data).get(0);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //linImage.removeAllViews();


                    if (media.isCompressed()) {

                        final String compressPath = media.getCompressPath();
                        //上传文件

                        showLog("路径检测:" + compressPath);

                        if (uploadImageMap.get(compressPath) == null) {
                            uploadFile(compressPath, TYPE_IMAGE);
                        }


                        //插入图片布局
                        instertImage(media, compressPath);


                    }


                    updateImageUI();


                    break;

            }


        }
    }

    private void instertImage(final LocalMedia media, final String compressPath) {
        final View view = LayoutInflater.from(this).inflate(R.layout.item_ask_image, null);

        final FrameLayout f_Image_bg = view.findViewById(R.id.f_Image_bg);
        ImageView iv_delete_Image = view.findViewById(R.id.iv_delete_Image);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                f_Image_bg.setVisibility(View.VISIBLE);

                return true;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_Image_bg.setVisibility(View.GONE);
            }
        });

        iv_delete_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linImage.removeView(view);

                uploadImageMap.remove(compressPath);

                updateImageUI();
            }
        });
        linImage.addView(view);

        //填充数据
        UploadImage uploadImage = uploadImageMap.get(compressPath);
        if (uploadImage == null) {
            uploadImage = new UploadImage();

        }
        uploadImage.setView(view);
        uploadImageMap.put(compressPath, uploadImage);


        ImageView imageView = view.findViewById(R.id.iv_image);

        Glide.with(this)
                .load(compressPath)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);
    }

    private void updateImageUI() {


        showLog("Map大小:" + uploadImageMap.size());

        if (uploadImageMap.size() == 1) {
            ivTakePhoto.setVisibility(View.VISIBLE);
            ivTakePhoto.setImageResource(R.mipmap.icon_add_question);
        } else if (uploadImageMap.size() == 2) {
            ivTakePhoto.setVisibility(View.GONE);
        } else {
            ivTakePhoto.setVisibility(View.VISIBLE);
            ivTakePhoto.setImageResource(R.mipmap.icon_camera_question);
        }
    }


    //--------------------------------------------------------------------------------------倒记时

    /**
     * 初始化计时器用来更新倒计时
     */
    private Handler mainHandler;

    private long recordTotalTime;

    private void startTimer() {
        mainHandler.postDelayed(runnable, 1000);
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
        tv_time.setText(string);

    }


    //------------------------------------------------------------------------------------语音回调

    private List<AudioEntity> audioBeanList = new ArrayList<>();

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
        }

        @Override
        public void onFinish(final Uri audioPath, int duration) {

            Log.e("======", "" + duration);

            uploadFile(audioPath.toString(), TYPE_VOICE);
            //录制结束
            recordTotalTime = 0;
            mainHandler.removeCallbacks(runnable);
            tv_time.setText("00:00");
            voicePopu.dismiss();

            AudioEntity audioEntity = new AudioEntity();
            audioEntity.setDuration(duration * 1000);
            audioEntity.setUrl(audioPath);
            /**
             * 插入音频UI
             */
            final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(AskQuestionActivity.this);
            commonSoundItemView.setAudioEntity(audioEntity);
            commonSoundItemView.setCommonSoundItemViewListener(new CommonSoundItemView.CommonSoundItemViewListener() {
                @Override
                public void onDelete(AudioEntity audioEntity) {
                    linVoice.removeView(commonSoundItemView);
                    audioBeanList.remove(audioEntity);


                    uploadVoiceMap.remove(audioPath.toString());

                    initVoiceUI();
                }


            });


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.topMargin = DisplayUtil.dip2px(AskQuestionActivity.this, 16);
            commonSoundItemView.setLayoutParams(params);

            linVoice.addView(commonSoundItemView);
            audioBeanList.add(audioEntity);

            initVoiceUI();


        }

        @Override
        public void onAudioDBChanged(int db) {
            //分贝改变

        }
    };

    private void initVoiceUI() {
        if (audioBeanList.size() == 1) {
            vLine.setVisibility(View.GONE);
            ivWrite.setVisibility(View.GONE);
            ivVoice.setVisibility(View.VISIBLE);

            linVoice.setVisibility(View.VISIBLE);

            linVoiceWrite.setVisibility(View.VISIBLE);
        } else if (audioBeanList.size() == 2) {
            ivVoice.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
            ivWrite.setVisibility(View.GONE);
            linVoiceWrite.setVisibility(View.GONE);
            linVoice.setVisibility(View.VISIBLE);
        } else {
            linVoice.setVisibility(View.GONE);
            vLine.setVisibility(View.VISIBLE);
            ivWrite.setVisibility(View.VISIBLE);
            ivVoice.setVisibility(View.VISIBLE);
            linVoiceWrite.setVisibility(View.VISIBLE);
        }
    }

    private void playAudio(Uri audioPath) {
        AudioPlayManager.getInstance().startPlay(AskQuestionActivity.this, audioPath, new IAudioPlayListener() {
            @Override
            public void onStart(Uri var1) {
                //开播（一般是开始语音消息动画）
            }

            @Override
            public void onStop(Uri var1) {
                //停播（一般是停止语音消息动画）
            }

            @Override
            public void onComplete(Uri var1) {
                //播完（一般是停止语音消息动画）
            }
        });
    }


    private Map<String, UploadImage> uploadImageMap = new HashMap<>();


    private Map<String, UploadVoice> uploadVoiceMap = new HashMap<>();

    private final int TYPE_IMAGE = 1;
    private final int TYPE_VOICE = 2;

    private void uploadFile(final String compressPath, final int type) {
        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(String s) {

                if (type == TYPE_IMAGE) {

                    UploadImage uploadImage = uploadImageMap.get(compressPath);
                    uploadImage.setUrl(s);
                    uploadImageMap.put(compressPath, uploadImage);

                    showLog("上传图片成功:" + s);

                }

                if (type == TYPE_VOICE) {

                    UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);

                    if (uploadVoice == null) {
                        uploadVoice = new UploadVoice();
                    }
                    uploadVoice.setUrl(s);

                    uploadVoiceMap.put(compressPath, uploadVoice);

                    showLog("上传语音成功:" + s);
                }


            }

            @Override
            public void onError(String e) {

                if (type == TYPE_IMAGE) {
                    UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);
                    linImage.removeView(uploadVoice.getView());
                    uploadVoiceMap.remove(compressPath);

                    updateImageUI();

                }

                if (type == TYPE_VOICE) {

                }


            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(this).getUserCache().getQiNiuToken());


    }
}
