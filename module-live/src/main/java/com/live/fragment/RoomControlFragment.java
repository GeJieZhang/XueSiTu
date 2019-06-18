package com.live.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lib.fastkit.views.dialog.arrow.TriangleDrawable;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

public class RoomControlFragment extends BaseAppFragment {
    @BindView(R2.id.iv_quit)
    ImageView ivQuit;
    @BindView(R2.id.iv_share)
    ImageView ivShare;
    @BindView(R2.id.iv_pen)
    ImageView ivPen;
    @BindView(R2.id.iv_ppt)
    ImageView ivPpt;
    @BindView(R2.id.iv_list)
    ImageView ivList;
    @BindView(R2.id.iv_class)
    ImageView ivClass;
    @BindView(R2.id.iv_chat)
    ImageView ivChat;
    @BindView(R2.id.iv_voice)
    ImageView ivVoice;
    @BindView(R2.id.iv_camera)
    ImageView ivCamera;
    @BindView(R2.id.iv_rotate)
    ImageView ivRotate;
    @BindView(R2.id.iv_menu)
    ImageView ivMenu;
    @BindView(R2.id.iv_quality)
    ImageView ivQuality;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        initQualityPopup();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_control;
    }


    @OnClick({R2.id.iv_quality, R2.id.iv_quit, R2.id.iv_share, R2.id.iv_pen, R2.id.iv_ppt, R2.id.iv_list, R2.id.iv_class, R2.id.iv_chat, R2.id.iv_voice, R2.id.iv_camera, R2.id.iv_rotate, R2.id.iv_menu})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_quit) {
            getActivity().finish();
        } else if (i == R.id.iv_share) {
        } else if (i == R.id.iv_pen) {
        } else if (i == R.id.iv_ppt) {
        } else if (i == R.id.iv_list) {
        } else if (i == R.id.iv_class) {
        } else if (i == R.id.iv_chat) {
        } else if (i == R.id.iv_voice) {
        } else if (i == R.id.iv_camera) {

        } else if (i == R.id.iv_quality) {
            showQualityPopup(view);

        } else if (i == R.id.iv_rotate) {


            //判断当前是否为横屏,判断是否旋转
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }


        } else if (i == R.id.iv_menu) {


            ivPen.setVisibility(View.GONE);
            ivPpt.setVisibility(View.GONE);
            ivList.setVisibility(View.GONE);
            ivQuality.setVisibility(View.GONE);
            ivRotate.setVisibility(View.GONE);
            ivCamera.setVisibility(View.GONE);
            ivVoice.setVisibility(View.GONE);


        }
    }

    private EasyPopup qualityPopup;

    private void initQualityPopup() {
        qualityPopup = EasyPopup.create()
                .setContext(getContext())
                .setContentView(R.layout.popup_quality)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        View arrowView = view.findViewById(R.id.v_arrow);
                        arrowView.setBackground(new TriangleDrawable(TriangleDrawable.BOTTOM, Color.parseColor("#ffffff")));
                    }
                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    private void showQualityPopup(View view) {
        int offsetX = 0;
        int offsetY = 0;
        qualityPopup.showAtAnchorView(view, YGravity.ABOVE, XGravity.ALIGN_RIGHT, offsetX, offsetY);
    }
}
