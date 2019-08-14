package com.dayi.bean;

public class ToLiveBeanDaYi {


    /**
     * code : 200
     * obj : {"roomname":"oto_2","roomtoken":"3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:DIBgb1bMiz5KJpbR28xUDRLLYiM=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoib3RvXzIiLCJ1c2VySWQiOiIxMzI1NjU2MDAwMCIsImV4cGlyZUF0IjoxNTYyOTE0MTYxLCJwZXJtaXNzaW9uIjoidXNlciJ9","permission":"user","phoen":"13126815573","userid":"13256560000","uuid":"cfe6f40259a245f89395cdc5f5519fc7","whitetoken":"WHITEcGFydG5lcl9pZD11QjFvMVhqUjNZa2RxaFpxMWNHTjlNbktBcGNudEtSRWFzNGwmc2lnPTM0Y2QwOTYwM2YxODhjOTcxNmNjMTMxMmZjNGY0MGE2ZTFiOTRiYjU6YWRtaW5JZD0yNzEmcm9vbUlkPWNmZTZmNDAyNTlhMjQ1Zjg5Mzk1Y2RjNWY1NTE5ZmM3JnRlYW1JZD0zOTYmcm9sZT1yb29tJmV4cGlyZV90aW1lPTE1OTQyMTE5MDAmYWs9dUIxbzFYalIzWWtkcWhacTFjR045TW5LQXBjbnRLUkVhczRsJmNyZWF0ZV90aW1lPTE1NjI2NTQ5NDgmbm9uY2U9MTU2MjY1NDk0ODQwNDAw"}
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
         * roomname : oto_2
         * roomtoken : 3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:DIBgb1bMiz5KJpbR28xUDRLLYiM=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoib3RvXzIiLCJ1c2VySWQiOiIxMzI1NjU2MDAwMCIsImV4cGlyZUF0IjoxNTYyOTE0MTYxLCJwZXJtaXNzaW9uIjoidXNlciJ9
         * permission : user
         * phoen : 13126815573
         * userid : 13256560000
         * uuid : cfe6f40259a245f89395cdc5f5519fc7
         * whitetoken : WHITEcGFydG5lcl9pZD11QjFvMVhqUjNZa2RxaFpxMWNHTjlNbktBcGNudEtSRWFzNGwmc2lnPTM0Y2QwOTYwM2YxODhjOTcxNmNjMTMxMmZjNGY0MGE2ZTFiOTRiYjU6YWRtaW5JZD0yNzEmcm9vbUlkPWNmZTZmNDAyNTlhMjQ1Zjg5Mzk1Y2RjNWY1NTE5ZmM3JnRlYW1JZD0zOTYmcm9sZT1yb29tJmV4cGlyZV90aW1lPTE1OTQyMTE5MDAmYWs9dUIxbzFYalIzWWtkcWhacTFjR045TW5LQXBjbnRLUkVhczRsJmNyZWF0ZV90aW1lPTE1NjI2NTQ5NDgmbm9uY2U9MTU2MjY1NDk0ODQwNDAw
         */

        private String roomname;
        private String roomtoken;
        private String permission;
        private String phoen;
        private String userid;
        private String uuid;
        private String whitetoken;

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getRoomtoken() {
            return roomtoken;
        }

        public void setRoomtoken(String roomtoken) {
            this.roomtoken = roomtoken;
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

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getWhitetoken() {
            return whitetoken;
        }

        public void setWhitetoken(String whitetoken) {
            this.whitetoken = whitetoken;
        }
    }


}
