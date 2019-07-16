package com.dayi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.view.CmmtWordPopup;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 学生提问
 */


@Route(path = ARouterPathUtils.Dayi_AskQuestionActivity)
public class AskQuestionActivity extends BaseAppActivity {
    @BindView(R2.id.lin_Image)
    LinearLayout linImage;
    @BindView(R2.id.iv_take_Photo)
    ImageView ivTakePhoto;
    @BindView(R2.id.iv_voice)
    ImageView ivVoice;
    @BindView(R2.id.iv_write)
    ImageView ivWrite;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreateView() {

        initVoicePopu();
        initCmmtWordPop();

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


                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    public void showVoicePopu(View view) {
        // int offsetX = 0;
        // int offsetY = 0;
        //requestPopu.showAtAnchorView(view, YGravity.CENTER, XGravity.CENTER, offsetX, offsetY);

        voicePopu.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    private CmmtWordPopup cmmtWordPopup;

    private void initCmmtWordPop() {


        cmmtWordPopup = CmmtWordPopup.create(this)

                .setWordPopupInterface(new CmmtWordPopup.WordPopupInterface() {
                    @Override
                    public void onVoiceClick() {

                    }

                    @Override
                    public void onSendClick(String content) {

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


    private void showCmmtWordPop(View view) {
        cmmtWordPopup.showSoftInput().showAtLocation(view, Gravity.BOTTOM, 0, 0);


    }

    private List<LocalMedia> selectMedia = new ArrayList<>();

    @OnClick({R2.id.iv_take_Photo, R2.id.iv_voice, R2.id.iv_write, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_take_Photo) {

            PhotoUtil.normalSelectPictureByCode(this, selectMedia, 2, PhotoUtil.ASK_IMAGE);


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
                    selectMedia = PictureSelector.obtainMultipleResult(data);

                    //LocalMedia boardMedia = boardSelectList.get(0);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    linImage.removeAllViews();
                    for (LocalMedia media : selectMedia) {
                        if (media.isCompressed()) {

                            String compressPath = media.getCompressPath();

                            View view = LayoutInflater.from(this).inflate(R.layout.item_ask_image, null);

//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
//                        params.setMargins(0, DisplayUtil.dip2px(this, 16), 0, 0);
//                        view.setLayoutParams(params);
                            linImage.addView(view);

                            ImageView imageView = view.findViewById(R.id.iv_image);

                            Glide.with(this)
                                    .load(compressPath)
                                    .apply(GlideConfig.getRoundOptions(10))
                                    .into(imageView);


                        }
                    }


                    updateImageUI();


                    break;

            }


        }
    }

    private void updateImageUI() {

        if (selectMedia.size() == 1) {
            ivTakePhoto.setImageResource(R.mipmap.icon_add_question);
        } else if (selectMedia.size() == 2) {
            ivTakePhoto.setVisibility(View.GONE);
        } else {
            ivTakePhoto.setImageResource(R.mipmap.icon_camera_question);
        }
    }
}
