package com.user.bean;

public class ToLiveBean {


    /**
     * code : 200
     * obj : {"roomname":"otm_33","permission":"admin","phoen":"13540354597","userid":"13540354597","token":"3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:205AA2Fu1GpfF3upHv4or8CmpwE=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoib3RtXzMzIiwidXNlcklkIjoiMTM1NDAzNTQ1OTciLCJleHBpcmVBdCI6MTU2MjIzMjM0OCwicGVybWlzc2lvbiI6ImFkbWluIn0="}
     * msg : 获取直播token成功
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
         * roomname : otm_33
         * permission : admin
         * phoen : 13540354597
         * userid : 13540354597
         * token : 3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:205AA2Fu1GpfF3upHv4or8CmpwE=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoib3RtXzMzIiwidXNlcklkIjoiMTM1NDAzNTQ1OTciLCJleHBpcmVBdCI6MTU2MjIzMjM0OCwicGVybWlzc2lvbiI6ImFkbWluIn0=
         */

        private String roomname;
        private String permission;
        private String phoen;
        private String userid;
        private String token;

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getPhoen() {
            return phoen;
        }

        public void setPhoen(String phoen) {
            this.phoen = phoen;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
