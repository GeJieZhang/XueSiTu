package com.live.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.herewhite.sdk.Room;
import com.herewhite.sdk.WhiteBroadView;
import com.herewhite.sdk.domain.Appliance;
import com.herewhite.sdk.domain.MemberState;
import com.herewhite.sdk.domain.Promise;
import com.herewhite.sdk.domain.SDKError;
import com.herewhite.sdk.domain.Scene;
import com.herewhite.sdk.domain.SceneState;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.EventBusTagUtils;
import com.lib.app.FragmentTag;
import com.lib.bean.Event;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.utils.json_deal.lib_mgson.MGson;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.status_bar.QMUI.QMUIStatusBarHelper;
import com.lib.fastkit.utils.status_bar.StatusBarUtil;
import com.lib.fastkit.utils.system.SystemUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.lib.http.call_back.HttpDialogCallBack;
import com.live.R;
import com.live.bean.CloseRoomBean;
import com.live.bean.control.IMBean;
import com.live.bean.control.RoomControlBean;
import com.live.bean.control.WhiteBoradBean;
import com.live.bean.live.MyTrackInfo;
import com.live.fragment.ChatFragment;
import com.live.fragment.ListVideoFragment;
import com.live.fragment.RoomControlFragment;
import com.live.fragment.WhiteBoardFragment;
import com.live.utils.WhiteBoardUtils;
import com.live.utils.config.LiveConfig;
import com.live.utils.HXChatUtils;
import com.live.utils.MyHashMap;
import com.live.utils.socket.IMSocketUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.droid.rtc.QNBeautySetting;
import com.qiniu.droid.rtc.QNErrorCode;
import com.qiniu.droid.rtc.QNRTCEngine;
import com.qiniu.droid.rtc.QNRTCEngineEventListener;
import com.qiniu.droid.rtc.QNRTCSetting;
import com.qiniu.droid.rtc.QNRTCUser;
import com.qiniu.droid.rtc.QNRoomState;
import com.qiniu.droid.rtc.QNSourceType;
import com.qiniu.droid.rtc.QNStatisticsReport;
import com.qiniu.droid.rtc.QNSurfaceView;

import com.qiniu.droid.rtc.QNTrackInfo;
import com.qiniu.droid.rtc.QNTrackKind;
import com.qiniu.droid.rtc.QNVideoFormat;
import com.qiniu.droid.rtc.model.QNAudioDevice;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.live.fragment.ChatFragment.ACTION_TYPE1;
import static com.live.fragment.ChatFragment.ACTION_TYPE2;
import static com.live.fragment.ChatFragment.MESSAGE_TYPE1;
import static com.live.utils.config.LiveConfig.DEFAULT_BITRATE;
import static com.live.utils.config.LiveConfig.DEFAULT_FPS;
import static com.live.utils.config.LiveConfig.DEFAULT_RESOLUTION;

@Route(path = ARouterPathUtils.Live_MainRoomActivity)
public class MainRoomActivity extends BaseRoomActivity implements QNRTCEngineEventListener, View.OnClickListener {
    @Autowired(name = "roomToken")
    String roomToken;

    @Autowired(name = "teacherPhone")
    String teacherPhone;
    @Autowired(name = "roomName")
    String roomName;

    @Autowired(name = "userPhone")
    String userPhone;

    @Autowired(name = "uuid")
    String uuid;
    @Autowired(name = "whitetoken")
    String whitetoken;

//    @Autowired(name = "name")
//    String hx_username;


    private String identity = "";
    private String userToken = "";


    private QNSurfaceView localSurfaceView;

    private static QNRTCEngine mEngine;
    private QNTrackInfo localVideoTrack;
    private QNTrackInfo localAudioTrack;
    //正在播放的Track
    private MyTrackInfo playingTrack;


    private FrameLayout f_p;//父布局
    private FrameLayout f_whiteboard;


    private MyHashMap<String, MyTrackInfo> trackInfoMap = new MyHashMap<>();

