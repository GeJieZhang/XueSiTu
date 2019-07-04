package com.live.bean.live;

import com.qiniu.droid.rtc.QNTrackInfo;

public class MyTrackInfo {

    private String userId;
    private QNTrackInfo videoTrack;
    private QNTrackInfo audioTrack;

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
