package com.live.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herewhite.sdk.domain.Appliance;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.dialog.arrow.TriangleDrawable;
import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.live.bean.control.IMBean;
import com.live.bean.control.RoomControlBean;
import com.live.bean.control.WhiteBoradBean;
import com.live.bean.live.MyTrackInfo;
import com.live.view.CmmtPopup;
import com.luck.picture.lib.entity.LocalMedia;
import com.smart.colorview.normal.CircleColorView;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.live.fragment.ChatFragment.ACTION_TYPE2;
import static com.live.fragment.ChatFragment.ACTION_TYPE3;

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
    private String identity = "";
    private String token = "";

    private RoomControlBean roomControlBean;

    private View root;


    int colors[] = {R.color.c1, R.color.c2, R.color.c3, R.color.c4
            , R.color.c5, R.color.c6, R.color.c7, R.color.c8};

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        root = view;
        identity = SharedPreferenceManager.getInstance(getActivity()).getUserCache().getUserIdentity();
        token = SharedPreferenceManager.getInstance(getActivity()).getUserCache().getUserToken();
        roomControlBean = MainRoomActivity.roomControlBean;


        initQualityPopup();
        initUserListPopup();
        initCmmtPop();
        initIconState();

        initSharePopup();
        initRequestPopu();
        initToolPopup();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkMeuState();
    }

    private void initIconState() {


        ivCamera.setImageResource(roomControlBean.isDefault_camera() ? R.mipmap.icon_camera_on : R.mipmap.icon_camera_off);
        ivVoice.setImageResource(roomControlBean.isDefault_voice() ? R.mipmap.icon_voice_on : R.mipmap.icon_voice_off);
        ivRotate.setImageResource(roomControlBean.isDefault_rotate() ? R.mipmap.icon_rotate_h : R.mipmap.icon_rotate_w);

        if (identity.equals("1")) {
            //只有老师才显示
            ivClass.setImageResource(roomControlBean.isDefault_class() ? R.mipmap.icon_class_off2 : R.mipmap.icon_class_on2);
        } else {
            //学生隐藏
            ivClass.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_control;
    }


    @OnClick({R2.id.iv_quality, R2.id.iv_quit, R2.id.iv_share
            , R2.id.iv_pen, R2.id.iv_ppt, R2.id.iv_list, R2.id.iv_class
            , R2.id.iv_chat, R2.id.iv_voice, R2.id.iv_camera
            , R2.id.iv_rotate, R2.id.iv_menu})
    public void onViewClicked(final View view) {
        int i = view.getId();
        if (i == R.id.iv_quit) {
            if (listener != null) {
                listener.onLiveRoom();
            }


        } else if (i == R.id.iv_share) {

            showSharePopup(view);

        } else if (i == R.id.iv_pen) {

            showToolPopup(view);
        } else if (i == R.id.iv_ppt) {
        } else if (i == R.id.iv_list) {

            updateUserInfo();
            if (userList.size() > 0) {
                showUserListPopup(view);
            } else {
                showToast("用户为空");
            }


        } else if (i == R.id.iv_class) {


            if (roomControlBean.isDefault_class()) {
                roomControlBean.setDefault_class(false);
            } else {
                roomControlBean.setDefault_class(true);
            }


            requestOverClass();


        } else if (i == R.id.iv_chat) {
            showCmmtPop(view);
        } else if (i == R.id.iv_voice) {


            if (roomControlBean.isDefault_voice()) {

                requestCloseVoice();


            } else {

                requestOpenVoice();

            }


        } else if (i == R.id.iv_camera) {

            if (roomControlBean.isDefault_camera()) {

                requestCloseCamera();

            } else {

                requestOpenCamera();

            }

//            if (roomControlBean.isDefault_camera()) {
//                roomControlBean.setDefault_camera(false);
//            } else {
//                roomControlBean.setDefault_camera(true);
//            }
//
//            initIconState();
//            if (listener != null) {
//                listener.onCameraClick();
//            }

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

                MainRoomActivity.screenOrientation = MainRoomActivity.screenHorization;


            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                MainRoomActivity.screenOrientation = MainRoomActivity.screenVertical;

            }


            if (listener != null) {
                listener.onChangeRotate();
            }

        } else if (i == R.id.iv_menu) {


            checkMeuState();


        }
    }


    /**
     * 更新用户数据
     */
    public void updateUserInfo() {
        userList.clear();
        userList.addAll(MainRoomActivity.trackInfoMap.getOrderedValues());
        userListAdapter.notifyDataSetChanged();

    }


    private void checkMeuState() {
        if (roomControlBean.isDefault_menu()) {
            //ivPen.setVisibility(View.GONE);
            ivPpt.setVisibility(View.GONE);
            ivList.setVisibility(View.GONE);
            fQuality.setVisibility(View.GONE);
            ivRotate.setVisibility(View.GONE);
            ivCamera.setVisibility(View.GONE);
            ivVoice.setVisibility(View.GONE);
            ivMenu.setImageResource(R.mipmap.icon_menu_open);
            roomControlBean.setDefault_menu(false);
        } else {
            // ivPen.setVisibility(View.VISIBLE);
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


                        initQualityView(view);


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


    private FrameLayout f_quality1, f_quality2, f_quality3;

    private void initQualityView(View view) {


        f_quality1 = view.findViewById(R.id.f_quality1);
        f_quality2 = view.findViewById(R.id.f_quality2);
        f_quality3 = view.findViewById(R.id.f_quality3);

        f_quality1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onCameraClick(1);
                    qualityPopup.dismiss();
                }

            }
        });

        f_quality2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCameraClick(2);
                    qualityPopup.dismiss();
                }
            }
        });

        f_quality3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCameraClick(3);
                    qualityPopup.dismiss();
                }
            }
        });


    }

    //--------------------------------------------------------------------------------分享
    private EasyPopup sharePopu;

    private void initSharePopup() {
        sharePopu = EasyPopup.create()
                .setContext(getContext())
                .setContentView(R.layout.popup_share)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        View arrowView = view.findViewById(R.id.v_arrow);
                        arrowView.setBackground(new TriangleDrawable(TriangleDrawable.TOP, Color.parseColor("#ffffff")));
                    }
                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    private void showSharePopup(View view) {
        int offsetX = 0;
        int offsetY = 0;
        sharePopu.showAtAnchorView(view, YGravity.BELOW, XGravity.ALIGN_RIGHT, offsetX, offsetY);
    }


    //--------------------------------------------------------------------------------分享
    private EasyPopup requestPopu;

    private void initRequestPopu() {
        requestPopu = EasyPopup.create()
                .setContext(getContext())
                .setContentView(R.layout.popup_request)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        //View arrowView = view.findViewById(R.id.v_arrow);
                        //arrowView.setBackground(new TriangleDrawable(TriangleDrawable.TOP, Color.parseColor("#ffffff")));
                        initRequestView(view);

                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    public void showRequestPopu() {
        // int offsetX = 0;
        // int offsetY = 0;
        //requestPopu.showAtAnchorView(view, YGravity.CENTER, XGravity.CENTER, offsetX, offsetY);

        requestPopu.showAtLocation(root, Gravity.CENTER, 0, 0);
    }


    private List<IMBean> mRequestList = new ArrayList<>();

    private RequestAdapter requestAdapter;

    private void initRequestView(View view) {
        RecyclerView rv_request = view.findViewById(R.id.rv_request);
        requestAdapter = new RequestAdapter(getActivity(), mRequestList);
        rv_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_request.setAdapter(requestAdapter);

    }

    public void addRequest(IMBean imBean) {
        mRequestList.add(imBean);
        requestAdapter.notifyDataSetChanged();
    }


    private class RequestAdapter extends BaseAdapter<IMBean> {

        public RequestAdapter(Context context, List<IMBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_request;
        }

        @Override
        protected void toBindEmptyViewHolder(ViewHolder holder) {
            super.toBindEmptyViewHolder(holder);
            requestPopu.dismiss();


        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<IMBean> mData) {

            ImageView iv_head = holder.getView(R.id.iv_head);
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_action = holder.getView(R.id.tv_action);


            tv_name.setText(mData.get(position).getObject().getUserName());


            if (mData.get(position).getActionType() == 2) {
                tv_action.setText("请求打开麦克风!");
            }
            if (mData.get(position).getActionType() == 3) {
                tv_action.setText("请求打开摄像头!");
            }

            Glide.with(getActivity())
                    .load(mData.get(position).getObject().getUserIcon())
                    .apply(GlideConfig.getCircleOptions())
                    .into(iv_head);


            holder.getView(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int actionType = mData.get(position).getActionType();

                    if (actionType == ACTION_TYPE2) {
                        MainRoomActivity.chatFragment.requestBackOpenVoice(true, mData.get(position).getObject().getUserPhone());
                        mRequestList.remove(position);
                        notifyDataSetChanged();
                    }

                    if (actionType == ACTION_TYPE3) {
                        MainRoomActivity.chatFragment.requestBackOpenCamera(true, mData.get(position).getObject().getUserPhone());
                        mRequestList.remove(position);
                        notifyDataSetChanged();
                    }

                }
            });

            holder.getView(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int actionType = mData.get(position).getActionType();

                    if (actionType == ACTION_TYPE2) {
                        MainRoomActivity.chatFragment.requestBackOpenVoice(false, mData.get(position).getObject().getUserPhone());
                        mRequestList.remove(position);
                        notifyDataSetChanged();
                    }

                    if (actionType == ACTION_TYPE3) {
                        MainRoomActivity.chatFragment.requestBackOpenCamera(false, mData.get(position).getObject().getUserPhone());
                        mRequestList.remove(position);
                        notifyDataSetChanged();
                    }

                }
            });


        }
    }


    //--------------------------------------------------------------------------------画笔工具
    private EasyPopup toolPopu;

    private void initToolPopup() {
        //https://blog.csdn.net/lixpjita39/article/details/77800521

        colorList.clear();
        for (int color : colors) {
            colorList.add(color);
        }

        toolPopu = EasyPopup.create()
                .setContext(getContext())
                .setContentView(R.layout.popup_tool)

                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        View arrowView = view.findViewById(R.id.v_arrow);
                        arrowView.setBackground(new TriangleDrawable(TriangleDrawable.RIGHT, Color.parseColor("#ffffff")));
                        initToolView(view);
                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();


    }


    private ToolAdapter toolAdapter;

    private List<Integer> colorList = new ArrayList<>();
    private List<CircleColorView> colorViewList = new ArrayList<>();
    private RecyclerView toolRecyclerView;
    private SeekBar seekBar;
    private TextView tv_size;


    private ImageView iv_pencil;
    private ImageView iv_eraser;
    private ImageView iv_rectangle;
    private ImageView iv_circle;


    public void initToolIcon() {
        iv_pencil.setImageResource(R.mipmap.icon_color_default);
        iv_eraser.setImageResource(R.mipmap.icon_eraser_default);
        iv_rectangle.setImageResource(R.mipmap.icon_rectangle_default);
        iv_circle.setImageResource(R.mipmap.icon_size_default);

        WhiteBoradBean whiteBoradBean = MainRoomActivity.whiteBoradBean;

        if (whiteBoradBean.isPen()) {
            iv_pencil.setImageResource(R.mipmap.icon_color_selected);
        }

        if (whiteBoradBean.isCirle()) {
            iv_circle.setImageResource(R.mipmap.icon_size_selected);
        }
        if (whiteBoradBean.isEraser()) {
            iv_eraser.setImageResource(R.mipmap.icon_eraser_selected);
        }
        if (whiteBoradBean.isRectangular()) {
            iv_rectangle.setImageResource(R.mipmap.icon_rectangle_selected);
        }


    }

    private void initToolView(View view) {
        iv_pencil = view.findViewById(R.id.iv_pencil);
        iv_eraser = view.findViewById(R.id.iv_eraser);
        iv_rectangle = view.findViewById(R.id.iv_rectangle);
        iv_circle = view.findViewById(R.id.iv_circle);

        initToolIcon();


        toolRecyclerView = view.findViewById(R.id.rv_color);
        toolRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        toolRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        toolAdapter = new ToolAdapter(getContext(), colorList);
        toolRecyclerView.setAdapter(toolAdapter);
        seekBar = view.findViewById(R.id.seek_bar);

        seekBar.setProgress(MainRoomActivity.whiteBoradBean.getPenSize());
        tv_size = view.findViewById(R.id.tv_size);
        tv_size.setText("5");


        view.findViewById(R.id.lin_pencil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //画笔


                MainRoomActivity.whiteBoardFragment.setBoradTool(Appliance.PENCIL);


            }
        });

        view.findViewById(R.id.lin_eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //橡皮

                MainRoomActivity.whiteBoardFragment.setBoradTool(Appliance.ERASER);

            }
        });
        view.findViewById(R.id.lin_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清屏
                MainRoomActivity.getwhiteBoardRoom().cleanScene(true);

            }
        });

        view.findViewById(R.id.lin_rectangle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //矩形

                MainRoomActivity.whiteBoardFragment.setBoradTool(Appliance.RECTANGLE);

            }
        });

        view.findViewById(R.id.lin_move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移动工具

                MainRoomActivity.whiteBoardFragment.setBoradTool(Appliance.SELECTOR);


            }
        });


        view.findViewById(R.id.lin_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainRoomActivity.whiteBoardFragment.insertImage();

//                if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    //横屏
//                } else {
//
//                    double width = DisplayUtil.getScreenWidth(getContext());
//                    double height = DisplayUtil.getScreenHeight(getContext()) / 3;
//
//
//                    showLog(width + "-" + height);
//
//                    //竖屏
//                    MainRoomActivity.getwhiteBoardRoom().insertImage(new ImageInformationWithUrl(0d, 0d, 200d, 100d, "https://white-pan.oss-cn-shanghai.aliyuncs.com/40/image/mask.jpg"));
//                }

            }
        });
        view.findViewById(R.id.lin_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (listener != null) {

                    listener.onWihteBoradAdd();

                }


            }
        });


        view.findViewById(R.id.lin_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainRoomActivity.whiteBoardFragment.setBoradTool(Appliance.ELLIPSE);

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tv_size.setText(progress + "");


                MainRoomActivity.whiteBoardFragment.setPenProgress(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void showToolPopup(View view) {

        int offsetX = 0;
        int offsetY = DisplayUtil.dip2px(getActivity(), -40);
        toolPopu.showAtAnchorView(view, YGravity.ALIGN_TOP, XGravity.LEFT, offsetX, offsetY);
    }


    private class ToolAdapter extends BaseAdapter<Integer> {

        public ToolAdapter(Context context, List<Integer> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_popup_tool;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<Integer> mData) {

            final CircleColorView circleColorView = holder.getView(R.id.circleColorView);
            colorViewList.add(circleColorView);
            circleColorView.setCircleColor(getResources().getColor(mData.get(position)));
            circleColorView.setOutlineStrokeColor(getResources().getColor(mData.get(position)));

            circleColorView.setCircleSelected(false);
            if (MainRoomActivity.whiteBoradBean.getCirlerIndex() == position) {
                circleColorView.setCircleSelected(true);
            }


            circleColorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initColorViewState();
                    circleColorView.setCircleSelected(true);


                    MainRoomActivity.whiteBoardFragment.setPenColor(mData.get(position), position);


                }
            });


        }
    }

    /**
     * 复位所选颜色
     */
    private void initColorViewState() {
        for (CircleColorView circleColorView : colorViewList) {
            circleColorView.setCircleSelected(false);
        }
    }


    //------------------------------------------------------------------------------------API
