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
import com.dayi.bean.QuestionDetail;
import com.dayi.bean.UploadVoice;
import com.dayi.utils.pop.ZoomImagePopupUtils;
import com.dayi.view.CommonSoundItemView;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;

import java.util.List;

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
//        instertImage();
//        instertVoice();
    }


    @Override
    protected int getLayoutId() {

        return R.layout.fragment_question;
    }

    private void instertImage(final String url) {

        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_ask_image, null);
        ImageView imageView = view.findViewById(R.id.iv_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZoomImagePopupUtils zoomImagePopupUtils=new ZoomImagePopupUtils(getActivity());
                zoomImagePopupUtils.setZoomImage(url);
                zoomImagePopupUtils.showAnswerPopuPopu(v);
            }
        });

        Glide.with(this)
                .load(url)
                .apply(GlideConfig.getRoundOptions(10))
                .into(imageView);

        linImage.addView(view);


    }

    private void instertVoice(String url) {


        UploadVoice uploadVoice = new UploadVoice();
        uploadVoice.setPlayUrl(url);

        final CommonSoundItemView commonSoundItemView = new CommonSoundItemView(getContext());
        commonSoundItemView.isLocalVoice(false);

        commonSoundItemView.setAudioEntity(uploadVoice);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = DisplayUtil.dip2px(getContext(), 16);
        commonSoundItemView.setLayoutParams(params);

        linVoice.addView(commonSoundItemView);

    }

    /**
     * 更新问题
     *
     * @param question
     */
    public void updateData(QuestionDetail.ObjBean.QuestionBean question) {


        /**
         * 插入文字
         */
        String description = question.getDescription();
        linWord.setVisibility(View.GONE);
        if (!description.equals("")) {
            linWord.setVisibility(View.VISIBLE);
            etCmmt.setText(description);

        }

        /**
         * 插入图片
         */
        List<String> imageList = question.getImage();
        if (imageList.size() > 0) {
            for (String s : imageList) {
                instertImage(s);
            }

        }
        /**
         * 插入
         */

        List<String> voiceList = question.getVoice();

        if (voiceList.size() > 0) {
            for (String s : voiceList) {
                instertVoice(s);
            }
        }


    }


}
