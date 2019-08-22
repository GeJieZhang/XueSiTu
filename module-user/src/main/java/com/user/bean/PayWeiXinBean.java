package com.user.bean;

import com.google.gson.annotations.SerializedName;

public class PayWeiXinBean {


    /**
     * code : 200
     * obj : {"package":"Sign=WXPay","appid":"wx2747c1e8b040c4d1","sign":"5128465D06816215CB54EAF1F9D12C54","partnerid":"1514611101","prepayid":"wx22152814100739eff206eed61722557200","noncestr":"zd4AyZK3PyeipjhK","timestamp":"1566458895"}
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
         * package : Sign=WXPay
         * appid : wx2747c1e8b040c4d1
         * sign : 5128465D06816215CB54EAF1F9D12C54
         * partnerid : 1514611101
         * prepayid : wx22152814100739eff206eed61722557200
         * noncestr : zd4AyZK3PyeipjhK
         * timestamp : 1566458895
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
