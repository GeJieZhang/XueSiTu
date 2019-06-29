package com.live.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.dialog.arrow.TriangleDrawable;
import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.picture_select.PhotoUtil;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.live.bean.control.RoomControlBean;
import com.live.view.CmmtPopup;
import com.luck.picture.lib.entity.LocalMedia;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R2.id.f_quality)
    FrameLayout fQuality;


    private RoomControlBean roomControlBean;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        initQualityPopup();
        initUserListPopup();
        initCmmtPop();
        initIconState();


    }

    @Override
    public void onResume() {
        super.onResume();

        checkMeuState();
    }

    private void initIconState() {
        roomControlBean = MainRoomActivity.roomControlBean;
        ivCamera.setImageResource(roomControlBean.isDefault_camera() ? R.mipmap.icon_camera_on : R.mipmap.icon_camera_off);
        ivVoice.setImageResource(roomControlBean.isDefault_voice() ? R.mipmap.icon_voice_on : R.mipmap.icon_voice_off);
        ivRotate.setImageResource(roomControlBean.isDefault_rotate() ? R.mipmap.icon_rotate_h : R.mipmap.icon_rotate_w);
        ivClass.setImageResource(roomControlBean.isDefault_class() ? R.mipmap.icon_class_off2 : R.mipmap.icon_class_on2);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_control;
    }


    @OnClick({R2.id.iv_quality, R2.id.iv_quit, R2.id.iv_share, R2.id.iv_pen, R2.id.iv_ppt, R2.id.iv_list, R2.id.iv_class, R2.id.iv_chat, R2.id.iv_voice, R2.id.iv_camera, R2.id.iv_rotate, R2.id.iv_menu})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_quit) {

            NormalDialog.getInstance()
                    .setContent("确认要退出直播间么？")
                    .setWidth(DisplayUtil.dip2px(getContext(), 300))
                    .setSureListener(new NormalDialog.SurelListener() {
                        @Override
                        public void onSure() {

                            getActivity().finish();

                        }
                    })
                    .show(getFragmentManager());
        } else if (i == R.id.iv_share) {
        } else if (i == R.id.iv_pen) {
        } else if (i == R.id.iv_ppt) {
        } else if (i == R.id.iv_list) {
            showUserListPopup(view);
        } else if (i == R.id.iv_class) {


            if (roomControlBean.isDefault_class()) {
                roomControlBean.setDefault_class(false);
            } else {
                roomControlBean.setDefault_class(true);
            }


        } else if (i == R.id.iv_chat) {
            showCmmtPop(view);
        } else if (i == R.id.iv_voice) {


            if (roomControlBean.isDefault_voice()) {
                roomControlBean.setDefault_voice(false);
            } else {
                roomControlBean.setDefault_voice(true);
            }

            initIconState();


            if (listener != null) {
                listener.onVoiceClick();
            }

        } else if (i == R.id.iv_camera) {

            if (roomControlBean.isDefault_camera()) {
                roomControlBean.setDefault_camera(false);
            } else {
                roomControlBean.setDefault_camera(true);
            }

            initIconState();
            if (listener != null) {
                listener.onCameraClick();
            }

        } else if (i == R.id.iv_quality) {
            showQualityPopup(view);

        } else if (i == R.id.iv_rotate) {


            if (roomControlBean.isDefault_rotate()) {
                roomControlBean.setDefault_rotate(false);
            } else {
                roomControlBean.setDefault_rotate(true);
            }

            initIconState();


            //判断当前是否为横屏,判断是否旋转
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }


        } else if (i == R.id.iv_menu) {


            checkMeuState();


        }
    }

    private void checkMeuState() {
        if (roomControlBean.isDefault_menu()) {
            ivPen.setVisibility(View.GONE);
            ivPpt.setVisibility(View.GONE);
            ivList.setVisibility(View.GONE);
            fQuality.setVisibility(View.GONE);
            ivRotate.setVisibility(View.GONE);
            ivCamera.setVisibility(View.GONE);
            ivVoice.setVisibility(View.GONE);
            ivMenu.setImageResource(R.mipmap.icon_menu_open);
            roomControlBean.setDefault_menu(false);
        } else {
            ivPen.setVisibility(View.VISIBLE);
            ivPpt.setVisibility(View.VISIBLE);
            ivList.setVisibility(View.VISIBLE);
            fQuality.setVisibility(View.VISIBLE);
            ivRotate.setVisibility(View.VISIBLE);
            ivCamera.setVisibility(View.VISIBLE);
            ivVoice.setVisibility(View.VISIBLE);
            ivMenu.setImageResource(R.mipmap.icon_menu_close);
            roomControlBean.setDefault_menu(true);
        }
    }


    //--------------------------------------------------------------------------------清晰度切换弹出层
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


    //--------------------------------------------------------------------------------用户列表弹出层
    private EasyPopup userListPopup;

    private void initUserListPopup() {
        userListPopup = EasyPopup.create()
                .setContext(getContext())
                .setContentView(R.layout.popup_user_list)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        View arrowView = view.findViewById(R.id.v_arrow);
                        arrowView.setBackground(new TriangleDrawable(TriangleDrawable.BOTTOM, Color.parseColor("#ffffff")));

                        initUserList(view);


                    }
                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }


    private void showUserListPopup(View view) {
        int offsetX = 0;
        int offsetY = 0;
        userListPopup.showAtAnchorView(view, YGravity.ABOVE, XGravity.ALIGN_RIGHT, offsetX, offsetY);
    }


    List<String> list = new ArrayList<>();

    private void initUserList(View view) {

        list.add("sdf");
        list.add("sdff");
        RecyclerView recyclerView = view.findViewById(R.id.rv_user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UserListAdapter userListAdapter = new UserListAdapter(getContext(), list);
        recyclerView.setAdapter(userListAdapter);

    }

    class UserListAdapter extends BaseAdapter<String> {

        public UserListAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_user_list;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

        }
    }

    //--------------------------------------------------------------------------------评论框弹出

    private CmmtPopup mCmmtPopup;

    private void initCmmtPop() {
        mCmmtPopup = CmmtPopup.create(getActivity())

                .setOnOkClickListener(new CmmtPopup.MyOkClickListener() {
                    @Override
                    public void onClick(View v, String content) {

                        mCmmtPopup.dismiss();

                        if (content.equals("")) {
                            showToast("内容不能为空!");
                            return;
                        }


                        EventBus.getDefault().post(new Event<>(1, content), EventBusTagUtils.RoomControlFragment);


                    }

                    @Override
                    public void onCameraClick() {

                        PhotoUtil.normalSelectPicture(getActivity(), listImage, 1);

                    }

                })


                .setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mCmmtPopup.hideSoftInput();
                    }
                })
                .apply();

    }


    private void showCmmtPop(View view) {
        mCmmtPopup.showSoftInput().showAtLocation(view, Gravity.BOTTOM, 0, 0);


    }


    public interface RoomControlFragmentListener {

        void onCameraClick();

        void onVoiceClick();

    }


    private RoomControlFragmentListener listener;

    public void setRoomControlFragmentListener(RoomControlFragmentListener roomControlFragmentListener) {

        this.listener = roomControlFragmentListener;

    }


    //=============================================================================================
    //==============================================================================图片选择=======
    //=============================================================================================
    List<LocalMedia> listImage = new ArrayList<>();


}
