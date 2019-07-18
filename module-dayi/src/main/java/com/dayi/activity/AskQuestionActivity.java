package com.dayi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.LongDef;
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
import com.dayi.bean.BaseHttpBean;
import com.dayi.bean.UploadImage;
import com.dayi.bean.UploadVoice;
import com.dayi.view.CmmtWordPopup;
import com.dayi.view.CommonSoundItemView;
import com.dayi.view.MyRecordAudioView;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.audio.AudioPlayManager;
import com.lib.fastkit.utils.audio.AudioRecordManager;
import com.lib.fastkit.utils.audio.IAudioPlayListener;
import com.lib.fastkit.utils.audio.IAudioRecordListener;
import com.lib.fastkit.utils.file.FileUtils;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.lib.view.navigationbar.NomalNavigationBar;
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
        initTitle();
        initView();
        initVoicePopu();
        initCmmtWordPop();
        initRecordManager();

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("")
                .builder();


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

    private String contentWord = "";

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

        uploadVoiceMap.clear();
        linVoiceWrite.setVisibility(View.VISIBLE);
        ivVoice.setVisibility(View.VISIBLE);
        vLine.setVisibility(View.VISIBLE);
        ivWrite.setVisibility(View.VISIBLE);
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


            if (uploadImageMap.size() <= 0) {
                showToast("请附上问题图片！");

                return;

            }

            if (uploadVoiceMap.size() <= 0 & contentWord.equals("")) {
                showToast("请对问题进行文字和语音描述！");

                return;

            }


            String image_description = "";
            int imageI = 0;
            for (Map.Entry<String, UploadImage> entry : uploadImageMap.entrySet()) {
                if (imageI != 0) {
                    image_description += ",";
                }
                image_description += entry.getValue().getUrl();
                imageI++;
            }


            String voice_description = "";
            int voiceI = 0;
            for (Map.Entry<String, UploadVoice> entry : uploadVoiceMap.entrySet()) {
                if (voiceI != 0) {
                    voice_description += ",";
                }
                voice_description += entry.getValue().getUrl();
                voiceI++;
            }


            HttpUtils.with(this)
                    .post()
                    .addParam("requestType", "QUESTION_INITIATE_QUESTION")
                    .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())

                    .addParam("image_description", image_description)
                    .addParam("voice_description", voice_description)

                    .addParam("text_description", contentWord)
                    .execute(new HttpDialogCallBack<BaseHttpBean>() {
                        @Override
                        public void onSuccess(BaseHttpBean result) {

                            if (result.getCode() == CodeUtil.CODE_200) {

                                finish();

                            }

                            showToast(result.getMsg());

                            showLog(result.toString());

                        }

                        @Override
                        public void onError(String e) {

                        }
                    });


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


                        //插入图片布局
                        instertImage(media, compressPath);

                        updateImageUI();

                        //上传文件

                        uploadFile(compressPath, TYPE_IMAGE);


                    }


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
        uploadImage.setPath(compressPath);
        uploadImageMap.put(compressPath, uploadImage);


        ImageView imageView = view.findViewById(R.id.iv_image);

        Glide.with(this)
                .load(compressPath)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);
    }


    /**
     * 刷新图片布局
     */
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

    //private List<AudioEntity> audioBeanList = new ArrayList<>();

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

            Log.e("======Vice时长", "" + duration);


            String realPath = FileUtils.getRealFilePath(AskQuestionActivity.this, audioPath);
            Log.e("======Vice路径", "" + realPath);
            //录制结束
            recordTotalTime = 0;
            mainHandler.removeCallbacks(runnable);
            tv_time.setText("00:00");
            voicePopu.dismiss();
            UploadVoice uploadVoice = uploadVoiceMap.get(realPath);

            if (uploadVoice == null) {
                uploadVoice = new UploadVoice();
            }

            uploadVoice.setDuration(duration * 1000);
            uploadVoice.setPath(realPath);
            /**
             * 插入音频UI
             */
            final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(AskQuestionActivity.this);
            commonSoundItemView.setAudioEntity(uploadVoice);
            commonSoundItemView.setCommonSoundItemViewListener(new CommonSoundItemView.CommonSoundItemViewListener() {
                @Override
                public void onDelete(UploadVoice audioEntity) {
                    linVoice.removeView(commonSoundItemView);

                    showLog("移除voice：" + audioEntity.getPath());
                    uploadVoiceMap.remove(audioEntity.getPath());
                    initVoiceUI();
                }


            });


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.topMargin = DisplayUtil.dip2px(AskQuestionActivity.this, 16);
            commonSoundItemView.setLayoutParams(params);

            linVoice.addView(commonSoundItemView);
            uploadVoice.setView(commonSoundItemView);
            uploadVoiceMap.put(realPath, uploadVoice);
            initVoiceUI();


            uploadFile(realPath, TYPE_VOICE);
        }

        @Override
        public void onAudioDBChanged(int db) {
            //分贝改变

        }
    };


    /**
     * 刷新音频布局
     */
    private void initVoiceUI() {
        showLog("Map大小:" + uploadVoiceMap.size());
        if (uploadVoiceMap.size() == 1) {
            vLine.setVisibility(View.GONE);
            ivWrite.setVisibility(View.GONE);
            ivVoice.setVisibility(View.VISIBLE);

            linVoice.setVisibility(View.VISIBLE);

            linVoiceWrite.setVisibility(View.VISIBLE);
        } else if (uploadVoiceMap.size() == 2) {
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

        showLog("上传文件路径" + compressPath);

        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(final String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (type == TYPE_IMAGE) {

                            UploadImage uploadImage = uploadImageMap.get(compressPath);

                            if (uploadImage == null) {
                                uploadImage = new UploadImage();
                            }
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
                });
            }

            @Override
            public void onError(String e) {

                showLog("文件上传失败:" + e);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (type == TYPE_IMAGE) {
                            UploadImage uploadImage = uploadImageMap.get(compressPath);

                            if (linImage.getChildCount() > 0 && uploadImage != null) {
                                linImage.removeView(uploadImage.getView());
                            }

                            uploadVoiceMap.remove(compressPath);


                            updateImageUI();
                            showToast("选择图片失败！");


                        }

                        if (type == TYPE_VOICE) {

                            UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);


                            if (linVoice.getChildCount() > 0 && uploadVoice != null) {
                                linVoice.removeView(uploadVoice.getView());
                            }

                            uploadVoiceMap.remove(compressPath);


                            initVoiceUI();

                            showToast("录制语音失败！");


                        }
                    }
                });

            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(this).getUserCache().getQiNiuToken());


    }
}
