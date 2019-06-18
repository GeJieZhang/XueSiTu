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
import com.live.fragment.ListVideoFragment;
import com.live.fragment.RoomControlFragment;
import com.live.utils.Config;
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

    //QNSurfaceView remoteSurfaceView;


    FrameLayout list_video;
    FrameLayout f_controller;
    private QNRTCEngine mEngine;
    private QNTrackInfo localVideoTrack;

    private QNTrackInfo localAudioTrack;
//    private int mScreenWidth = 0;
//    private int mScreenHeight = 0;


    List<QNTrackInfo> trackInfoList = new ArrayList<>();


    private static final int BITRATE_FOR_SCREEN_VIDEO = (int) (1.5 * 1000 * 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//        getWindow().getDecorView().setSystemUiVisibility(getSystemUiVisibility());
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.statusBarTintColor(this, getResources().getColor(R.color.black));
//        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
//        mScreenWidth = outMetrics.widthPixels;
//        mScreenHeight = outMetrics.heightPixels;

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


        FragmentCustomUtils.setFragment(this, R.id.list_video, listVideoFragment, FragmentTag.List_Video);
        FragmentCustomUtils.setFragment(this, R.id.f_controller, roomControlFragment, FragmentTag.Room_Controller);
        //FragmentCustomUtils.removeFragment(this,listVideoFragment,FragmentTag.List_Video);
        // FragmentCustomUtils.addFragment(this, R.id.list_video, listVideoFragment, FragmentTag.List_Video);

        //listVideoFragment.setTrackInfo(mEngine, trackInfoList);
    }

    private void updateVideoFragment() {
        trackInfoList.remove(localVideoTrack);
        listVideoFragment.setTrackInfo(mEngine, trackInfoList);
    }


    private void findView() {
        localSurfaceView = findViewById(R.id.local_surface_view);

        list_video = findViewById(R.id.list_video);

        f_controller = findViewById(R.id.f_controller);
        //remoteSurfaceView = findViewById(R.id.remote_surface_view);
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
        trackInfoList.add(localVideoTrack);

        mEngine.setRenderWindow(localVideoTrack, localSurfaceView);
        LogUtil.e(roomToken);
        mEngine.joinRoom(roomToken);
    }


    //--------------------------------------------------------------QNRTCEngineEventListener

    @Override
    public void onRoomStateChanged(QNRoomState qnRoomState) {


        LogUtil.e("onRoomStateChanged");

        switch (qnRoomState) {
            case CONNECTED:
                mEngine.publishTracks(Arrays.asList(localVideoTrack, localAudioTrack));


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
                trackInfoList.add(track);
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

                trackInfoList.remove(track);
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

    //--------------------------------------------------------------判断是不是主播，如果是设置为主屏观看


    private ListVideoFragment.ListVideoFragmentListener listVideoFragmentListener = new ListVideoFragment.ListVideoFragmentListener() {

        @Override
        public void onFindAdmin(QNTrackInfo trackInfo, QNSurfaceView qnSurfaceView) {
            mEngine.setRenderWindow(localVideoTrack, null);
            mEngine.setRenderWindow(trackInfo, null);


            mEngine.setRenderWindow(trackInfo, qnSurfaceView);
            mEngine.setRenderWindow(localVideoTrack, qnSurfaceView);


        }

        @Override
        public void onChangeQNSurfaceView(QNTrackInfo trackInfo, QNSurfaceView qnSurfaceView) {

//            QNTrackInfo trackInfo1 = trackInfo;
//            for (QNTrackInfo qnTrackInfo : trackInfoList) {
//
//                if (qnTrackInfo.getUserId().equals(trackInfo1.getUserId())) {
//                    qnTrackInfo = localVideoTrack;
//                    localVideoTrack = trackInfo1;
//
//
//                }
//
//            }
//            updateVideoFragment();

            // mEngine.setRenderWindow(trackInfo, localSurfaceView);


//            mEngine.setRenderWindow(localVideoTrack, null);
//            mEngine.setRenderWindow(trackInfo, null);
//
//            if (trackInfo.getUserId().equals(localVideoTrack.getUserId())) {
//                mEngine.setRenderWindow(localVideoTrack, localSurfaceView);
//                mEngine.setRenderWindow(trackInfo, qnSurfaceView);
//            } else {
//                mEngine.setRenderWindow(localVideoTrack, qnSurfaceView);
//                mEngine.setRenderWindow(trackInfo, localSurfaceView);
//            }


        }


    };


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


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}
