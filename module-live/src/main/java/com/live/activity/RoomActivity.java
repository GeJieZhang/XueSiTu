package com.live.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.live.R;
import com.live.fragment.ControlFragment;
import com.live.utils.Config;
import com.live.utils.QNAppServer;
import com.live.utils.ToastUtils;
import com.live.utils.TrackWindowMgr;
import com.live.view.UserTrackView;
import com.qiniu.droid.rtc.QNBeautySetting;
import com.qiniu.droid.rtc.QNCameraSwitchResultCallback;
import com.qiniu.droid.rtc.QNErrorCode;
import com.qiniu.droid.rtc.QNRTCEngine;
import com.qiniu.droid.rtc.QNRTCEngineEventListener;
import com.qiniu.droid.rtc.QNRTCSetting;
import com.qiniu.droid.rtc.QNRoomState;
import com.qiniu.droid.rtc.QNSourceType;
import com.qiniu.droid.rtc.QNStatisticsReport;
import com.qiniu.droid.rtc.QNTrackInfo;
import com.qiniu.droid.rtc.QNTrackKind;
import com.qiniu.droid.rtc.QNVideoFormat;

import com.qiniu.droid.rtc.model.QNAudioDevice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.live.utils.Config.DEFAULT_BITRATE;
import static com.live.utils.Config.DEFAULT_FPS;
import static com.live.utils.Config.DEFAULT_RESOLUTION;

@Route(path = ARouterPathUtils.Live_RoomActivity)
public class RoomActivity extends Activity implements QNRTCEngineEventListener, ControlFragment.OnCallEvents {
    private static final String TAG = "RoomActivity";
    private static final int BITRATE_FOR_SCREEN_VIDEO = (int) (1.5 * 1000 * 1000);

    public static final String EXTRA_USER_ID = "USER_ID";
    public static final String EXTRA_ROOM_TOKEN = "ROOM_TOKEN";
    public static final String EXTRA_ROOM_ID = "ROOM_ID";

    private static final String[] MANDATORY_PERMISSIONS = {
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET"
    };

    private Toast mLogToast;
    private List<String> mHWBlackList = new ArrayList<>();

    private UserTrackView mTrackWindowFullScreen;
    private List<UserTrackView> mTrackWindowsList;
    private AlertDialog mKickOutDialog;

    private QNRTCEngine mEngine;
    private String mRoomToken;
    private String mUserId;
    private String mRoomId;
    private boolean mMicEnabled = true;
    private boolean mBeautyEnabled = false;
    private boolean mVideoEnabled = true;
    private boolean mSpeakerEnabled = true;
    private boolean mIsError = false;
    private boolean mIsAdmin = false;
    private boolean mIsJoinedRoom = false;
    private ControlFragment mControlFragment;
    private List<QNTrackInfo> mLocalTrackList;

    private QNTrackInfo mLocalVideoTrack;
    private QNTrackInfo mLocalAudioTrack;
    private QNTrackInfo mLocalScreenTrack;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mCaptureMode = Config.CAMERA_CAPTURE;

    private TrackWindowMgr mTrackWindowMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(getSystemUiVisibility());

        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;

        setContentView(R.layout.activity_muti_track_room);

        Intent intent = getIntent();

        //final String token = QNAppServer.getInstance().requestRoomToken(RoomActivity.this, "zhangjie", "5123");
        mRoomToken = intent.getStringExtra(EXTRA_ROOM_TOKEN);
        mUserId = intent.getStringExtra(EXTRA_USER_ID);
        mRoomId = intent.getStringExtra(EXTRA_ROOM_ID);
        mIsAdmin = mUserId.equals(QNAppServer.ADMIN_USER);

        mTrackWindowFullScreen = (UserTrackView) findViewById(R.id.track_window_full_screen);
        mTrackWindowsList = new LinkedList<UserTrackView>(Arrays.asList(
                (UserTrackView) findViewById(R.id.track_window_a),
                (UserTrackView) findViewById(R.id.track_window_b),
                (UserTrackView) findViewById(R.id.track_window_c),
                (UserTrackView) findViewById(R.id.track_window_d),
                (UserTrackView) findViewById(R.id.track_window_e),
                (UserTrackView) findViewById(R.id.track_window_f),
                (UserTrackView) findViewById(R.id.track_window_g),
                (UserTrackView) findViewById(R.id.track_window_h),
                (UserTrackView) findViewById(R.id.track_window_i)
        ));

