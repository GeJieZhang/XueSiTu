package com.live.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentTag;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.status_bar.QMUI.QMUIStatusBarHelper;
import com.lib.fastkit.utils.status_bar.StatusBarUtil;
import com.lib.fastkit.views.button_deal.click.ClickUtils;
import com.lib.ui.activity.kit.BaseActivity;
import com.live.R;
import com.live.bean.control.RoomControlBean;
import com.live.fragment.ListVideoFragment;
import com.live.fragment.RoomControlFragment;
import com.live.utils.Config;
import com.live.utils.MyHashMap;
import com.live.utils.QNAppServer;
import com.live.view.UserTrackView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Unbinder;

import static com.live.utils.Config.DEFAULT_BITRATE;
import static com.live.utils.Config.DEFAULT_FPS;
import static com.live.utils.Config.DEFAULT_RESOLUTION;

@Route(path = ARouterPathUtils.Live_MainRoomActivity)
public class MainRoomActivity extends BaseRoomActivity implements QNRTCEngineEventListener {
    @Autowired(name = "roomToken")
    String roomToken;

    QNSurfaceView localSurfaceView;

    FrameLayout list_video;
    FrameLayout f_controller;
    private QNRTCEngine mEngine;
    private QNTrackInfo localVideoTrack;
    private QNTrackInfo localAudioTrack;
    //正在播放的Track
    private QNTrackInfo playingScreenVideoTrack;


    private MyHashMap<String, QNTrackInfo> trackInfoMap = new MyHashMap<>();

    //控制页面的状态管理
    public static RoomControlBean roomControlBean = new RoomControlBean();


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

        initListVideo();
        LogUtil.e("onCreate");
    }


    private ListVideoFragment listVideoFragment;

    private RoomControlFragment roomControlFragment;

    private void initListVideo() {


        listVideoFragment = new ListVideoFragment();

        listVideoFragment.setListVideoFragmentListener(listVideoFragmentListener);

        updateVideoFragment();

        roomControlFragment = new RoomControlFragment();
        roomControlFragment.setRoomControlFragmentListener(roomControlFragmentListener);

        FragmentCustomUtils.setFragment(this, R.id.list_video, listVideoFragment, FragmentTag.List_Video);
        FragmentCustomUtils.setFragment(this, R.id.f_controller, roomControlFragment, FragmentTag.Room_Controller);

    }

    private void updateVideoFragment() {

        listVideoFragment.setTrackInfo(trackInfoMap.getOrderedValues());
    }


    private void findView() {
        localSurfaceView = findViewById(R.id.local_surface_view);

        list_video = findViewById(R.id.list_video);

        f_controller = findViewById(R.id.f_controller);

    }


    private void initEngine() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        int videoWidth = preferences.getInt(Config.WIDTH, DEFAULT_RESOLUTION[1][0]);
        int videoHeight = preferences.getInt(Config.HEIGHT, DEFAULT_RESOLUTION[1][1]);
        int fps = preferences.getInt(Config.FPS, DEFAULT_FPS[1]);
        boolean isHwCodec = preferences.getInt(Config.CODEC_MODE, Config.HW) == Config.HW;
        int videoBitrate = preferences.getInt(Config.BITRATE, DEFAULT_BITRATE[1]);
        boolean isMaintainRes = preferences.getBoolean(Config.MAINTAIN_RES, false);


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
                .setTag(UserTrackView.TAG_CAMERA).create();
        localAudioTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.AUDIO)
                .setBitrate(64 * 1000)// 设置音频码率
                .setMaster(true)
                .create();


        playingScreenVideoTrack = localVideoTrack;
        trackInfoMap.put("local", playingScreenVideoTrack);

        mEngine.joinRoom(roomToken);
    }


    //--------------------------------------------------------------QNRTCEngineEventListener

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


    };


    private RoomControlFragment.RoomControlFragmentListener roomControlFragmentListener = new RoomControlFragment.RoomControlFragmentListener() {
        @Override
        public void onCameraClick() {

            localVideoTrack.setMuted(true);
            playingScreenVideoTrack.setMuted(true);
            mEngine.muteTracks(Arrays.asList(playingScreenVideoTrack, localVideoTrack));

        }

        @Override
        public void onVoiceClick() {
            localAudioTrack.setMuted(true);
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
            initListVideo();


            mEngine.setRenderWindow(localVideoTrack, localSurfaceView);


        } else {
            Toast.makeText(getApplicationContext(), "竖屏", Toast.LENGTH_SHORT).show();
            fullscreen(false);

            setContentView(R.layout.activity_main_room_vertical);
            findView();
            initListVideo();
            mEngine.setRenderWindow(localVideoTrack, localSurfaceView);


        }


    }


}
