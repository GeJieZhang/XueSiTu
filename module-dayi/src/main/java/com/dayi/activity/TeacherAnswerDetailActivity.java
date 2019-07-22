package com.dayi.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.UploadVoice;
import com.dayi.view.CommonSoundItemView;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 老师答案详情
 */
@Route(path = ARouterPathUtils.Dayi_TeacherAnswerDetailActivity)
public class TeacherAnswerDetailActivity extends BaseAppActivity {
    @BindView(R2.id.lin_video)
    LinearLayout linVideo;
    @BindView(R2.id.lin_image)
    LinearLayout linImage;
    @BindView(R2.id.lin_voice)
    LinearLayout linVoice;
    @BindView(R2.id.lin_word)
    LinearLayout linWord;
    private String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=262644851,3907824053&fm=26&gp=0.jpg";
    @Autowired(name = "questionId")
    String questionId;

    @Override
    protected void onCreateView() {
        ARouter.getInstance().inject(this);

        insertImage(0);
        insertImage(1);

        instertVoice();


        insertWord();
    }

    private void insertWord() {
        final View itemImage = LayoutInflater.from(this).inflate(R.layout.item_answer_wrod, null);

        linWord.addView(itemImage);
    }

    private void insertImage(int postion) {
        final View itemImage = LayoutInflater.from(this).inflate(R.layout.item_answer_image, null);

        ImageView imageView = itemImage.findViewById(R.id.iv_image);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CardView cardView = itemImage.findViewById(R.id.card);

        params.width = DisplayUtil.getScreenWidth(this) / 2 - DisplayUtil.dip2px(this, 20);
        params.height = DisplayUtil.dip2px(this, 112);
        if (postion == 0) {
            params.rightMargin = DisplayUtil.dip2px(this, 4);
        }
        if (postion == 1) {
            params.leftMargin = DisplayUtil.dip2px(this, 4);
        }
        cardView.setLayoutParams(params);

        Glide.with(this)
                .load(url)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);

        linImage.addView(itemImage);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_answer_detail;
    }


    private void instertVoice() {


        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(this);
        commonSoundItemView.setAudioEntity(new UploadVoice());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        params.topMargin = DisplayUtil.dip2px(this, 16);
        commonSoundItemView.setLayoutParams(params);

        linVoice.addView(commonSoundItemView);

    }

}
