package com.xuesitu.bean;

public class MsgStateBean {


    /**
     * code : 200
     * obj : {"unread_count":0,"unread_status":0}
     * msg : 请求成功
     * seccess : true
     */

    private int code;
    private ObjBean obj;
    private String msg;
    private boolean seccess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public static class ObjBean {
        /**
         * unread_count : 0
         * unread_status : 0
         */

        private int unread_count;
        private int unread_status;

        public int getUnread_count() {
            return unread_count;
        }

        public void setUnread_count(int unread_count) {
            this.unread_count = unread_count;
        }

        public int getUnread_status() {
            return unread_status;
        }

        public void setUnread_status(int unread_status) {
            this.unread_status = unread_status;
        }
    }
}
