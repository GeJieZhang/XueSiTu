package com.user.bean;

import java.util.List;

public class ClassDetailBean {


    /**
     * code : 200
     * obj : {"service_hotline":"400-6534-789","pay_status":0,"total_price":2970,"article_list_link":"http://192.168.0.10:88/article/article_list.html?sc=","course_list":[{"course_type":2,"course_name":"三年级英语精品衔接班","course_id":44,"total_class":110,"price":30,"discount":"0.9","class_time":"2019-07-19 08:00:00 - 09:00:00"}],"order_id":"MC20190812142236769577758930","tk_protocol_link":"http://192.168.0.10:88/agreement.html?mark=tk_protocol"}
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
         * service_hotline : 400-6534-789
         * pay_status : 0
         * total_price : 2970
         * article_list_link : http://192.168.0.10:88/article/article_list.html?sc=
         * course_list : [{"course_type":2,"course_name":"三年级英语精品衔接班","course_id":44,"total_class":110,"price":30,"discount":"0.9","class_time":"2019-07-19 08:00:00 - 09:00:00"}]
         * order_id : MC20190812142236769577758930
         * tk_protocol_link : http://192.168.0.10:88/agreement.html?mark=tk_protocol
         */

        private String service_hotline;
        private int pay_status;
        private int total_price;
        private String article_list_link;
        private String order_id;
        private String tk_protocol_link;
        private List<CourseListBean> course_list;

        private int order_type;

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getService_hotline() {
            return service_hotline;
        }

        public void setService_hotline(String service_hotline) {
            this.service_hotline = service_hotline;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public String getArticle_list_link() {
            return article_list_link;
        }

        public void setArticle_list_link(String article_list_link) {
            this.article_list_link = article_list_link;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getTk_protocol_link() {
            return tk_protocol_link;
        }

        public void setTk_protocol_link(String tk_protocol_link) {
            this.tk_protocol_link = tk_protocol_link;
        }

        public List<CourseListBean> getCourse_list() {
            return course_list;
        }

        public void setCourse_list(List<CourseListBean> course_list) {
            this.course_list = course_list;
        }

        public static class CourseListBean {
            /**
             * course_type : 2
             * course_name : 三年级英语精品衔接班
             * course_id : 44
             * total_class : 110
             * price : 30
             * discount : 0.9
             * class_time : 2019-07-19 08:00:00 - 09:00:00
             */

            private int course_type;
            private String course_name;
            private int course_id;
            private int total_class;
            private int price;
            private String discount;
            private String class_time;

            public int getCourse_type() {
                return course_type;
            }

            public void setCourse_type(int course_type) {
                this.course_type = course_type;
            }

            public String getCourse_name() {
                return course_name;
            }

            public void setCourse_name(String course_name) {
                this.course_name = course_name;
            }

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
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

            public String getClass_time() {
                return class_time;
            }

            public void setClass_time(String class_time) {
                this.class_time = class_time;
            }
        }
    }
}
