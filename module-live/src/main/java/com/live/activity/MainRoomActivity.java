package com.live.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.herewhite.sdk.Room;
import com.herewhite.sdk.WhiteBroadView;
import com.herewhite.sdk.domain.Appliance;
import com.herewhite.sdk.domain.MemberState;
import com.lib.app.ARouterPathUtils;
import com.lib.app.EventBusTagUtils;
import com.lib.app.FragmentTag;
import com.lib.bean.Event;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.status_bar.QMUI.QMUIStatusBarHelper;
import com.lib.fastkit.utils.status_bar.StatusBarUtil;
import com.live.R;
import com.live.bean.control.RoomControlBean;
import com.live.fragment.ChatFragment;
import com.live.fragment.ListVideoFragment;
import com.live.fragment.RoomControlFragment;
import com.live.fragment.WhiteBoardFragment;
import com.live.utils.WhiteBoardUtils;
import com.live.utils.config.LiveConfig;
import com.live.utils.HXChatUtils;
import com.live.utils.MyHashMap;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.droid.rtc.QNBeautySetting;
import com.qiniu.droid.rtc.QNErrorCode;
import com.qiniu.droid.rtc.QNRTCEngine;
import com.qiniu.droid.rtc.QNRTCEngineEventListener;
import com.qiniu.droid.rtc.QNRTCSetting;
import com.qiniu.droid.rtc.QNRoomState;
import com.qiniu.droid.rtc.QNSourceType;
import com.qiniu.droid.rtc.QNStatisticsReport;
import com.qiniu.droid.rtc.QNSurfaceView;

import com.qiniu.droid.rtc.QNTrackInfo;
import com.qiniu.droid.rtc.QNTrackKind;
import com.qiniu.droid.rtc.QNVideoFormat;
import com.qiniu.droid.rtc.model.QNAudioDevice;

import org.simple.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import static com.live.utils.config.LiveConfig.DEFAULT_BITRATE;
import static com.live.utils.config.LiveConfig.DEFAULT_FPS;
import static com.live.utils.config.LiveConfig.DEFAULT_RESOLUTION;

@Route(path = ARouterPathUtils.Live_MainRoomActivity)
public class MainRoomActivity extends BaseRoomActivity implements QNRTCEngineEventListener, View.OnClickListener {
    @Autowired(name = "roomToken")
    String roomToken;
    @Autowired(name = "name")
    String hx_username;
    QNSurfaceView localSurfaceView;

    private QNRTCEngine mEngine;
    private QNTrackInfo localVideoTrack;
    private QNTrackInfo localAudioTrack;
    //正在播放的Track
    private QNTrackInfo playingScreenVideoTrack;

    private FrameLayout f_whiteboard;


    private MyHashMap<String, QNTrackInfo> trackInfoMap = new MyHashMap<>();

    //控制页面的状态管理
    public static RoomControlBean roomControlBean = new RoomControlBean();

    //横屏聊天按钮控制
    private ImageView iv_chat_control;
    //横屏聊天菜单
    private LinearLayout lin_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.statusBarTintColor(this, getResources().getColor(R.color.black));


        setContentView(R.layout.activity_main_room_vertical);
        ARouter.getInstance().inject(this);

        findView();

        initEngine();


        //聊天室处理
        initChat();

        //initWhiteBorad();

