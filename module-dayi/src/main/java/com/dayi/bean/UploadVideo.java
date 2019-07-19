package com.dayi.bean;

import android.view.View;

public class UploadVideo {
    private String path;
    private String url;
    private String palyUrl;
    private View view;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPalyUrl() {
        return palyUrl;
    }

    public void setPalyUrl(String palyUrl) {
        this.palyUrl = palyUrl;
    }
}
