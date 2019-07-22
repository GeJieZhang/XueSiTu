package com.dayi.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.BaseHttpBean;
import com.dayi.bean.UploadImage;
import com.dayi.bean.UploadVideo;
import com.dayi.bean.UploadVoice;
import com.dayi.utils.pop.RecordVoicePopupUtils;
import com.dayi.view.CommonSoundItemView;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
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
 * 老师编辑答案
 */
@Route(path = ARouterPathUtils.Dayi_TeacherWriteAnswerActivity)
public class TeacherWriteAnswerActivity extends BaseAppActivity {

    @Autowired(name = "type")
    String type;
    @BindView(R2.id.iv_video)
    ImageView ivVideo;
    @BindView(R2.id.f_video)
    FrameLayout fVideo;
    @BindView(R2.id.lin_vedio)
    LinearLayout linVedio;
    @BindView(R2.id.lin_image)
    LinearLayout linImage;
    @BindView(R2.id.iv_image)
    ImageView ivImage;
    @BindView(R2.id.lin_voice)
    LinearLayout linVoice;
    @BindView(R2.id.iv_voice)
    ImageView ivVoice;
    @BindView(R2.id.lin_image_voice)
    LinearLayout linImageVoice;
    @BindView(R2.id.btn_sure)
    Button btnSure;
    private String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=262644851,3907824053&fm=26&gp=0.jpg";
    @Autowired(name = "questionId")
    String questionId;

    @Override
    protected void onCreateView() {

        ARouter.getInstance().inject(this);
        initTitle();
        initType();

        initRecodVoiceUtils();


    }

