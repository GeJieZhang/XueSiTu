package com.user.bean;

import java.util.List;

public class ClassDetailBean {


    /**
     * code : 200
     * obj : [{"course_name":"三年级数学","order_id":"","total_class":100,"price":20,"discount":"0.8","gift":"","pay_status":"","total_price":1600,"class_time":"2019-06-26 09:00:00 - 10:00:00"}]
     * msg : 请求成功
     * seccess : true
     */

    private int code;
    private String msg;
    private boolean seccess;
    private List<ObjBean> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * course_name : 三年级数学
         * order_id :
         * total_class : 100
         * price : 20
         * discount : 0.8
         * gift :
         * pay_status :
         * total_price : 1600
         * class_time : 2019-06-26 09:00:00 - 10:00:00
         */

        private String course_name;
        private String order_id;
        private int total_class;
        private int price;
        private String discount;
        private String gift;
        private String pay_status;
        private int total_price;
        private String class_time;

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getTotal_class() {
            return total_class;
        }

        public void setTotal_class(int total_class) {
            this.total_class = total_class;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getGift() {
            return gift;
        }

        public void setGift(String gift) {
            this.gift = gift;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public String getClass_time() {
            return class_time;
        }

        public void setClass_time(String class_time) {
            this.class_time = class_time;
        }
    }
}
