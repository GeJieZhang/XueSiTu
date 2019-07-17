package com.xuesitu.bean;

public class QiNiuBean {


    /**
     * code : 200
     * obj : {"baseurl":"http://pu00k0ssj.bkt.clouddn.com/","token":"3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:IHtTOnhA4PwmqOLHY-DOR32SjH0=:eyJzY29wZSI6Inh1ZXNpdHVfdjIiLCJkZWFkbGluZSI6MTU2MzM2MjI3Nn0="}
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
         * baseurl : http://pu00k0ssj.bkt.clouddn.com/
         * token : 3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:IHtTOnhA4PwmqOLHY-DOR32SjH0=:eyJzY29wZSI6Inh1ZXNpdHVfdjIiLCJkZWFkbGluZSI6MTU2MzM2MjI3Nn0=
         */

        private String baseurl;
        private String token;

        public String getBaseurl() {
            return baseurl;
        }

        public void setBaseurl(String baseurl) {
            this.baseurl = baseurl;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