        initFragment();
    }


    /**
     * 初始化聊天
     */
    private void initChat() {
        HXChatUtils.signIn(hx_username, "123456", this);
    }


    //---------------------------------------------------------------------------------Fragment

    //视频菜单
    private ListVideoFragment listVideoFragment;
    //控制页面
    private RoomControlFragment roomControlFragment;
    //聊天
    private ChatFragment chatFragment;
    //白板
    private WhiteBoardFragment whiteBoardFragment;


    /**
     * 初始化Fragment
     */
    private void initFragment() {


        listVideoFragment = new ListVideoFragment();

        listVideoFragment.setListVideoFragmentListener(listVideoFragmentListener);

        updateVideoFragment();

        roomControlFragment = new RoomControlFragment();
        roomControlFragment.setRoomControlFragmentListener(roomControlFragmentListener);


        chatFragment = new ChatFragment();


        whiteBoardFragment = new WhiteBoardFragment(whiteBroadView, whiteBoardRoom);


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

        listVideoFragment.setTrackInfo(trackInfoMap.getOrderedValues());
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
        }


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


        playingScreenVideoTrack = localVideoTrack;
        trackInfoMap.put("local", playingScreenVideoTrack);

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


    //--------------------------------------------------------------QNRTCEngineEventListener


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
                mEngine.publishTracks(Arrays.asList(localVideoTrack, localAudioTrack));

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

    @Override
    public void onRemoteUserJoined(String s, String s1) {
        LogUtil.e("onRemoteUserJoined用户加入:" + s + "消息:" + s1);
    }

    @Override
    public void onRemoteUserLeft(String s) {
        LogUtil.e("onRemoteUserLeft用户离开:" + s);
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
        for (QNTrackInfo track : list) {
            if (track.getTrackKind().equals(QNTrackKind.VIDEO)) {


                trackInfoMap.put(track.getUserId(), track, track.getUserId().equals("token1"));
            }
        }

        if (listVideoFragment != null) {
            updateVideoFragment();
        }
    }

    @Override
    public void onRemoteUnpublished(String s, List<QNTrackInfo> list) {
        LogUtil.e("onRemoteUnpublished数据大小:" + list.size());
        logQNTrackInfo(list);
        for (QNTrackInfo track : list) {
            if (track.getTrackKind().equals(QNTrackKind.VIDEO)) {

                trackInfoMap.remove(track.getUserId());
                //trackInfoList.add(track);
            }
        }
        if (listVideoFragment != null) {


            updateVideoFragment();
        }
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

    //--------------------------------------------------------------视频列表


    private ListVideoFragment.ListVideoFragmentListener listVideoFragmentListener = new ListVideoFragment.ListVideoFragmentListener() {

        @Override
        public void onFindAdmin(QNTrackInfo trackInfo) {
            mEngine.setRenderWindow(playingScreenVideoTrack, null);
            playingScreenVideoTrack = trackInfo;
            mEngine.setRenderWindow(trackInfo, localSurfaceView);


        }

        @Override
        public void onSetQNSurfaceView(QNTrackInfo trackInfo, QNSurfaceView qnSurfaceView) {
            mEngine.setRenderWindow(trackInfo, qnSurfaceView);
        }

        @Override
        public void onChangeQNSurfaceView(QNTrackInfo trackInfo, QNSurfaceView qnSurfaceView) {

            trackInfoMap.replacePositonByValue(playingScreenVideoTrack, trackInfo);
            updateVideoFragment();


        }

        @Override
        public void onWhiteBoradClick() {

            if (f_whiteboard.getVisibility() == View.VISIBLE) {
                f_whiteboard.setVisibility(View.GONE);

                roomControlBean.setDefault_board(true);

                localSurfaceView.setVisibility(View.VISIBLE);
            } else {
                f_whiteboard.setVisibility(View.VISIBLE);
                roomControlBean.setDefault_board(false);
                localSurfaceView.setVisibility(View.GONE);


            }

        }

        @Override
        public void onPPTClick() {

        }


    };


    private RoomControlFragment.RoomControlFragmentListener roomControlFragmentListener = new RoomControlFragment.RoomControlFragmentListener() {
        @Override
        public void onCameraClick() {


            if (localVideoTrack.isMuted()) {
                localVideoTrack.setMuted(false);
                playingScreenVideoTrack.setMuted(false);

            } else {

                localVideoTrack.setMuted(true);
                playingScreenVideoTrack.setMuted(true);
            }

            mEngine.muteTracks(Arrays.asList(playingScreenVideoTrack, localVideoTrack));


//            if (trackInfoMap.get("token2") != null) {
//
//                QNTrackInfo qnTrackInfo = trackInfoMap.get("token2");
//                qnTrackInfo.setMuted(true);
//                mEngine.muteTracks(Arrays.asList(qnTrackInfo));
//
//            }

        }

        @Override
        public void onVoiceClick() {

            if (localAudioTrack.isMuted()) {
                localAudioTrack.setMuted(false);
            } else {
                localAudioTrack.setMuted(true);
            }


            mEngine.muteTracks(Arrays.asList(localAudioTrack));


        }


    };

    //--------------------------------------------------------------控制回调


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEngine != null) {
            mEngine.leaveRoom();
            mEngine.destroy();
            mEngine = null;
        }


        HXChatUtils.signOut();
        HXChatUtils.leaveChatRoom();

        LogUtil.e("onDestroy");
    }


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
            } else {

                lin_chat.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_chat_control.getLayoutParams();
                params.leftMargin = DisplayUtil.dip2px(this, -16);
                iv_chat_control.setLayoutParams(params);

                iv_chat_control.setImageResource(R.mipmap.icon_left);
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


    WhiteBroadView whiteBroadView;

    private Room whiteBoardRoom;

    /**
     * 初始化白板
     */
    private void initWhiteBorad() {

        whiteBroadView = new WhiteBroadView(this);
        whiteBoardRoom = WhiteBoardUtils.getInstance().joinToRoom(this, whiteBroadView);


    }


}
