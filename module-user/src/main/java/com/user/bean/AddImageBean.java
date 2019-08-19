package com.user.bean;

import com.user.view.MyAddImageView;

public class AddImageBean {

    private String localUrl;
    private String url;
    private MyAddImageView myAddImageView;


    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MyAddImageView getMyAddImageView() {
        return myAddImageView;
    }

    public void setMyAddImageView(MyAddImageView myAddImageView) {
        this.myAddImageView = myAddImageView;
    }
}