        for (final UserTrackView view : mTrackWindowsList) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mIsAdmin) {
                        showKickoutDialog(view.getUserId());
                    }
                    return false;
                }
            });
        }

        // init Control fragment
        mControlFragment = new ControlFragment();
        mControlFragment.setArguments(intent.getExtras());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.control_fragment_container, mControlFragment);
        ft.commitAllowingStateLoss();

        // permission check
        for (String permission : MANDATORY_PERMISSIONS) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                logAndToast("Permission " + permission + " is not granted");
                setResult(RESULT_CANCELED);
                finish();
                return;
            }
        }

        // init rtcEngine and local track info list.
        initQNRTCEngine();
        initLocalTrackInfoList();

        // init decorate and set default to p2p mode
        mTrackWindowMgr = new TrackWindowMgr(mUserId, mScreenWidth, mScreenHeight, outMetrics.density
                , mEngine, mTrackWindowFullScreen, mTrackWindowsList);

        List<QNTrackInfo> localTrackListExcludeScreenTrack = new ArrayList<>(mLocalTrackList);
        localTrackListExcludeScreenTrack.remove(mLocalScreenTrack);
        mTrackWindowMgr.addTrackInfo(mUserId, localTrackListExcludeScreenTrack);

        LogUtil.e("初始化完毕");
    }

    private void initQNRTCEngine() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        int videoWidth = preferences.getInt(Config.WIDTH, DEFAULT_RESOLUTION[1][0]);
        int videoHeight = preferences.getInt(Config.HEIGHT, DEFAULT_RESOLUTION[1][1]);
        int fps = preferences.getInt(Config.FPS, DEFAULT_FPS[1]);
        boolean isHwCodec = preferences.getInt(Config.CODEC_MODE, Config.HW) == Config.HW;
        int videoBitrate = preferences.getInt(Config.BITRATE, DEFAULT_BITRATE[1]);
        boolean isMaintainRes = preferences.getBoolean(Config.MAINTAIN_RES, false);
        mCaptureMode = preferences.getInt(Config.CAPTURE_MODE, Config.CAMERA_CAPTURE);

        // get the items in hw black list, and set isHwCodec false forcibly
        String[] hwBlackList = getResources().getStringArray(R.array.hw_black_list);
        mHWBlackList.addAll(Arrays.asList(hwBlackList));
        if (mHWBlackList.contains(Build.MODEL)) {
            isHwCodec = false;
        }

        QNVideoFormat format = new QNVideoFormat(videoWidth, videoHeight, fps);
        QNRTCSetting setting = new QNRTCSetting();

        setting.setCameraID(QNRTCSetting.CAMERA_FACING_ID.FRONT)//配置默认摄像头 ID
                .setHWCodecEnabled(isHwCodec)//是否开启硬编码
                .setMaintainResolution(isMaintainRes)//设置是否固定分辨率
                .setVideoBitrate(videoBitrate)
                .setVideoEncodeFormat(format)//配置视频的编码格式
                .setVideoPreviewFormat(format);//配置预览的编码格式
        mEngine = QNRTCEngine.createEngine(getApplicationContext(), setting, this);
    }

    private void initLocalTrackInfoList() {
        mLocalTrackList = new ArrayList<>();
        mLocalAudioTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.AUDIO)
                .setMaster(true)
                .create();
        mLocalTrackList.add(mLocalAudioTrack);

        QNVideoFormat screenEncodeFormat = new QNVideoFormat(mScreenWidth / 2, mScreenHeight / 2, 15);
        switch (mCaptureMode) {
            case Config.CAMERA_CAPTURE:
                mLocalVideoTrack = mEngine.createTrackInfoBuilder()
                        .setSourceType(QNSourceType.VIDEO_CAMERA)
                        .setMaster(true)
                        .setTag(UserTrackView.TAG_CAMERA).create();
                mLocalTrackList.add(mLocalVideoTrack);
                break;
            case Config.ONLY_AUDIO_CAPTURE:
                mControlFragment.setAudioOnly(true);
                break;
            case Config.SCREEN_CAPTURE:
                mLocalScreenTrack = mEngine.createTrackInfoBuilder()
                        .setVideoPreviewFormat(screenEncodeFormat)
                        .setBitrate(BITRATE_FOR_SCREEN_VIDEO)
                        .setSourceType(QNSourceType.VIDEO_SCREEN)
                        .setMaster(true)
                        .setTag(UserTrackView.TAG_SCREEN).create();
                mLocalTrackList.add(mLocalScreenTrack);
                mControlFragment.setAudioOnly(true);
                break;
            case Config.MUTI_TRACK_CAPTURE:
                mLocalScreenTrack = mEngine.createTrackInfoBuilder()
                        .setSourceType(QNSourceType.VIDEO_SCREEN)
                        .setVideoPreviewFormat(screenEncodeFormat)
                        .setBitrate(BITRATE_FOR_SCREEN_VIDEO)
                        .setMaster(true)
                        .setTag(UserTrackView.TAG_SCREEN).create();
                mLocalVideoTrack = mEngine.createTrackInfoBuilder()
                        .setSourceType(QNSourceType.VIDEO_CAMERA)
                        .setTag(UserTrackView.TAG_CAMERA).create();
                mLocalTrackList.add(mLocalScreenTrack);
                mLocalTrackList.add(mLocalVideoTrack);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsJoinedRoom) {
            mEngine.joinRoom(mRoomToken);

            LogUtil.e("joinRoom");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEngine != null) {
            mEngine.destroy();
            mEngine = null;
        }
        if (mTrackWindowFullScreen != null) {
            mTrackWindowFullScreen.dispose();
        }
        for (UserTrackView item : mTrackWindowsList) {
            item.dispose();
        }
        mTrackWindowsList.clear();
    }

    private void logAndToast(final String msg) {
        Log.d(TAG, msg);
        if (mLogToast != null) {
            mLogToast.cancel();
        }
        mLogToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mLogToast.show();
    }

    private void disconnectWithErrorMessage(final String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle(getText(R.string.channel_error_title))
                .setMessage(errorMessage)
                .setCancelable(false)
                .setNeutralButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        })
                .create()
                .show();
    }

    private void reportError(final String description) {
        // TODO: handle error.
        if (!mIsError) {
            mIsError = true;
            disconnectWithErrorMessage(description);
        }
    }

    private void showKickoutDialog(final String userId) {
        if (mKickOutDialog == null) {
            mKickOutDialog = new AlertDialog.Builder(this)
                    .setNegativeButton(R.string.negative_dialog_tips, null)
                    .create();
        }
        mKickOutDialog.setMessage(getString(R.string.kickout_tips, userId));
        mKickOutDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.positive_dialog_tips),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEngine.kickOutUser(userId);//踢人
                    }
                });
        mKickOutDialog.show();
    }

    private void updateRemoteLogText(final String logText) {
        Log.i(TAG, logText);
        mControlFragment.updateRemoteLogText(logText);
    }

    @TargetApi(19)
    private static int getSystemUiVisibility() {
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        return flags;
    }


    /**
     * 加入/离开房间成功后会触发
     *
     * @param state
     */
    @Override
    public void onRoomStateChanged(QNRoomState state) {
        Log.i(TAG, "onRoomStateChanged:" + state.name());

        LogUtil.e("onRoomStateChanged:" + state.name());
        switch (state) {
            case RECONNECTING:
                logAndToast(getString(R.string.reconnecting_to_room));
                mControlFragment.stopTimer();

                LogUtil.e("重新连接中");
                break;
            case CONNECTED:
                mEngine.publishTracks(mLocalTrackList);//发布Tack
                logAndToast(getString(R.string.connected_to_room));
                mIsJoinedRoom = true;
                mControlFragment.startTimer();
                LogUtil.e("连接成功");
                break;
            case RECONNECTED:
                logAndToast(getString(R.string.connected_to_room));
                mControlFragment.startTimer();

                LogUtil.e("重新连接");
                break;
            case CONNECTING:
                logAndToast(getString(R.string.connecting_to, mRoomId));
                LogUtil.e("连接中");
                break;
        }
    }


    /**
     * 加入远端用户收到的回调
     *
     * @param remoteUserId
     * @param userData
     */
    @Override
    public void onRemoteUserJoined(String remoteUserId, String userData) {
        updateRemoteLogText("onRemoteUserJoined:remoteUserId = " + remoteUserId + " ,userData = " + userData);


        LogUtil.e("onRemoteUserJoined" + "加入远端用户收到的回调");
    }

    /**
     * 离开远端用户收到的回调
     *
     * @param remoteUserId
     */
    @Override
    public void onRemoteUserLeft(final String remoteUserId) {
        updateRemoteLogText("onRemoteUserLeft:remoteUserId = " + remoteUserId);
        LogUtil.e("onRemoteUserLeft" + "离开远端用户收到的回调");
    }


    /**
     * 发布本地 Tracks回调
     *
     * @param trackInfoList
     */
    @Override
    public void onLocalPublished(List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onLocalPublished");
        mEngine.enableStatistics();
        LogUtil.e("onLocalPublished" + "发布本地 Tracks回调");
    }


    /**
     * 当远端发布后回调
     *
     * @param remoteUserId
     * @param trackInfoList
     */
    @Override
    public void onRemotePublished(String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onRemotePublished:remoteUserId = " + remoteUserId);


        LogUtil.e("onRemotePublished" + "当远端发布后回调");
    }


    /**
     * 取消发布后回调
     *
     * @param remoteUserId
     * @param trackInfoList
     */
    @Override
    public void onRemoteUnpublished(final String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onRemoteUnpublished:remoteUserId = " + remoteUserId);
        if (mTrackWindowMgr != null) {
            mTrackWindowMgr.removeTrackInfo(remoteUserId, trackInfoList);
        }

        LogUtil.e("onRemoteUnpublished" + "取消发布后回调");
    }

    @Override
    public void onRemoteUserMuted(String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onRemoteUserMuted:remoteUserId = " + remoteUserId);
        if (mTrackWindowMgr != null) {
            mTrackWindowMgr.onTrackInfoMuted(remoteUserId);
        }


        LogUtil.e("onRemoteUserMuted");
    }


    /**
     * 订本地订阅远端用户媒体流成功后，会触如下回调
     *
     * @param remoteUserId
     * @param trackInfoList
     */
    @Override
    public void onSubscribed(String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onSubscribed:remoteUserId = " + remoteUserId);
        if (mTrackWindowMgr != null) {
            mTrackWindowMgr.addTrackInfo(remoteUserId, trackInfoList);
        }

        LogUtil.e("onSubscribed" + "订本地订阅远端用户媒体流成功后，会触如下回调");
    }

    @Override
    public void onKickedOut(String userId) {
        ToastUtils.s(RoomActivity.this, getString(R.string.kicked_by_admin));
        finish();
    }

    @Override
    public void onStatisticsUpdated(final QNStatisticsReport report) {
        if (report.userId == null || report.userId.equals(mUserId)) {
            if (QNTrackKind.AUDIO.equals(report.trackKind)) {
                final String log = "音频码率:" + report.audioBitrate / 1000 + "kbps \n" +
                        "音频丢包率:" + report.audioPacketLostRate;
                mControlFragment.updateLocalAudioLogText(log);
            } else if (QNTrackKind.VIDEO.equals(report.trackKind)) {
                final String log = "视频码率:" + report.videoBitrate / 1000 + "kbps \n" +
                        "视频丢包率:" + report.videoPacketLostRate + " \n" +
                        "视频的宽:" + report.width + " \n" +
                        "视频的高:" + report.height + " \n" +
                        "视频的帧率:" + report.frameRate;
                mControlFragment.updateLocalVideoLogText(log);
            }
        }
    }

    @Override
    public void onAudioRouteChanged(QNAudioDevice routing) {
        updateRemoteLogText("onAudioRouteChanged: " + routing.name());

        LogUtil.e("onAudioRouteChanged");

    }

    @Override
    public void onCreateMergeJobSuccess(String mergeJobId) {
    }

    @Override
    public void onError(int errorCode, String description) {
        if (errorCode == QNErrorCode.ERROR_TOKEN_INVALID
                || errorCode == QNErrorCode.ERROR_TOKEN_ERROR
                || errorCode == QNErrorCode.ERROR_TOKEN_EXPIRED) {
            reportError("roomToken 错误，请重新加入房间");
        } else if (errorCode == QNErrorCode.ERROR_AUTH_FAIL
                || errorCode == QNErrorCode.ERROR_RECONNECT_TOKEN_ERROR) {
            // reset TrackWindowMgr
            mTrackWindowMgr.reset();
            // display local videoTrack
            List<QNTrackInfo> localTrackListExcludeScreenTrack = new ArrayList<>(mLocalTrackList);
            localTrackListExcludeScreenTrack.remove(mLocalScreenTrack);
            mTrackWindowMgr.addTrackInfo(mUserId, localTrackListExcludeScreenTrack);
            // rejoin Room
            mEngine.joinRoom(mRoomToken);
        } else if (errorCode == QNErrorCode.ERROR_PUBLISH_FAIL) {
            reportError("发布失败，请重新加入房间发布");
        } else {
            logAndToast("errorCode:" + errorCode + " description:" + description);
        }
    }

    // Demo control
    @Override
    public void onCallHangUp() {
        if (mEngine != null) {

            mEngine.leaveRoom();
        }
        finish();
    }

    @Override
    public void onCameraSwitch() {
        if (mEngine != null) {
            mEngine.switchCamera(new QNCameraSwitchResultCallback() {
                @Override
                public void onCameraSwitchDone(boolean isFrontCamera) {
                }

                @Override
                public void onCameraSwitchError(String errorMessage) {
                }
            });
        }
    }

    @Override
    public boolean onToggleMic() {
        if (mEngine != null && mLocalAudioTrack != null) {
            mMicEnabled = !mMicEnabled;
            mLocalAudioTrack.setMuted(!mMicEnabled);
            mEngine.muteTracks(Collections.singletonList(mLocalAudioTrack));
            if (mTrackWindowMgr != null) {
                mTrackWindowMgr.onTrackInfoMuted(mUserId);
            }
        }
        return mMicEnabled;
    }

    @Override
    public boolean onToggleVideo() {
        if (mEngine != null && mLocalVideoTrack != null) {
            mVideoEnabled = !mVideoEnabled;
            mLocalVideoTrack.setMuted(!mVideoEnabled);
            if (mLocalScreenTrack != null) {
                mLocalScreenTrack.setMuted(!mVideoEnabled);
                mEngine.muteTracks(Arrays.asList(mLocalScreenTrack, mLocalVideoTrack));
            } else {
                mEngine.muteTracks(Collections.singletonList(mLocalVideoTrack));
            }
            if (mTrackWindowMgr != null) {
                mTrackWindowMgr.onTrackInfoMuted(mUserId);
            }
        }
        return mVideoEnabled;
    }

    @Override
    public boolean onToggleSpeaker() {
        if (mEngine != null) {
            mSpeakerEnabled = !mSpeakerEnabled;
            mEngine.muteRemoteAudio(!mSpeakerEnabled);
        }
        return mSpeakerEnabled;
    }

    @Override
    public boolean onToggleBeauty() {
        if (mEngine != null) {
            mBeautyEnabled = !mBeautyEnabled;
            QNBeautySetting beautySetting = new QNBeautySetting(0.5f, 0.5f, 0.5f);
            beautySetting.setEnable(mBeautyEnabled);
            mEngine.setBeauty(beautySetting);
        }
        return mBeautyEnabled;
    }
}
