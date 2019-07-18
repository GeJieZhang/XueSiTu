package com.dayi.activity;


import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.UploadVoice;
import com.dayi.view.CommonSoundItemView;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.zyyoona7.popup.EasyPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.Dayi_QustionDetailActivity)
public class QustionDetailActivity extends BaseAppActivity {

    @BindView(R2.id.tv_action)
    TextView tvAction;
    @BindView(R2.id.lin_image)
    LinearLayout linImage;
    @BindView(R2.id.lin_voice)
    LinearLayout linVoice;
    @BindView(R2.id.et_cmmt)
    AppCompatEditText etCmmt;
    @BindView(R2.id.lin_word)
    LinearLayout linWord;
    @BindView(R2.id.btn_sure)
    Button btnSure;
    private String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=262644851,3907824053&fm=26&gp=0.jpg";


    @Override
    protected void onCreateView() {
        initTitle();
        instertImage();
        instertVoice();

        initAnswerPopuPopu();
    }

    protected void initTitle() {

        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("问答")
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qustion_detail;
    }


    private void instertImage() {

        final View view = LayoutInflater.from(this).inflate(R.layout.item_ask_image, null);
        ImageView imageView = view.findViewById(R.id.iv_image);

        Glide.with(this)
                .load(url)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);

        linImage.addView(view);


    }

    private void instertVoice() {


        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(QustionDetailActivity.this);
        commonSoundItemView.setAudioEntity(new UploadVoice());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = DisplayUtil.dip2px(QustionDetailActivity.this, 16);
        commonSoundItemView.setLayoutParams(params);

        linVoice.addView(commonSoundItemView);

    }


    @OnClick({R2.id.et_cmmt, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.et_cmmt) {
        } else if (i == R.id.btn_sure) {

            showAnswerPopuPopu(view);
        }
    }


    //--------------------------------------------------------------------------------分享
    private EasyPopup answerPopu;

    private LinearLayout lin_video;
    private LinearLayout lin_voice;

    private void initAnswerPopuPopu() {
        answerPopu = EasyPopup.create()
                .setContext(this)
                .setContentView(R.layout.popup_answer)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {


                        lin_video = view.findViewById(R.id.lin_video);
                        lin_voice = view.findViewById(R.id.lin_voice);
                        lin_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        });

                        lin_voice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    public void showAnswerPopuPopu(View view) {

        answerPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
