package com.live.bean.control;


import com.qiniu.droid.rtc.QNRTCUser;

import java.util.List;

public class RoomControlBean {


    private String roomId = "";

    private int default_quality = 0;
    private boolean default_voice = true;
    private boolean default_camera = true;
    private boolean default_rotate = true;
    private boolean default_menu = true;
    private boolean default_board = true;
    private boolean default_class = true;
    //画面质量1标清，2高清，3超清
    private int videoQuality = 1;

    public int getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(int videoQuality) {
        this.videoQuality = videoQuality;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public boolean isDefault_board() {
        return default_board;
    }

    public void setDefault_board(boolean default_board) {
        this.default_board = default_board;
    }


    public boolean isDefault_voice() {
        return default_voice;
    }

    public void setDefault_voice(boolean default_voice) {
        this.default_voice = default_voice;
    }

    public boolean isDefault_camera() {
        return default_camera;
    }

    public void setDefault_camera(boolean default_camera) {
        this.default_camera = default_camera;
    }

    public boolean isDefault_rotate() {
        return default_rotate;
    }

    public void setDefault_rotate(boolean default_rotate) {
        this.default_rotate = default_rotate;
    }

    public int getDefault_quality() {
        return default_quality;
    }

    public void setDefault_quality(int default_quality) {
        this.default_quality = default_quality;
    }


    public boolean isDefault_menu() {
        return default_menu;
    }

    public void setDefault_menu(boolean default_menu) {
        this.default_menu = default_menu;
    }

    public boolean isDefault_class() {
        return default_class;
    }

    public void setDefault_class(boolean default_class) {
        this.default_class = default_class;
    }


}
