package com.dayi.fragment.child;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.R2;
import com.dayi.bean.UploadVoice;
import com.dayi.view.CommonSoundItemView;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;

import butterknife.BindView;

public class QuestionFragment extends BaseAppFragment {
    @BindView(R2.id.lin_image)
    LinearLayout linImage;
    @BindView(R2.id.lin_voice)
    LinearLayout linVoice;
    @BindView(R2.id.et_cmmt)
    AppCompatEditText etCmmt;
    @BindView(R2.id.lin_word)
    LinearLayout linWord;
    private String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=262644851,3907824053&fm=26&gp=0.jpg";

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        instertImage();
        instertVoice();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question;
    }

    private void instertImage() {

        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_ask_image, null);
        ImageView imageView = view.findViewById(R.id.iv_image);

        Glide.with(this)
                .load(url)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);

        linImage.addView(view);


    }

    private void instertVoice() {


        UploadVoice uploadVoice = new UploadVoice();
        uploadVoice.setPlayUrl("http://pu00k0ssj.bkt.clouddn.com/myRecord.aac");

        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(getContext());
        commonSoundItemView.setAudioEntity(uploadVoice);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = DisplayUtil.dip2px(getContext(), 16);
        commonSoundItemView.setLayoutParams(params);

        linVoice.addView(commonSoundItemView);

    }


}
