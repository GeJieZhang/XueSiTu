package com.live.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.lib.bean.PushDetailBean;
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
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.pop.LiveCheckMoneyPopupUtils;
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
import org.simple.eventbus.Subscriber;

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
    //房间Token
    @Autowired(name = "roomToken")
    String roomToken;
    //当前房间的老师
    @Autowired(name = "teacherPhone")
    String teacherPhone;
    //房间名称
    @Autowired(name = "roomName")
    String roomName;
    //当前用户的手机号
    @Autowired(name = "userPhone")
    String userPhone;

    //白板UUID
    @Autowired(name = "uuid")
    String uuid;
    //白板Token
    @Autowired(name = "whitetoken")
    String whitetoken;


    //身份表示(1老师 0学生)
    private String identity = "";
    private String userToken = "";


    //显示大屏直播画面的View
    private QNSurfaceView localSurfaceView;

    //直播引擎
    private static QNRTCEngine mEngine;
    //本地的视频流
    private QNTrackInfo localVideoTrack;
    //本地的音频流
    private QNTrackInfo localAudioTrack;
    //正在播放的流(包含视频流和音频流)
    private MyTrackInfo playingTrack;
    //直播画面父布局
    private FrameLayout f_p;
    //白板父布局
    private FrameLayout f_whiteboard;

    //Map用于保存进入直播间用户的信息(每进一个用户需要添加一次，反之移除)
    public static MyHashMap<String, MyTrackInfo> trackInfoMap = new MyHashMap<>();

    //控制页面的状态管理
    public static RoomControlBean roomControlBean;

    //白板页面状态管理
    public static WhiteBoradBean whiteBoradBean;

    //聊天室长连接(公司服务器)用于聊天使用
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


        /**
         * 初始换页面配置
         */
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.statusBarTintColor(this, getResources().getColor(R.color.black));
        screenOrientation = screenVertical;
        setContentView(R.layout.activity_main_room_vertical);
        ARouter.getInstance().inject(this);

        /**
         * 初始化页面数据
         */
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


        /**
         * 通过横竖屏来获取不同的控件
         * 横竖屏是两套页面
         */
        findView();


        /**
         * 聊天
         */
        initChatFragment();
        initChat();


        /**
         * 直播
         */
        initEngine();
        initListVideoFragment();

        /**
         * 控制
         */
        initRoomControlFragment();


        /**
         * 白板
         */
        initWhiteBorad(FIRST_INIT);
        initWitheBoardFragment();
    }


    /**
     * 初始化聊天
     */
    private void initChat() {

        imSocketUtils = IMSocketUtils.getInstance().start(roomName, userToken);


    }


    /**
     * ==============================================================================================
     * ==========================================================================Fragment
     * =============================================================================================
     */

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

        initChatFragment();
        initListVideoFragment();
        initRoomControlFragment();
        initWitheBoardFragment();


    }

    private void initWitheBoardFragment() {
        whiteBoardFragment = new WhiteBoardFragment(whiteBroadView, whiteBoardRoom);
        whiteBoardFragment.setWhiteBoardFragmentListener(whiteBoardFragmentListener);

        FragmentCustomUtils.setFragment(this, R.id.f_whiteboard, whiteBoardFragment, FragmentTag.WhiteBoardFragment);
    }

    private void initChatFragment() {
        chatFragment = new ChatFragment(roomName);
        chatFragment.setChatFragmentListener(chatFragmentListener);
        FragmentCustomUtils.setFragment(this, R.id.f_chat, chatFragment, FragmentTag.Chat_Fragment);
    }

    private void initRoomControlFragment() {
        roomControlFragment = new RoomControlFragment();
        roomControlFragment.setRoomControlFragmentListener(roomControlFragmentListener);
        FragmentCustomUtils.setFragment(this, R.id.f_controller, roomControlFragment, FragmentTag.Room_Controller);
    }

    private void initListVideoFragment() {
        listVideoFragment = new ListVideoFragment();
        listVideoFragment.setListVideoFragmentListener(listVideoFragmentListener);
        updateVideoFragment();
        FragmentCustomUtils.setFragment(this, R.id.list_video, listVideoFragment, FragmentTag.List_Video);
    }

    private void removeFragment() {
        FragmentCustomUtils.removeFragment(this, FragmentTag.List_Video);
        FragmentCustomUtils.removeFragment(this, FragmentTag.Room_Controller);
        FragmentCustomUtils.removeFragment(this, FragmentTag.Chat_Fragment);
        FragmentCustomUtils.removeFragment(this, FragmentTag.WhiteBoardFragment);
    }


    /**
     * ==============================================================================================
     * ==========================================================================MainRoomActivity
     * =============================================================================================
     */

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


            //mEngine.setRenderWindow(localVideoTrack, localSurfaceView);


        } else {
            Toast.makeText(getApplicationContext(), "竖屏", Toast.LENGTH_SHORT).show();
            fullscreen(false);

            setContentView(R.layout.activity_main_room_vertical);
            findView();

            removeFragment();
            initFragment();
            //mEngine.setRenderWindow(localVideoTrack, localSurfaceView);


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
                case PhotoUtil.MESSAGE_IMAGE:
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


                        MainRoomActivity.chatFragment.sendImageMessage(compressPath);


                    }

                    break;

                case PhotoUtil.WHITE_BORAD_IMAGE:

                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> boardSelectList = PictureSelector.obtainMultipleResult(data);

                    LocalMedia boardMedia = boardSelectList.get(0);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    if (boardMedia.isCompressed()) {

                        String compressPath = boardMedia.getCompressPath();

                        if (whiteBoardFragment != null) {
                            whiteBoardFragment.upLoadImage(compressPath);
                        }


                    }


                    break;

            }


        }
    }

    /**
     *==============================================================================================
     * ==========================================================================直播相关
     * =============================================================================================
     */
    /**
     * 离开房间
     */
    //老师每次下课扣除1个课时
    String consume_class = "1";

    private void liveRoom() {


        NormalDialog.getInstance()
                .setContent("确认要退出直播间么？")
                .setWidth(DisplayUtil.dip2px(this, 300))
                .setSureListener(new NormalDialog.SurelListener() {
                    @Override
                    public void onSure() {


                        requestLiveRoom(consume_class);


                    }
                })
                .show(getSupportFragmentManager());

    }


    /**
     * 请求离开房间
     *
     * @param consume_class
     */
    private void requestLiveRoom(String consume_class) {
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


    /**
     * 学生申请麦克风成功
     */
    public void requestVoiceSuccess() {


        mEngine.publishTracks(Arrays.asList(localAudioTrack));

        QNTrackInfo video = trackInfoMap.get(userPhone).getVideoTrack();

        MyTrackInfo myTrackInfo = new MyTrackInfo(userPhone, video, localAudioTrack);
        trackInfoMap.put(userPhone, myTrackInfo);
        updateVideoFragment();
    }


    /**
     * 学生申请摄像头成功
     */
    public void requestCameraSuccess() {

        mEngine.publishTracks(Arrays.asList(localVideoTrack));
        QNTrackInfo audio = trackInfoMap.get(userPhone).getAudioTrack();
        MyTrackInfo myTrackInfo = new MyTrackInfo(userPhone, localVideoTrack, audio);
        trackInfoMap.put(userPhone, myTrackInfo);
        updateVideoFragment();
    }


    @Override
    public void onBackPressed() {


        liveRoom();


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
     * 获取直播引擎
     *
     * @return
     */
    public static QNRTCEngine getEngine() {

        return mEngine;

    }

    /**
     * 初始化直播
     */

    private QNRTCSetting setting;

    private void initEngine() {

        setQuality();


        mEngine = QNRTCEngine.createEngine(getApplicationContext(), setting, this);

        //视频流
        localVideoTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.VIDEO_CAMERA)
                .setMaster(true)
                .create();

        //音频流
        localAudioTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.AUDIO)
                .setBitrate(64 * 1000)// 设置音频码率
                .setMaster(true)
                .create();


        MyTrackInfo myTrackInfo = new MyTrackInfo(userPhone, null, null);
        trackInfoMap.put(userPhone, myTrackInfo);
        playingTrack = myTrackInfo;


        initBeauty();

        mEngine.joinRoom(roomToken);


    }


    /**
     * 设置画面质量
     */
    private void setQuality() {
        //是否开启硬编码
        boolean isHwCodec = false;
        //设置是否固定分辨率
        boolean isMaintainRes = false;
        int videoWidth = DEFAULT_RESOLUTION[1][0];
        int videoHeight = DEFAULT_RESOLUTION[1][1];
        int fps = DEFAULT_FPS[1];
        int videoBitrate = DEFAULT_BITRATE[1];


        QNVideoFormat format = new QNVideoFormat(videoWidth, videoHeight, fps);
        setting = new QNRTCSetting();
        setting.setCameraID(QNRTCSetting.CAMERA_FACING_ID.FRONT)//配置默认摄像头 ID
                .setHWCodecEnabled(isHwCodec)//是否开启硬编码
                .setMaintainResolution(isMaintainRes)//设置是否固定分辨率
                .setVideoBitrate(videoBitrate)//毕特率
                .setVideoEncodeFormat(format)//配置视频的编码格式
                .setVideoPreviewFormat(format);//配置预览的编码格式
        showLog("视频清晰度配置:" + videoWidth + "-" + videoHeight + "-" + fps + "-" + videoBitrate);


    }


    private void setTrackQuality(int type) {
        int videoWidth = DEFAULT_RESOLUTION[type][0];
        int videoHeight = DEFAULT_RESOLUTION[type][1];
        int fps = DEFAULT_FPS[type];
        QNVideoFormat format = new QNVideoFormat(videoWidth, videoHeight, fps);


        localVideoTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.VIDEO_CAMERA)
                .setMaster(true)
                .setVideoPreviewFormat(format)// 配置预览格式
                .setVideoEncodeFormat(format)// 配置编码格式
                .create();
    }


    /**
     * 美颜
     */
    private void initBeauty() {

        /**
         *
         * bl
         * whiten 美白
         * redden 色彩饱和度(红色)
         *
         *
         */
        QNBeautySetting beautySetting = new QNBeautySetting(0.5f, 0.5f, 0.5f);
        beautySetting.setEnable(true);
        mEngine.setBeauty(beautySetting);

    }


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
            MyTrackInfo myTrackInfo = new MyTrackInfo(userPhone, localVideoTrack, localAudioTrack);
            trackInfoMap.put(userPhone, myTrackInfo);
            updateVideoFragment();
        }

    }


    @Override
    public void onRemoteUserJoined(String s, String s1) {
        LogUtil.e("直播-onRemoteUserJoined用户加入:" + s + "消息:" + s1);
        MyTrackInfo myTrackInfo = trackInfoMap.get(s);
        if (myTrackInfo == null) {
            myTrackInfo = new MyTrackInfo();
        }
        myTrackInfo.setUserId(s);
        trackInfoMap.put(s, myTrackInfo, s.equals(teacherPhone));
        updateVideoFragment();
    }

    @Override
    public void onRemoteUserLeft(String s) {
        LogUtil.e("直播-onRemoteUserLeft用户离开:" + s);

        trackInfoMap.remove(s);

        updateVideoFragment();

    }

    @Override
    public void onLocalPublished(List<QNTrackInfo> list) {
        LogUtil.e("直播-onLocalPublished数据大小:" + list.size());
    }

    private void logQNTrackInfo(List<QNTrackInfo> list) {

        for (QNTrackInfo qnTrackInfo : list) {
            LogUtil.e(qnTrackInfo.toString());
        }


    }

    @Override
    public void onRemotePublished(String s, List<QNTrackInfo> list) {


        LogUtil.e("直播-onRemotePublished数据大小:" + list.size());
        logQNTrackInfo(list);

        MyTrackInfo myTrackInfo = null;


        for (QNTrackInfo track : list) {


            myTrackInfo = trackInfoMap.get(track.getUserId());
            if (myTrackInfo == null) {
                myTrackInfo = new MyTrackInfo();
            }
            myTrackInfo.setUserId(track.getUserId());
            if (track.getTrackKind().equals(QNTrackKind.VIDEO)) {
                myTrackInfo.setVideoTrack(track);

            } else {
                myTrackInfo.setAudioTrack(track);
            }

        }


        trackInfoMap.put(myTrackInfo.getUserId(), myTrackInfo, myTrackInfo.getUserId().equals(teacherPhone));

        updateVideoFragment();


    }

    @Override
    public void onRemoteUnpublished(String s, List<QNTrackInfo> list) {
        LogUtil.e("直播-onRemoteUnpublished数据大小:" + list.size());
        //logQNTrackInfo(list);


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
        LogUtil.e("直播-onRemoteUserMuted人数:" + list.size());

    }

    @Override
    public void onSubscribed(String s, List<QNTrackInfo> list) {
        LogUtil.e("直播-onSubscribed人数:" + list.size());


    }

    @Override
    public void onKickedOut(String s) {
        LogUtil.e("onKickedOut");
    }

    @Override
    public void onStatisticsUpdated(QNStatisticsReport qnStatisticsReport) {
        LogUtil.e("直播-onStatisticsUpdated");
    }


    @Override
    public void onAudioRouteChanged(QNAudioDevice qnAudioDevice) {
        LogUtil.e("直播-onAudioRouteChanged");
    }

    @Override
    public void onCreateMergeJobSuccess(String s) {
        LogUtil.e("直播-onCreateMergeJobSuccess");
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

    /**
     * 视频列表回调
     */
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

                initWhiteBorad(SECOND_INIT);
                return;
            }


            initWhiteBoradStae();

        }

        @Override
        public void onPPTClick() {

        }


    };


    /**
     *==============================================================================================
     * ==========================================================================白板
     * =============================================================================================
     */

    /**
     * 初始化白板状态
     */
    private void initWhiteBoradStae() {
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


    private WhiteBroadView whiteBroadView;


    private WhiteBoardUtils whiteBoardUtils;


    private static Room whiteBoardRoom;
    final String SCENE_DIR = "/dir";
    private int pageIndex = 1;//记录当前页面位置
    private int maxSize = 1;//当前页面的总大小

    private int FIRST_INIT = 1;//首次初始化
    private int SECOND_INIT = 2;//首次初始化

    /**
     * 初始化白板
     */
    private void initWhiteBorad(final int type) {

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

                    if (type == SECOND_INIT) {
                        initWhiteBoradStae();
                    }

                }
            }

            @Override
            public void onRoomStateChange() {

                ///getScenesSize();

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


    /**
     * 白板监听
     */
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

        @Override
        public void onWihteBoradScreenChange() {
            //在横竖平切换的时候刷新一下白板的数量显示
            getScenesSize();
        }
    };


    private List<Scene> sceneList = new ArrayList<>();


    /**
     * 获取当前白板数量
     */
    public void getScenesSize() {

        if (whiteBoardRoom != null) {


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
    }


    /**
     * ==============================================================================================
     * ==========================================================================聊天
     * =============================================================================================
     */
    public static List<IMBean> messageList = new ArrayList<>();
    /**
     * 聊天回调
     */
    private ChatFragment.ChatFragmentListener chatFragmentListener = new ChatFragment.ChatFragmentListener() {
        @Override
        public void onRequestVoiceSuccess() {
            requestVoiceSuccess();


            roomControlFragment.isOpenVoiceUI(true);


        }

        @Override
        public void onRequestCameraSuccess() {
            requestCameraSuccess();

            roomControlFragment.isOpenCameraUI(true);


        }

        @Override
        public void onGetUserList(List<IMBean.UserListBean> userList) {


            for (IMBean.UserListBean userListBean : userList) {
                MyTrackInfo myTrackInfo = trackInfoMap.get(userListBean.getUserPhone());
                if (myTrackInfo == null) {
                    myTrackInfo = new MyTrackInfo();
                }
                myTrackInfo.setUserId(userListBean.getUserPhone());
                myTrackInfo.setUserIcon(userListBean.getUserIcon());
                myTrackInfo.setUserName(userListBean.getUserName());
                trackInfoMap.put(userListBean.getUserPhone(), myTrackInfo, userListBean.getUserPhone().equals(teacherPhone));

            }

            updateVideoFragment();


//            MyTrackInfo myTrackInfo = trackInfoMap.get(userPhone);
//
//            int voice = myTrackInfo.getVoice();
//            int camera = myTrackInfo.getCamera();
//            if (voice==0){
//                //当前用户没有开启麦克风
//
//            }


        }

        @Override
        public void onUserJoinRoom(IMBean.ObjectBean user) {


            MyTrackInfo myTrackInfo = trackInfoMap.get(user.getUserPhone());
            if (myTrackInfo == null) {
                myTrackInfo = new MyTrackInfo();
            }
            myTrackInfo.setUserId(user.getUserPhone());
            myTrackInfo.setUserIcon(user.getUserIcon());
            myTrackInfo.setUserName(user.getUserName());
            trackInfoMap.put(user.getUserPhone(), myTrackInfo, user.getUserPhone().equals(teacherPhone));

            updateVideoFragment();

            if (roomControlFragment != null) {
                roomControlFragment.updateUserInfo();
            }

        }

        @Override
        public void onUserLeftRoom(IMBean.ObjectBean user) {

            trackInfoMap.remove(user.getUserPhone());

            updateVideoFragment();

        }

        @Override
        public void onUpdateUserInfo(IMBean.ObjectBean user) {
            MyTrackInfo myTrackInfo = trackInfoMap.get(user.getUserPhone());
            if (myTrackInfo == null) {
                myTrackInfo = new MyTrackInfo();
            }

            myTrackInfo.setUserId(user.getUserPhone());
            myTrackInfo.setUserIcon(user.getUserIcon());
            myTrackInfo.setUserName(user.getUserName());
            trackInfoMap.put(user.getUserPhone(), myTrackInfo, user.getUserPhone().equals(teacherPhone));

            updateVideoFragment();


            if (roomControlFragment != null) {
                roomControlFragment.updateUserInfo();
            }

        }

        @Override
        public void onUpdateStudentVoiceCameraInfo(IMBean.ObjectBean user) {

            int type = user.getType();

            String action = user.getAction();

            if (type == 2) {
                //麦克风

                if (action.equals("1")) {
                    //打开

                    if (roomControlFragment != null) {
                        roomControlFragment.isOpenVoiceUI(true);
                    }


                } else if (action.equals("0")) {
                    //关闭
                    if (roomControlFragment != null) {
                        roomControlFragment.isOpenVoiceUI(false);
                    }
                }


            } else if (type == 3) {
                //摄像头

                if (action.equals("1")) {
                    //打开
                    if (roomControlFragment != null) {
                        roomControlFragment.isOpenCameraUI(true);
                    }

                } else if (action.equals("0")) {
                    //关闭
                    if (roomControlFragment != null) {
                        roomControlFragment.isOpenCameraUI(false);
                    }

                }

            }


            roomControlFragment.isOpenCameraUI(true);
        }
    };
    /**
     *==============================================================================================
     * ==========================================================================控制页面
     * =============================================================================================
     */


    /**
     * 控制页回调
     */
    private RoomControlFragment.RoomControlFragmentListener roomControlFragmentListener = new RoomControlFragment.RoomControlFragmentListener() {


        @Override
        public void onCameraClick(int type) {
            switch (type) {

                case 0: {
                    //关闭

                    localVideoTrack.setMuted(true);
                    mEngine.muteTracks(Arrays.asList(localVideoTrack));

                    if (!identity.equals("1")) {
                        if (chatFragment != null) {
                            chatFragment.requestClocseCamera();
                        }

                    }
                    if (roomControlFragment != null) {
                        roomControlFragment.isOpenCameraUI(false);
                    }
                    break;
                }

                case 1: {
                    //打开

                    if (identity.equals("1")) {
                        localVideoTrack.setMuted(false);
                        mEngine.muteTracks(Arrays.asList(localVideoTrack));
                        if (roomControlFragment != null) {
                            roomControlFragment.isOpenCameraUI(true);
                        }
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
                    if (!identity.equals("1")) {
                        if (chatFragment != null) {
                            chatFragment.requestCloseVoice();
                        }

                    }

                    if (roomControlFragment != null) {
                        roomControlFragment.isOpenVoiceUI(false);
                    }


                    break;
                }

                case 1: {
                    //打开

                    if (identity.equals("1")) {
                        localAudioTrack.setMuted(false);
                        mEngine.muteTracks(Arrays.asList(localAudioTrack));

                        if (roomControlFragment != null) {
                            roomControlFragment.isOpenVoiceUI(true);
                        }
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

//        @Override
//        public void closeUserCamera(String userId) {
//
//            QNTrackInfo qnTrackInfo = trackInfoMap.get(userId).getVideoTrack();
//
//            if (qnTrackInfo.isMuted()) {
//                qnTrackInfo.setMuted(false);
//
//            } else {
//                qnTrackInfo.setMuted(true);
//            }
//            mEngine.muteTracks(Arrays.asList(qnTrackInfo));
//
//
//        }
//
//        @Override
//        public void closeUserVoice(String userId) {
//            QNTrackInfo qnTrackInfo = trackInfoMap.get(userId).getAudioTrack();
//
//            if (qnTrackInfo.isMuted()) {
//                qnTrackInfo.setMuted(false);
//
//            } else {
//                qnTrackInfo.setMuted(true);
//            }
//            mEngine.muteTracks(Arrays.asList(qnTrackInfo));
//
//        }

        @Override
        public void onChangeRotate() {


//            if (whiteBoardRoom != null) {
//                whiteBoardRoom.refreshViewSize();
//                showLog("刷新白板");
//            }
        }

        @Override
        public void onWihteBoradAdd() {


            maxSize++;


            Scene scene = new Scene("page" + maxSize);
            whiteBoardRoom.putScenes(SCENE_DIR, new Scene[]{
                    scene}, maxSize - 1);


            showLog("添加画板:" + maxSize + "画板索引:" + (maxSize - 1));

            whiteBoardRoom.setScenePath(SCENE_DIR + "/page" + maxSize);

            showLog("画板路径:" + SCENE_DIR + "/page" + maxSize);


            getScenesSize();
        }

        @Override
        public void onTeacherCloseClass() {

            if (chatFragment != null) {
                chatFragment.requestOverClass();

            }

        }

        @Override
        public void onQualityClick(int i) {


            setTrackQuality(i);


        }


    };

    /**
     * ==============================================================================================
     * ==========================================================================EventBus
     * =============================================================================================
     */

    @Subscriber(tag = EventBusTagUtils.BaseActivity)
    public void fromBaseActivity(Event event) {

        switch (event.getEventCode()) {

            case 1: {


                //去充值

                ARouter.getInstance().build(ARouterPathUtils.User_RechargeActivity).navigation();
                //去充值前需要手动调用退出直播间
                mEngine.leaveRoom();
                showLog("进入充值页面,退出直播间");

                break;
            }

            case 2: {


                finish();


                break;
            }


            case 3: {
                //充值完成后重新进入直播间
                mEngine.joinRoom(roomToken);

                showLog("充值完成重新进入直播间");

                break;
            }


        }


    }


}
