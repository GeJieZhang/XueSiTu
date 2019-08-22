package com.user.bean;

import java.util.List;

public class OrderList {


    /**
     * code : 200
     * obj : {"account":9945,"recharge_recommend_list":[{"recharge_id":1,"recharge_name":"充值100送100","recharge_amount":100,"is_gift":1,"gift_amount":100},{"recharge_id":2,"recharge_name":"充值200送200","recharge_amount":200,"is_gift":1,"gift_amount":200},{"recharge_id":3,"recharge_name":"充值333","recharge_amount":333,"is_gift":0,"gift_amount":""},{"recharge_id":4,"recharge_name":"充值555","recharge_amount":555,"is_gift":0,"gift_amount":""},{"recharge_id":5,"recharge_name":"充值666","recharge_amount":666,"is_gift":1,"gift_amount":666}]}
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
         * account : 9945
         * recharge_recommend_list : [{"recharge_id":1,"recharge_name":"充值100送100","recharge_amount":100,"is_gift":1,"gift_amount":100},{"recharge_id":2,"recharge_name":"充值200送200","recharge_amount":200,"is_gift":1,"gift_amount":200},{"recharge_id":3,"recharge_name":"充值333","recharge_amount":333,"is_gift":0,"gift_amount":""},{"recharge_id":4,"recharge_name":"充值555","recharge_amount":555,"is_gift":0,"gift_amount":""},{"recharge_id":5,"recharge_name":"充值666","recharge_amount":666,"is_gift":1,"gift_amount":666}]
         */

        private int account;
        private List<RechargeRecommendListBean> recharge_recommend_list;

        public int getAccount() {
            return account;
        }

        public void setAccount(int account) {
            this.account = account;
        }

        public List<RechargeRecommendListBean> getRecharge_recommend_list() {
            return recharge_recommend_list;
        }

        public void setRecharge_recommend_list(List<RechargeRecommendListBean> recharge_recommend_list) {
            this.recharge_recommend_list = recharge_recommend_list;
        }

        public static class RechargeRecommendListBean {
            /**
             * recharge_id : 1
             * recharge_name : 充值100送100
             * recharge_amount : 100
             * is_gift : 1
             * gift_amount : 100
             */

            private int recharge_id;
            private String recharge_name;
            private int recharge_amount;
            private int is_gift;
            private int gift_amount;

            public int getRecharge_id() {
                return recharge_id;
            }

            public void setRecharge_id(int recharge_id) {
                this.recharge_id = recharge_id;
            }

            public String getRecharge_name() {
                return recharge_name;
            }

            public void setRecharge_name(String recharge_name) {
                this.recharge_name = recharge_name;
            }

            public int getRecharge_amount() {
                return recharge_amount;
            }

            public void setRecharge_amount(int recharge_amount) {
                this.recharge_amount = recharge_amount;
            }

            public int getIs_gift() {
                return is_gift;
            }

            public void setIs_gift(int is_gift) {
                this.is_gift = is_gift;
            }

            public int getGift_amount() {
                return gift_amount;
            }

            public void setGift_amount(int gift_amount) {
                this.gift_amount = gift_amount;
            }
        }
    }
}
