package com.lib.bean;

public class ToLiveBeanBase {


    /**
     * code : 200
     * obj : {"roomname":"ec_28_29","roomtoken":"3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:6_owN8RscbeqdnjBdsSScqYvtFo=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoiZWNfMjhfMjkiLCJ1c2VySWQiOiIxMzI1NjU2MDAwMCIsImV4cGlyZUF0IjoxNTY2MjEwNDkzLCJwZXJtaXNzaW9uIjoidXNlciJ9","permission":"user","phoen":"13126815573","userid":"13256560000","uuid":"d3a25ca3d30f445f8b24a9416fc80739","info":{"live_billing_time":10,"live_billing":5,"account":9895},"whitetoken":"WHITEcGFydG5lcl9pZD11QjFvMVhqUjNZa2RxaFpxMWNHTjlNbktBcGNudEtSRWFzNGwmc2lnPTYxYjljZmY2MTNkYTU2Yzg2ZmJjNThiMWNiODJiMGE4MjIwZjU5Mzg6YWRtaW5JZD0yNzEmcm9vbUlkPWQzYTI1Y2EzZDMwZjQ0NWY4YjI0YTk0MTZmYzgwNzM5JnRlYW1JZD0zOTYmcm9sZT1yb29tJmV4cGlyZV90aW1lPTE1OTc1MDgyNDUmYWs9dUIxbzFYalIzWWtkcWhacTFjR045TW5LQXBjbnRLUkVhczRsJmNyZWF0ZV90aW1lPTE1NjU5NTEyOTMmbm9uY2U9MTU2NTk1MTI5MzExOTAw"}
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
         * roomname : ec_28_29
         * roomtoken : 3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:6_owN8RscbeqdnjBdsSScqYvtFo=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoiZWNfMjhfMjkiLCJ1c2VySWQiOiIxMzI1NjU2MDAwMCIsImV4cGlyZUF0IjoxNTY2MjEwNDkzLCJwZXJtaXNzaW9uIjoidXNlciJ9
         * permission : user
         * phoen : 13126815573
         * userid : 13256560000
         * uuid : d3a25ca3d30f445f8b24a9416fc80739
         * info : {"live_billing_time":10,"live_billing":5,"account":9895}
         * whitetoken : WHITEcGFydG5lcl9pZD11QjFvMVhqUjNZa2RxaFpxMWNHTjlNbktBcGNudEtSRWFzNGwmc2lnPTYxYjljZmY2MTNkYTU2Yzg2ZmJjNThiMWNiODJiMGE4MjIwZjU5Mzg6YWRtaW5JZD0yNzEmcm9vbUlkPWQzYTI1Y2EzZDMwZjQ0NWY4YjI0YTk0MTZmYzgwNzM5JnRlYW1JZD0zOTYmcm9sZT1yb29tJmV4cGlyZV90aW1lPTE1OTc1MDgyNDUmYWs9dUIxbzFYalIzWWtkcWhacTFjR045TW5LQXBjbnRLUkVhczRsJmNyZWF0ZV90aW1lPTE1NjU5NTEyOTMmbm9uY2U9MTU2NTk1MTI5MzExOTAw
         */

        private String roomname;
        private String roomtoken;
        private String permission;
        private String phoen;
        private String userid;
        private String uuid;
        private InfoBean info;
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

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getWhitetoken() {
            return whitetoken;
        }

        public void setWhitetoken(String whitetoken) {
            this.whitetoken = whitetoken;
        }

        public static class InfoBean {
            /**
             * live_billing_time : 10
             * live_billing : 5
             * account : 9895
             */

            private int live_billing_time;
            private int live_billing;
            private int account;

            public int getLive_billing_time() {
                return live_billing_time;
            }

            public void setLive_billing_time(int live_billing_time) {
                this.live_billing_time = live_billing_time;
            }

            public int getLive_billing() {
                return live_billing;
            }

            public void setLive_billing(int live_billing) {
                this.live_billing = live_billing;
            }

            public int getAccount() {
                return account;
            }

            public void setAccount(int account) {
                this.account = account;
            }
        }
    }
}