    /**
     * 初始化回答类型
     */
    private void initType() {
        if (type.equals("1")) {
            //视频
            linVedio.setVisibility(View.VISIBLE);
            linImageVoice.setVisibility(View.GONE);

        } else {
            //图片语音
            linVedio.setVisibility(View.GONE);
            linImageVoice.setVisibility(View.VISIBLE);

        }
    }

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("问答")
                .builder();


    }

    private RecordVoicePopupUtils recordVoicePopupUtils;

    private void initRecodVoiceUtils() {
        recordVoicePopupUtils = new RecordVoicePopupUtils(this);

        recordVoicePopupUtils.setRecordVoicePopupUtilsListener(new RecordVoicePopupUtils.RecordVoicePopupUtilsListener() {
            @Override
            public void onVoiceIconClick() {

            }

            @Override
            public void onRecordFinish(Uri audioPath, int duration) {
                finishRecordDo(audioPath, duration);
            }
        });
    }

    private void finishRecordDo(Uri audioPath, int duration) {

        String path = audioPath.getPath();

        showLog("语音路径:" + path);


        insertVoice(path);

    }

    private void insertVoice(String path) {
        UploadVoice uploadVoice = uploadVoiceMap.get(path);

        if (uploadVoice == null) {
            uploadVoice = new UploadVoice();
        }
        uploadVoice.setPath(path);


        /**
         * 填充布局
         */
        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(this);

        commonSoundItemView.setCommonSoundItemViewListener(new CommonSoundItemView.CommonSoundItemViewListener() {
            @Override
            public void onDelete(UploadVoice uploadVoice) {

                removeVoice(uploadVoice.getPath());

            }
        });
        commonSoundItemView.setAudioEntity(uploadVoice);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        params.topMargin = DisplayUtil.dip2px(this, 16);
        commonSoundItemView.setLayoutParams(params);
        linVoice.addView(commonSoundItemView);

        /**
         * 填充数据到Map
         */
        uploadVoice.setView(commonSoundItemView);
        uploadVoiceMap.put(path, uploadVoice);

        /**
         * 更新UI
         */
        updateVoiceUI();
    }

    private void updateVoiceUI() {

        if (uploadVoiceMap.size() == 1) {

            ivVoice.setVisibility(View.GONE);

        } else {
            ivVoice.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_write_answer;
    }


    @OnClick({R2.id.iv_video, R2.id.iv_image, R2.id.iv_voice, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_video) {

            PhotoUtil.recordVideoByCode(this, new ArrayList<LocalMedia>(), 1, PhotoUtil.ASK_VIDEO);

        } else if (i == R.id.iv_image) {


            PhotoUtil.normalSelectPictureByCode(this, new ArrayList<LocalMedia>(), 1, PhotoUtil.ASK_IMAGE_ANSWER);


        } else if (i == R.id.iv_voice) {
            recordVoicePopupUtils.showVoicePopu(view);

        } else if (i == R.id.btn_sure) {

            requestUplaodData();

        }
    }


    private TextView videoProgress;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // 图片、视频、音频选择结果回调
            LocalMedia media = PictureSelector.obtainMultipleResult(data).get(0);
            switch (requestCode) {

                case PhotoUtil.ASK_IMAGE_ANSWER:

                    if (media.isCompressed()) {
                        String imagePath = media.getCompressPath();
                        insertImage(imagePath);


                    }


                    break;


                case PhotoUtil.ASK_VIDEO:


                    final String path = media.getPath();

                    insertVideo(path);


                    break;

            }


        }
    }


    private Map<String, UploadVideo> uploadVideoMap = new HashMap<>();
    private Map<String, UploadImage> uploadImageMap = new HashMap<>();
    private Map<String, UploadVoice> uploadVoiceMap = new HashMap<>();
    private final int TYPE_IMAGE = 1;
    private final int TYPE_VOICE = 2;
    private final int TYPE_VIDEO = 3;

    private void uploadFile(final String compressPath, final int type) {

        showLog("上传文件路径" + compressPath);

        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {


                if (type == TYPE_IMAGE) {


                }

                if (type == TYPE_VOICE) {


                }

                if (type == TYPE_VIDEO) {

                    if (videoProgress != null) {
                        videoProgress.setText(progress + "%");
                    }

                }


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
                            uploadImage.setPlayUrl(SharedPreferenceManager.getInstance(TeacherWriteAnswerActivity.this).getUserCache().getQiNiuUrl() + s);
                            uploadImageMap.put(compressPath, uploadImage);

                        }

                        if (type == TYPE_VOICE) {
                            UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);
                            if (uploadVoice == null) {
                                uploadVoice = new UploadVoice();
                            }

                            uploadVoice.setUrl(s);
                            uploadVoice.setPlayUrl(SharedPreferenceManager.getInstance(TeacherWriteAnswerActivity.this).getUserCache().getQiNiuUrl() + s);
                            uploadVoiceMap.put(compressPath, uploadVoice);


                        }

                        if (type == TYPE_VIDEO) {


                            /**
                             * 将上传后的文件路径保存到Map中
                             */
                            UploadVideo uploadVideo = uploadVideoMap.get(compressPath);
                            if (uploadVideo == null) {
                                uploadVideo = new UploadVideo();
                            }

                            uploadVideo.setUrl(s);
                            uploadVideo.setPalyUrl(SharedPreferenceManager.getInstance(TeacherWriteAnswerActivity.this).getUserCache().getQiNiuUrl() + s);
                            uploadVideoMap.put(compressPath, uploadVideo);

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
                            removeImage(compressPath);


                        }

                        if (type == TYPE_VOICE) {


                            removeVoice(compressPath);


                        }

                        if (type == TYPE_VIDEO) {
                            /**
                             *上传失败后需要删除之前的布局
                             */
                            UploadVideo uploadVideo = uploadVideoMap.get(compressPath);
                            if (uploadVideo == null) {
                                uploadVideo = new UploadVideo();
                            }

                            View view = uploadVideo.getView();

                            fVideo.removeView(view);

                            uploadVideoMap.remove(compressPath);
                            /**
                             * 更新UI
                             */
                            updateVideoUI();


                        }
                    }
                });

            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(this).getUserCache().getQiNiuToken());


    }


    /**
     * 插入视频布局
     *
     * @param path
     */
    private void insertVideo(final String path) {
        /**
         * 填充布局
         */
        final View videoView = LayoutInflater.from(this).inflate(R.layout.item_answer_video, null);

        final FrameLayout f_Image_bg = videoView.findViewById(R.id.f_Image_bg);

        ImageView iv_delete_Image = videoView.findViewById(R.id.iv_delete_Image);

        videoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                f_Image_bg.setVisibility(View.VISIBLE);

                return true;
            }
        });

        iv_delete_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UploadVideo uploadVideo = uploadVideoMap.remove(path);

                if (uploadVideo != null) {
                    fVideo.removeView(uploadVideo.getView());
                }

                uploadVideoMap.remove(path);

                updateVideoUI();

            }
        });

        ImageView iv_image = videoView.findViewById(R.id.iv_image);

        videoProgress = videoView.findViewById(R.id.tv_progress);

        Glide.with(this)
                .load(path)
                .apply(GlideConfig.getRoundVdieoOptions(10))
                .into(iv_image);

        fVideo.addView(videoView);


        /**
         * 填充布局信息
         */
        UploadVideo uploadVideo = uploadVideoMap.get(path);
        if (uploadVideo == null) {
            uploadVideo = new UploadVideo();
        }

        uploadVideo.setView(videoView);
        uploadVideo.setPath(path);
        uploadVideoMap.put(path, uploadVideo);

        /**
         * 更新UI
         */
        updateVideoUI();
        /**
         * 上传文件
         */
        uploadFile(path, TYPE_VIDEO);
    }

    /**
     * 插入图片布局
     *
     * @param imagePath
     */
    private void insertImage(final String imagePath) {
        /**
         * 填充布局
         */
        final View view = LayoutInflater.from(this).inflate(R.layout.item_ask_image, null);


        final FrameLayout f_Image_bg = view.findViewById(R.id.f_Image_bg);

        final ImageView iv_delete_Image = view.findViewById(R.id.iv_delete_Image);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                f_Image_bg.setVisibility(View.VISIBLE);

                return true;
            }
        });

        iv_delete_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImage(imagePath);
            }
        });

        ImageView imageView = view.findViewById(R.id.iv_image);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CardView cardView = view.findViewById(R.id.card);

        params.width = DisplayUtil.getScreenWidth(this) / 2 - DisplayUtil.dip2px(this, 20);
        params.height = DisplayUtil.dip2px(this, 112);
        int margin = DisplayUtil.dip2px(this, 2);

        params.setMargins(margin, margin, margin, margin);

        cardView.setLayoutParams(params);

        Glide.with(this)
                .load(imagePath)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);

        linImage.addView(view);


        /**
         * 保存数据到Map
         */
        UploadImage uploadImage = uploadImageMap.get(imagePath);
        if (uploadImage == null) {
            uploadImage = new UploadImage();
        }
        uploadImage.setPath(imagePath);
        uploadImage.setView(view);
        uploadImageMap.put(imagePath, uploadImage);


        /**
         * 更新UI
         */
        updateImageUI();

        /**
         * 上传图片
         */

        uploadFile(imagePath, TYPE_IMAGE);
    }

    private void updateImageUI() {
        if (uploadImageMap.size() == 1) {

            ivImage.setImageResource(R.mipmap.icon_add_question);
            ivImage.setVisibility(View.VISIBLE);
        } else if (uploadImageMap.size() == 2) {
            ivImage.setVisibility(View.GONE);

        } else {
            ivImage.setVisibility(View.VISIBLE);
            ivImage.setImageResource(R.mipmap.icon_camera_question);
        }
    }

    private void updateVideoUI() {
        if (uploadVideoMap.size() == 1) {
            ivVideo.setVisibility(View.GONE);
        } else {

            ivVideo.setVisibility(View.VISIBLE);

        }
    }

    private void removeImage(String compressPath) {
        /**
         *上传失败后需要删除之前的布局
         */
        UploadImage uploadImage = uploadImageMap.get(compressPath);
        if (uploadImage == null) {
            uploadImage = new UploadImage();
        }

        View view = uploadImage.getView();

        linImage.removeView(view);

        uploadImageMap.remove(compressPath);
        /**
         * 更新UI
         */
        updateImageUI();
    }

    private void removeVoice(String compressPath) {
        /**
         *上传失败后需要删除之前的布局
         */
        UploadVoice uploadVoice = uploadVoiceMap.get(compressPath);
        if (uploadVoice == null) {
            uploadVoice = new UploadVoice();
        }

        View view = uploadVoice.getView();

        linVoice.removeView(view);

        uploadVoiceMap.remove(compressPath);
        /**
         * 更新UI
         */
        updateVoiceUI();
    }

    private void requestUplaodData() {

        String image_description = "";
        String voice_description = "";
        String video_description = "";

        String reply_type = "";

        if (type.equals("1")) {

            reply_type = "0";
            //视频
            if (uploadVideoMap.size() <= 0) {
                showToast("请附上解题视频！");

                return;
            }

            int videoI = 0;
            for (Map.Entry<String, UploadVideo> entry : uploadVideoMap.entrySet()) {
                if (videoI != 0) {
                    video_description += ",";
                }
                video_description += entry.getValue().getUrl();
                videoI++;
            }


        } else {
            reply_type = "1";

            if (uploadImageMap.size() <= 0) {
                showToast("请附上解题图片！");

                return;

            }


            int imageI = 0;
            for (Map.Entry<String, UploadImage> entry : uploadImageMap.entrySet()) {
                if (imageI != 0) {
                    image_description += ",";
                }
                image_description += entry.getValue().getUrl();
                imageI++;
            }

            if (uploadVoiceMap.size() <= 0) {
                showToast("请附上解题语音！");

                return;

            }

            int voiceI = 0;
            for (Map.Entry<String, UploadVoice> entry : uploadVoiceMap.entrySet()) {
                if (voiceI != 0) {
                    voice_description += ",";
                }
                voice_description += entry.getValue().getUrl();
                voiceI++;
            }


        }


        HttpUtils.with(this)
                .post()
                .addParam("requestType", "QUESTION_SUBMIT_REPLY")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("reply_type", reply_type)
                .addParam("voice_reply", voice_description)
                .addParam("image_reply", image_description)
                .addParam("video_reply", video_description)
                .addParam("question_id", questionId)

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
