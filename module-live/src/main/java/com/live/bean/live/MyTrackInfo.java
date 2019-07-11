package com.live.bean.live;

import com.qiniu.droid.rtc.QNTrackInfo;

public class MyTrackInfo {

    private String userId;
    private QNTrackInfo videoTrack;
    private QNTrackInfo audioTrack;

    private int voice=0;
    private int camera=0;
    private String userName;
    private String userIcon;

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public MyTrackInfo() {
    }

    public MyTrackInfo(String userId, QNTrackInfo videoTrack, QNTrackInfo audioTrack) {
        this.userId = userId;
        this.videoTrack = videoTrack;
        this.audioTrack = audioTrack;
    }

    public QNTrackInfo getVideoTrack() {
        return videoTrack;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVideoTrack(QNTrackInfo videoTrack) {
        this.videoTrack = videoTrack;
    }

    public QNTrackInfo getAudioTrack() {
        return audioTrack;
    }

    public void setAudioTrack(QNTrackInfo audioTrack) {
        this.audioTrack = audioTrack;
    }

    @Override
    public String toString() {
        return "MyTrackInfo{" +
                "userId='" + userId + '\'' +
                ", videoTrack=" + videoTrack +
                ", audioTrack=" + audioTrack +
                '}';
    }
}