    //控制页面的状态管理
    public static RoomControlBean roomControlBean;

    public static WhiteBoradBean whiteBoradBean;

    //聊天室长连接
    public static IMSocketUtils imSocketUtils;
    //横屏聊天按钮控制
    private ImageView iv_chat_control;
    //横屏聊天菜单
    private LinearLayout lin_chat;

    private String userIcon;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.statusBarTintColor(this, getResources().getColor(R.color.black));

        screenOrientation = screenVertical;
        setContentView(R.layout.activity_main_room_vertical);
        ARouter.getInstance().inject(this);


        identity = SharedPreferenceManager.getInstance(this).getUserCache().getUserIdentity();
        userToken = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();
        userIcon = SharedPreferenceManager.getInstance(this).getUserCache().getUserHeadUrl();
        userName = SharedPreferenceManager.getInstance(this).getUserCache().getUserName();
        roomControlBean = new RoomControlBean();
        if (identity.equals("1")) {
            roomControlBean.setDefault_camera(true);
            roomControlBean.setDefault_voice(true);
        } else {
            roomControlBean.setDefault_camera(false);
            roomControlBean.setDefault_voice(false);
        }


        whiteBoradBean = new WhiteBoradBean();


        findView();

        initEngine();

        //聊天室处理
        initChat();

        initWhiteBorad();

        initFragment();
    }


    /**
     * 初始化聊天
     */
    private void initChat() {
        //HXChatUtils.signIn("token1", "123456", this);
        imSocketUtils = IMSocketUtils.getInstance().start(roomName, userToken);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();


    }


    //---------------------------------------------------------------------------------Fragment

    //视频菜单
    public static ListVideoFragment listVideoFragment;
    //控制页面
    public static RoomControlFragment roomControlFragment;
    //聊天
    public static ChatFragment chatFragment;
    //白板
    public static WhiteBoardFragment whiteBoardFragment;


    /**
     * 初始化Fragment
     */
    private void initFragment() {


        listVideoFragment = new ListVideoFragment();

        listVideoFragment.setListVideoFragmentListener(listVideoFragmentListener);

        updateVideoFragment();

        roomControlFragment = new RoomControlFragment();
        roomControlFragment.setRoomControlFragmentListener(roomControlFragmentListener);


        chatFragment = new ChatFragment(roomName);


        whiteBoardFragment = new WhiteBoardFragment(whiteBroadView, whiteBoardRoom);

        whiteBoardFragment.setWhiteBoardFragmentListener(whiteBoardFragmentListener);


        FragmentCustomUtils.setFragment(this, R.id.list_video, listVideoFragment, FragmentTag.List_Video);
        FragmentCustomUtils.setFragment(this, R.id.f_controller, roomControlFragment, FragmentTag.Room_Controller);
        FragmentCustomUtils.setFragment(this, R.id.f_chat, chatFragment, FragmentTag.Chat_Fragment);
        FragmentCustomUtils.setFragment(this, R.id.f_whiteboard, whiteBoardFragment, FragmentTag.WhiteBoardFragment);


    }

    private void removeFragment() {
        FragmentCustomUtils.removeFragment(this, FragmentTag.List_Video);
        FragmentCustomUtils.removeFragment(this, FragmentTag.Room_Controller);
        FragmentCustomUtils.removeFragment(this, FragmentTag.Chat_Fragment);
        FragmentCustomUtils.removeFragment(this, FragmentTag.WhiteBoardFragment);
    }


    /**
     * 更新直播列表
     */
    private void updateVideoFragment() {

        if (listVideoFragment != null) {
            listVideoFragment.setTrackInfo(trackInfoMap.getOrderedValues());
        }


    }


    /**
     * 获取控件ID
     */
    private void findView() {

        f_whiteboard = findViewById(R.id.f_whiteboard);

        if (roomControlBean.isDefault_board()) {
            f_whiteboard.setVisibility(View.GONE);
        } else {
            f_whiteboard.setVisibility(View.VISIBLE);
        }


        localSurfaceView = findViewById(R.id.local_surface_view);

        localSurfaceView.setZOrderOnTop(false);
        localSurfaceView.setZOrderMediaOverlay(false);
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏

            //聊天菜单控制
            try {
                iv_chat_control = findViewById(R.id.iv_chat_control);
                iv_chat_control.setOnClickListener(this);
                lin_chat = findViewById(R.id.lin_chat);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            //竖屏

            f_p = findViewById(R.id.f_p);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) f_p.getLayoutParams();

            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = DisplayUtil.getScreenHeight(this) / 3;
            f_p.setLayoutParams(params);
        }


    }


    public static QNRTCEngine getEngine() {

        return mEngine;

    }

    /**
     * 初始化直播
     */
    private void initEngine() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        int videoWidth = preferences.getInt(LiveConfig.WIDTH, DEFAULT_RESOLUTION[1][0]);
        int videoHeight = preferences.getInt(LiveConfig.HEIGHT, DEFAULT_RESOLUTION[1][1]);
        int fps = preferences.getInt(LiveConfig.FPS, DEFAULT_FPS[1]);
        boolean isHwCodec = preferences.getInt(LiveConfig.CODEC_MODE, LiveConfig.HW) == LiveConfig.HW;
        int videoBitrate = preferences.getInt(LiveConfig.BITRATE, DEFAULT_BITRATE[1]);
        boolean isMaintainRes = preferences.getBoolean(LiveConfig.MAINTAIN_RES, false);


        QNVideoFormat format = new QNVideoFormat(videoWidth, videoHeight, fps);
        QNRTCSetting setting = new QNRTCSetting();
        setting.setCameraID(QNRTCSetting.CAMERA_FACING_ID.FRONT)//配置默认摄像头 ID
                .setHWCodecEnabled(isHwCodec)//是否开启硬编码
                .setMaintainResolution(isMaintainRes)//设置是否固定分辨率
                .setVideoBitrate(videoBitrate)
                .setVideoEncodeFormat(format)//配置视频的编码格式
                .setVideoPreviewFormat(format);//配置预览的编码格式


        mEngine = QNRTCEngine.createEngine(getApplicationContext(), setting, this);


        localVideoTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.VIDEO_CAMERA)
                .setMaster(true)
                .create();
        localAudioTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.AUDIO)
                .setBitrate(64 * 1000)// 设置音频码率
                .setMaster(true)
                .create();

