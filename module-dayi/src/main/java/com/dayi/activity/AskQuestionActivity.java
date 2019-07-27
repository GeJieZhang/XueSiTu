package com.dayi.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.BaseHttpBean;
import com.dayi.bean.QuestionStateBean;
import com.dayi.bean.UploadImage;
import com.dayi.bean.UploadVoice;
import com.dayi.utils.pop.PayPopupUtils;
import com.dayi.utils.pop.RecordVoicePopupUtils;
import com.dayi.utils.pop.SubjectPopupUtils;
import com.dayi.utils.pop.WriteWordPopupUtils;
import com.dayi.view.CommonSoundItemView;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.file.FileUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
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
    @BindView(R2.id.tv_tips)
    TextView tv_tips;

    @Override
    protected void onCreateView() {

        initTitle();
        initView();
        initRecodVoiceUtils();

        initWordWritePopupUtils();


        initPayMoneyPopupUtils();

        requestpayMoneyState();
        initSubjectPopupUtils();
    }

    PayPopupUtils payPopupUtils;

    private void initPayMoneyPopupUtils() {
        payPopupUtils = new PayPopupUtils(this);
        payPopupUtils.setPayPopupUtilsListener(new PayPopupUtils.PayPopupUtilsListener() {
            @Override
            public void onSure() {


                subjectPopupUtils.showAnswerPopuPopu(AskQuestionActivity.this.getWindow().getDecorView());
                payPopupUtils.dismiss();
            }

            @Override
            public void onRechargeClick() {
                //去充值页面

                ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
            }
        });

    }

    SubjectPopupUtils subjectPopupUtils;

    private void initSubjectPopupUtils() {
        subjectPopupUtils = new SubjectPopupUtils(this);
        subjectPopupUtils.setGradeChooseListener(new SubjectPopupUtils.GradeChooseListener() {

            @Override
            public void onSure(int subjectId) {
                requestUplaodData(subjectId);
                subjectPopupUtils.dismiss();
            }
        });

    }


    private void requestpayMoneyState() {

        HttpUtils.with(this)
                .addParam("requestType", "QUESTION_USER_STATUS")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .execute(new HttpDialogCallBack<QuestionStateBean>() {
                    @Override
                    public void onSuccess(QuestionStateBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            payPopupUtils.setPopupValue(result.getObj().getAmount() + "", result.getObj().getTotal() + "");

                            tv_tips.setText(result.getObj().getMsg());
                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }

    private WriteWordPopupUtils writeWordPopupUtils;

    private void initWordWritePopupUtils() {

        writeWordPopupUtils = new WriteWordPopupUtils(this);

        writeWordPopupUtils.setWriteWordPopupUtilsListener(new WriteWordPopupUtils.WriteWordPopupUtilsListener() {
            @Override
            public void onVoiceIconClick() {
                contentWord = "";
                initWirteAndVoiceUI();
            }

            @Override
            public void onSendClick(String content) {
                etCmmt.setText(content);
                tvNum.setText(content.length() + "/" + 500);
                contentWord = content;
                initWordUI();
            }
        });

    }

    private RecordVoicePopupUtils recordVoicePopupUtils;

    private void initRecodVoiceUtils() {
        recordVoicePopupUtils = new RecordVoicePopupUtils(this);

        recordVoicePopupUtils.setRecordVoicePopupUtilsListener(new RecordVoicePopupUtils.RecordVoicePopupUtilsListener() {
            @Override
            public void onVoiceIconClick() {
                initWirteAndVoiceUI();
            }

            @Override
            public void onRecordFinish(Uri audioPath, int duration) {
                finishRecordDo(audioPath, duration);
            }
        });
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
                writeWordPopupUtils.showCmmtWordPop(v);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask_question;
    }


    private String contentWord = "";


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


    @OnClick({R2.id.iv_take_Photo, R2.id.iv_voice, R2.id.iv_write, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_take_Photo) {

            PhotoUtil.normalSelectPictureByCode(this, new ArrayList<LocalMedia>(), 1, PhotoUtil.ASK_IMAGE);


        } else if (i == R.id.iv_voice) {
            //showVoicePopu(view);

            recordVoicePopupUtils.showVoicePopu(view);


        } else if (i == R.id.iv_write) {


            writeWordPopupUtils.showCmmtWordPop(view);

        } else if (i == R.id.btn_sure) {


            payPopupUtils.showAnswerPopuPopu(view);
        }
    }


    private void requestUplaodData(int subjectId) {

        if (subjectId == 0) {

            showToast("请选择你的学科！");

            return;

        }


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
                .addParam("subject_id", subjectId)
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


    @Override
    protected void onStop() {
        super.onStop();

        writeWordPopupUtils.dismiss();
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


    private void finishRecordDo(Uri audioPath, int duration) {
        String realPath = FileUtils.getRealFilePath(AskQuestionActivity.this, audioPath);

        UploadVoice uploadVoice = uploadVoiceMap.get(realPath);

        if (uploadVoice == null) {
            uploadVoice = new UploadVoice();
        }

        uploadVoice.setDuration(duration);
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
                            uploadVoice.setPlayUrl(SharedPreferenceManager.getInstance(AskQuestionActivity.this).getUserCache().getQiNiuUrl() + s);
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
