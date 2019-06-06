package com.xuesitu.bean;

public class QiNiuBean {


    /**
     * code : 200
     * obj : qiniu.accesskey:zKSPMJreai330JEVj99yHBnoEq0=:eyJzY29wZSI6InFpbml1LmJ1Y2tldCIsImRlYWRsaW5lIjoxNTU5ODAxNjg4fQ==
     * msg : 请求成功
     * seccess : true
     */

    private int code;
    private String obj;
    private String msg;
    private boolean seccess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
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
}