//        playingTrack.setUserId();
//        playingScreenVideoTrack = localVideoTrack;
//        playingScreenAudioTrack=localAudioTrack;


        MyTrackInfo myTrackInfo = new MyTrackInfo(userPhone, localVideoTrack, localAudioTrack);
        trackInfoMap.put(userPhone, myTrackInfo);
        playingTrack = myTrackInfo;


        initBeauty();

        mEngine.joinRoom(roomToken);


    }


    /**
     * 美颜
     */
    private void initBeauty() {
        QNBeautySetting beautySetting = new QNBeautySetting(0.5f, 0.5f, 0.5f);
        beautySetting.setEnable(true);
        mEngine.setBeauty(beautySetting);
    }


    //--------------------------------------------------------------直播间回调


    /**
     * 直播状态回调
     *
     * @param qnRoomState
     */
    @Override
    public void onRoomStateChanged(QNRoomState qnRoomState) {


        LogUtil.e("onRoomStateChanged");

        switch (qnRoomState) {
            case RECONNECTING:
                //重新连接中
                showToast(getString(R.string.reconnecting_to_room));
                break;
            case CONNECTED:
                //连接成功
                toTeacherPublishTrack();


                showLog("连接直播房间成功");
                break;
            case RECONNECTED:
                //重新连接成功
                showToast(getString(R.string.connected_to_room));
                break;
            case CONNECTING:
                //连接中
                showToast(getString(R.string.connecting_to));
                break;
        }

    }

    /**
     * 推送流
     */
    private void toTeacherPublishTrack() {

        if (identity.equals("1")) {
            mEngine.publishTracks(Arrays.asList(localVideoTrack, localAudioTrack));
        }

    }


    @Override
    public void onRemoteUserJoined(String s, String s1) {
        LogUtil.e("onRemoteUserJoined用户加入:" + s + "消息:" + s1);
        MyTrackInfo myTrackInfo = new MyTrackInfo();
        myTrackInfo.setUserId(s);

        trackInfoMap.put(s, myTrackInfo);
    }

    @Override
    public void onRemoteUserLeft(String s) {
        LogUtil.e("onRemoteUserLeft用户离开:" + s);

        trackInfoMap.remove(s);

        updateVideoFragment();

    }

    @Override
    public void onLocalPublished(List<QNTrackInfo> list) {
        LogUtil.e("onLocalPublished数据大小:" + list.size());
    }

    private void logQNTrackInfo(List<QNTrackInfo> list) {

        for (QNTrackInfo qnTrackInfo : list) {
            LogUtil.e(qnTrackInfo.toString());
        }


    }

    @Override
    public void onRemotePublished(String s, List<QNTrackInfo> list) {


        LogUtil.e("onRemotePublished数据大小:" + list.size());
        logQNTrackInfo(list);

        MyTrackInfo myTrackInfo = new MyTrackInfo();


        for (QNTrackInfo track : list) {

            myTrackInfo.setUserId(track.getUserId());

            if (track.getTrackKind().equals(QNTrackKind.VIDEO)) {
                myTrackInfo.setVideoTrack(track);

            } else {
                myTrackInfo.setAudioTrack(track);
            }
        }

        showLog(myTrackInfo.getUserId());


        trackInfoMap.put(myTrackInfo.getUserId(), myTrackInfo, myTrackInfo.getUserId().equals(teacherPhone));
        showLog(myTrackInfo.getUserId());
        updateVideoFragment();


    }

    @Override
    public void onRemoteUnpublished(String s, List<QNTrackInfo> list) {
        LogUtil.e("onRemoteUnpublished数据大小:" + list.size());
        logQNTrackInfo(list);


        for (QNTrackInfo track : list) {
            if (track.getTrackKind().equals(QNTrackKind.VIDEO)) {

                trackInfoMap.get(track.getUserId()).setVideoTrack(null);

            } else {
                trackInfoMap.get(track.getUserId()).setAudioTrack(null);
            }
        }


        updateVideoFragment();

    }

    @Override
    public void onRemoteUserMuted(String s, List<QNTrackInfo> list) {
        LogUtil.e("onRemoteUserMuted人数:" + list.size());

    }

    @Override
    public void onSubscribed(String s, List<QNTrackInfo> list) {
        LogUtil.e("onSubscribed人数:" + list.size());


    }

    @Override
    public void onKickedOut(String s) {
        LogUtil.e("onKickedOut");
    }

    @Override
    public void onStatisticsUpdated(QNStatisticsReport qnStatisticsReport) {
        LogUtil.e("onStatisticsUpdated");
    }


    @Override
    public void onAudioRouteChanged(QNAudioDevice qnAudioDevice) {
        LogUtil.e("onAudioRouteChanged");
    }

    @Override
    public void onCreateMergeJobSuccess(String s) {
        LogUtil.e("onCreateMergeJobSuccess");
    }

    @Override
    public void onError(int errorCode, String description) {
        LogUtil.e("onError");

        if (errorCode == QNErrorCode.ERROR_TOKEN_INVALID
                || errorCode == QNErrorCode.ERROR_TOKEN_ERROR
                || errorCode == QNErrorCode.ERROR_TOKEN_EXPIRED) {
            showToast("roomToken 错误，请重新加入房间");
        } else if (errorCode == QNErrorCode.ERROR_AUTH_FAIL
                || errorCode == QNErrorCode.ERROR_RECONNECT_TOKEN_ERROR) {

            showToast("roomToken 错误");

        } else if (errorCode == QNErrorCode.ERROR_PUBLISH_FAIL) {

            showToast("发布失败，请重新加入房间发布");
        } else {
            showToast("errorCode:" + errorCode + " description:" + description);
        }
    }


    //--------------------------------------------------------------视频列表回调


    private ListVideoFragment.ListVideoFragmentListener listVideoFragmentListener = new ListVideoFragment.ListVideoFragmentListener() {

        @Override
        public void onFindAdmin(MyTrackInfo trackInfo) {


            mEngine.setRenderWindow(playingTrack.getVideoTrack(), null);


            playingTrack = trackInfo;

            mEngine.setRenderWindow(playingTrack.getVideoTrack(), localSurfaceView);


            //playingTrack.setVideoTrack(trackInfo.getVideoTrack());

        }

        @Override
        public void onSetQNSurfaceView(MyTrackInfo trackInfo, QNSurfaceView qnSurfaceView) {
            mEngine.setRenderWindow(trackInfo.getVideoTrack(), qnSurfaceView);
        }

        @Override
        public void onChangeQNSurfaceView(MyTrackInfo trackInfo, QNSurfaceView qnSurfaceView) {

            trackInfoMap.replacePositonByValue(playingTrack, trackInfo);
            updateVideoFragment();


        }

        @Override
        public void onWhiteBoradClick() {


            if (whiteBoardRoom == null) {

                showLog("白板正在初始化中!");
                return;
            }


            if (f_whiteboard.getVisibility() == View.VISIBLE) {

                //隐藏白板
                f_whiteboard.setVisibility(View.GONE);

                roomControlBean.setDefault_board(true);

                localSurfaceView.setVisibility(View.VISIBLE);
                localSurfaceView.setZOrderOnTop(false);
                localSurfaceView.setZOrderMediaOverlay(false);


            } else {
                //显示白板
                f_whiteboard.setVisibility(View.VISIBLE);
                roomControlBean.setDefault_board(false);
                localSurfaceView.setVisibility(View.GONE);
                localSurfaceView.setZOrderOnTop(false);
                localSurfaceView.setZOrderMediaOverlay(false);


                if (whiteBoardFragment != null) {
                    whiteBoardFragment.refreshRoom();
                }


            }

        }

        @Override
        public void onPPTClick() {

        }


    };

    //--------------------------------------------------------------控制页回调
    private RoomControlFragment.RoomControlFragmentListener roomControlFragmentListener = new RoomControlFragment.RoomControlFragmentListener() {


        @Override
        public void onCameraClick(int type) {
            switch (type) {

                case 0: {
                    //关闭

                    localVideoTrack.setMuted(true);
                    mEngine.muteTracks(Arrays.asList(localVideoTrack));


                    break;
                }

                case 1: {
                    //打开

                    if (identity.equals("1")) {
                        localVideoTrack.setMuted(false);
                        mEngine.muteTracks(Arrays.asList(localVideoTrack));
                    } else {

                        if (chatFragment != null) {
                            chatFragment.requestOpenCamera();
                        }
                    }
                    break;
                }
            }
        }

        @Override
        public void onVoiceClick(int type) {


            switch (type) {

                case 0: {
                    //关闭

                    localAudioTrack.setMuted(true);
                    mEngine.muteTracks(Arrays.asList(localAudioTrack));


                    break;
                }

                case 1: {
                    //打开

                    if (identity.equals("1")) {
                        localAudioTrack.setMuted(false);
                        mEngine.muteTracks(Arrays.asList(localAudioTrack));
                    } else {

                        if (chatFragment != null) {
                            chatFragment.requestOpenVoice();
                        }
                    }
                    break;
                }
            }


        }


        @Override
        public void onLiveRoom() {
            liveRoom();
        }

        @Override
        public void closeUserCamera(String userId) {

            QNTrackInfo qnTrackInfo = trackInfoMap.get(userId).getVideoTrack();

            if (qnTrackInfo.isMuted()) {
                qnTrackInfo.setMuted(false);

            } else {
                qnTrackInfo.setMuted(true);
            }
            mEngine.muteTracks(Arrays.asList(qnTrackInfo));


        }

        @Override
        public void closeUserVoice(String userId) {
            QNTrackInfo qnTrackInfo = trackInfoMap.get(userId).getAudioTrack();

            if (qnTrackInfo.isMuted()) {
                qnTrackInfo.setMuted(false);

            } else {
                qnTrackInfo.setMuted(true);
            }
            mEngine.muteTracks(Arrays.asList(qnTrackInfo));

        }

        @Override
        public void onChangeRotate() {


//            if (whiteBoardRoom != null) {
//                whiteBoardRoom.refreshViewSize();
//                showLog("刷新白板");
//            }
        }

        @Override
        public void onWihteBoradAdd() {
            whiteBoardRoom.putScenes(SCENE_DIR, new Scene[]{
                    new Scene("page" + maxSize)}, +maxSize);
            whiteBoardRoom.setScenePath(SCENE_DIR + "/page" + maxSize);

            //  maxSize++;

            //showLog("白板的数量1:" + maxSize);

            getScenesSize();
        }


    };


    //--------------------------------------------------------------屏幕切换控制

    //默认为竖屏
    public static int screenOrientation = screenVertical;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        screenOrientation = newConfig.orientation;

        LogUtil.e(screenOrientation + "");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            fullscreen(true);
            Toast.makeText(getApplicationContext(), "横屏", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_main_room_horizontal);
            findView();

            removeFragment();
            initFragment();


            mEngine.setRenderWindow(localVideoTrack, localSurfaceView);


        } else {
            Toast.makeText(getApplicationContext(), "竖屏", Toast.LENGTH_SHORT).show();
            fullscreen(false);

            setContentView(R.layout.activity_main_room_vertical);
            findView();

            removeFragment();
            initFragment();
            mEngine.setRenderWindow(localVideoTrack, localSurfaceView);


        }


    }
    //--------------------------------------------------------------点击事件

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.iv_chat_control) {

            if (lin_chat.getVisibility() == View.VISIBLE) {
                lin_chat.setVisibility(View.GONE);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_chat_control.getLayoutParams();
                params.leftMargin = DisplayUtil.dip2px(this, 16);
                iv_chat_control.setLayoutParams(params);
                iv_chat_control.setImageResource(R.mipmap.icon_right);

                if (listVideoFragment != null) {
                    listVideoFragment.hideVideoList(true);
                }
            } else {

                lin_chat.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_chat_control.getLayoutParams();
                params.leftMargin = DisplayUtil.dip2px(this, -16);
                iv_chat_control.setLayoutParams(params);

                iv_chat_control.setImageResource(R.mipmap.icon_left);

                if (listVideoFragment != null) {
                    listVideoFragment.hideVideoList(false);
                }
            }


        }

    }


    //-----------------------------------------------------------------------------------图片选择回调

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    LocalMedia media = selectList.get(0);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    if (media.isCompressed()) {

                        String compressPath = media.getCompressPath();

                        showToast("选择成功");


                        EventBus.getDefault().post(new Event<>(2, compressPath), EventBusTagUtils.RoomControlFragment);


                    }

                    break;


            }


        }
    }


    //-----------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------白板相关
    //------------------------------------------------------------------------------------------


    private WhiteBroadView whiteBroadView;


    private WhiteBoardUtils whiteBoardUtils;


    private static Room whiteBoardRoom;
    final String SCENE_DIR = "/dir";
    private int pageIndex = 1;//记录当前页面位置
    private int maxSize = 1;//当前页面的总大小

    /**
     * 初始化白板
     */
    private void initWhiteBorad() {

        whiteBroadView = new WhiteBroadView(this);
        whiteBoardUtils = WhiteBoardUtils.getInstance().joinToRoom(this, whiteBroadView, uuid, whitetoken);

        whiteBoardUtils.setWhiteBoardListener(new WhiteBoardUtils.WhiteBoardListener() {
            @Override
            public void onJoinRoomSucess(Room room) {
                if (whiteBoardFragment != null) {
                    whiteBoardRoom = room;
                    whiteBoardFragment.setRoom(room);

                    getScenesSize();
                    showLog("room回调");

                }
            }
        });
    }

    /**
     * 获取白板房间
     *
     * @return
     */
    public static Room getwhiteBoardRoom() {


        return whiteBoardRoom;
    }


    private WhiteBoardFragment.WhiteBoardFragmentListener whiteBoardFragmentListener = new WhiteBoardFragment.WhiteBoardFragmentListener() {
        @Override
        public void onWihteBoradFont() {

            if (pageIndex > 0) {
                pageIndex--;
                whiteBoardRoom.setScenePath(SCENE_DIR + "/" + sceneList.get(pageIndex).getName());

            } else {
                showToast("没有更多了");
            }

            if (whiteBoardFragment != null) {
                whiteBoardFragment.updatePage((pageIndex + 1) + "/" + maxSize);
            }


            showLog("当前页码:" + pageIndex);


        }

        @Override
        public void onWihteBoradNext() {

            if (pageIndex < maxSize - 1) {
                pageIndex++;
                whiteBoardRoom.setScenePath(SCENE_DIR + "/" + sceneList.get(pageIndex).getName());
            } else {
                showToast("没有更多了");
            }
            if (whiteBoardFragment != null) {
                whiteBoardFragment.updatePage((pageIndex + 1) + "/" + maxSize);
            }
            showLog("当前页码:" + pageIndex);
        }
    };


    private List<Scene> sceneList = new ArrayList<>();

    public void getScenesSize() {
        whiteBoardRoom.getSceneState(new Promise<SceneState>() {
            @Override
            public void then(SceneState sceneState) {
                maxSize = sceneState.getScenes().length;
                pageIndex = sceneState.getIndex();

                sceneList.clear();
                for (Scene scene : sceneState.getScenes()) {
                    sceneList.add(scene);
                }

                if (whiteBoardFragment != null) {
                    whiteBoardFragment.updatePage((pageIndex + 1) + "/" + maxSize);
                }
                showLog("白板总数量:" + maxSize);

            }

            @Override
            public void catchEx(SDKError sdkError) {

            }
        });


    }


    //-----------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------房间相关
    //------------------------------------------------------------------------------------------


    @Override
    public void onBackPressed() {


        liveRoom();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEngine != null) {
            mEngine.leaveRoom();
            mEngine.destroy();
            mEngine = null;
        }

        //清空控制页面数据
        roomControlBean = null;

        //HXChatUtils.signOut();
        //HXChatUtils.leaveChatRoom();

        imSocketUtils.closeSocket();


        LogUtil.e("onDestroy");
    }

    /**
     * 离开房间
     */
    private void liveRoom() {


        NormalDialog.getInstance()
                .setContent("确认要退出直播间么？")
                .setWidth(DisplayUtil.dip2px(this, 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {

                        String consume_class = "";

                        if (identity.equals("1")) {
                            consume_class = "0";
                        } else {

                        }

                        HttpUtils.with(MainRoomActivity.this)
                                .addParam("requestType", "LIVE_DROPOTU_ROOM")
                                .addParam("room_name", roomName)
                                .addParam("consume_class", consume_class)
                                .addParam("token", userToken)
                                .execute(new HttpDialogCallBack<CloseRoomBean>() {
                                    @Override
                                    public void onSuccess(CloseRoomBean result) {

                                        if (result.getCode() == CodeUtil.CODE_200) {
                                            finish();
                                        } else {
                                            showToast(result.getMsg());
                                        }


                                    }

                                    @Override
                                    public void onError(String e) {

                                    }
                                });


                    }
                })
                .show(getSupportFragmentManager());

    }


}
