package com.lib.bean;

public class RobQuestionBean {

    /**
     * code : 408
     * obj : {}
     * msg : 兔币不足
     * seccess : false
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
    }

    @Override
    public String toString() {
        return "BaseHttpBean{" +
                "code=" + code +
                ", obj=" + obj +
                ", msg='" + msg + '\'' +
                ", seccess=" + seccess +
                '}';
    }
}