package com.live.bean;

public class ChatBean {

    private String content = "";
    private int type = 0;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChatBean(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