//
//    private int toolStrokeWidth = 5;
//
//    private int toolTextSize = 5;
//
//    private String toolName = Appliance.PENCIL;
//
//    private int toolColor = R.color.base_money;
//
//    public void setTool() {
//
//        Room room = MainRoomActivity.getwhiteBoardRoom();
//        MemberState memberState = new MemberState();
//        memberState.setStrokeColor(ColorUtil.int2Rgb(getResources().getColor(toolColor)));
//        memberState.setCurrentApplianceName(toolName);
//        memberState.setStrokeWidth(toolStrokeWidth);
//        memberState.setTextSize(toolTextSize);
//
//        if (room != null) {
//            room.setMemberState(memberState);
//        }
//
//
//        showLog("设置铅笔");
//    }


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


    List<MyTrackInfo> userList = new ArrayList<>();

    private UserListAdapter userListAdapter;

    private void initUserList(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.rv_user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userListAdapter = new UserListAdapter(getContext(), userList);
        recyclerView.setAdapter(userListAdapter);

    }

    class UserListAdapter extends BaseAdapter<MyTrackInfo> {

        public UserListAdapter(Context context, List<MyTrackInfo> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_user_list;
        }

        @Override
        protected void toBindViewHolder(final ViewHolder holder, final int position, final List<MyTrackInfo> mData) {

            final LinearLayout lin_menu = holder.getView(R.id.lin_menu);

            ImageView iv_head = holder.getView(R.id.iv_head);


            Glide.with(getActivity())
                    .load(mData.get(position).getUserIcon())
                    .apply(GlideConfig.getCircleOptions())
                    .into(iv_head);
            holder.setText(R.id.tv_userName, mData.get(position).getUserName());

            if (position == 0) {
                holder.getView(R.id.iv_more).setVisibility(View.GONE);
            }

            holder.getView(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (identity.equals("1")) {


                        if (lin_menu.getVisibility() == View.VISIBLE) {
                            lin_menu.setVisibility(View.GONE);
                        } else {
                            lin_menu.setVisibility(View.VISIBLE);
                        }

                    }
                }
            });


            final int voice = mData.get(position).getVoice();
            final int camera = mData.get(position).getCamera();


            ImageView iv_voice = holder.getView(R.id.iv_voice);
            ImageView iv_camera = holder.getView(R.id.iv_camera);

            iv_camera.setImageResource(camera == 1 ? R.mipmap.icon_camera_on : R.mipmap.icon_camera_off);
            iv_voice.setImageResource(voice == 1 ? R.mipmap.icon_voice_on : R.mipmap.icon_voice_off);


            iv_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (voice == 1) {
                        //如果是打开那么去关闭

                        MainRoomActivity.chatFragment.requestCloseOpenCameraVoice(false, mData.get(position).getUserId(), 2);

                    } else {

                        MainRoomActivity.chatFragment.requestCloseOpenCameraVoice(true, mData.get(position).getUserId(), 2);

                    }


                }
            });

            iv_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (camera == 1) {
                        //如果是打开那么去关闭

                        MainRoomActivity.chatFragment.requestCloseOpenCameraVoice(false, mData.get(position).getUserId(), 3);
                    } else {

                        MainRoomActivity.chatFragment.requestCloseOpenCameraVoice(true, mData.get(position).getUserId(), 3);
                    }
                }
            });

        }
    }

    //--------------------------------------------------------------------------------评论框弹出

    private CmmtPopup mCmmtPopup;

    private void initCmmtPop() {
        mCmmtPopup = CmmtPopup.create(getActivity())
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setOnOkClickListener(new CmmtPopup.MyOkClickListener() {
                    @Override
                    public void onClick(View v, String content) {

                        mCmmtPopup.dismiss();

                        if (content.equals("")) {
                            showToast("内容不能为空!");
                            return;
                        }


                        MainRoomActivity.chatFragment.sendTextMessage(content);


                    }

                    @Override
                    public void onCameraClick() {

                        PhotoUtil.normalSelectPictureByCode(getActivity(), listImage, 1, PhotoUtil.MESSAGE_IMAGE);

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

        void onCameraClick(int type);

        void onVoiceClick(int type);

        void onLiveRoom();


//        void closeUserCamera(String userId);
//
//        void closeUserVoice(String userId);

        void onChangeRotate();


        void onWihteBoradAdd();

        void onTeacherCloseClass();


        void onQualityClick(int i);
    }


    private RoomControlFragmentListener listener;

    public void setRoomControlFragmentListener(RoomControlFragmentListener roomControlFragmentListener) {

        this.listener = roomControlFragmentListener;

    }


    //=============================================================================================
    //==============================================================================图片选择=======
    //=============================================================================================
    List<LocalMedia> listImage = new ArrayList<>();


    /**
     * 学生请求打开麦克风
     */
    private void requestOpenVoice() {


        String title = "是否向老师申请打开麦克风？";
        if (identity.equals("1")) {
            title = "是否关闭麦克风?";
        }

        NormalDialog.getInstance()
                .setContent(title)
                .setWidth(DisplayUtil.dip2px(getActivity(), 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {

                        if (listener != null) {
                            listener.onVoiceClick(1);
                        }


                    }
                })
                .show(getFragmentManager());

    }


    /**
     * 请求关闭麦克风
     */
    private void requestCloseVoice() {


        NormalDialog.getInstance()
                .setContent("是否关闭麦克风？")
                .setWidth(DisplayUtil.dip2px(getActivity(), 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {
                        if (listener != null) {
                            listener.onVoiceClick(0);
                        }
                    }
                })
                .show(getFragmentManager());

    }


    /**
     * 学生请求打开摄像头
     */
    private void requestOpenCamera() {

        String title = "是否向老师申请打开摄像头？";
        if (identity.equals("1")) {
            title = "是否打开摄像头?";
        }
        NormalDialog.getInstance()
                .setContent(title)
                .setWidth(DisplayUtil.dip2px(getActivity(), 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {

                        if (listener != null) {
                            listener.onCameraClick(1);
                        }


                    }
                })
                .show(getFragmentManager());

    }


    /**
     * 请求关闭摄像头
     */
    private void requestCloseCamera() {


        NormalDialog.getInstance()
                .setContent("是否关闭摄像头？")
                .setWidth(DisplayUtil.dip2px(getActivity(), 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {
                        if (listener != null) {
                            listener.onCameraClick(0);
                        }
                    }
                })
                .show(getFragmentManager());

    }


    /**
     * 老师下课
     */
    private void requestOverClass() {


        NormalDialog.getInstance()
                .setContent("是否结束本次课程？")
                .setWidth(DisplayUtil.dip2px(getActivity(), 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {
                        if (listener != null) {
                            listener.onTeacherCloseClass();
                        }
                    }
                })
                .show(getFragmentManager());

    }

    /**
     * 修改麦克风UI
     */
    public void isOpenVoiceUI(boolean b) {

        if (b) {
            roomControlBean.setDefault_voice(true);
        } else {
            roomControlBean.setDefault_voice(false);
        }

        initIconState();
    }


    /**
     * 修摄像头UI
     */
    public void isOpenCameraUI(boolean b) {

        if (b) {
            roomControlBean.setDefault_camera(true);
        } else {
            roomControlBean.setDefault_camera(false);
        }

        initIconState();
    }
}
