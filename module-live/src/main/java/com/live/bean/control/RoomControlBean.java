package com.live.bean.control;


public class RoomControlBean {


    private boolean default_voice = true;
    private boolean default_camera = true;
    private boolean default_rotate = true;
    private int default_quality = 0;

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
}
