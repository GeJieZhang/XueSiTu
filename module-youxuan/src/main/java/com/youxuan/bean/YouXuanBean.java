package com.youxuan.bean;

import java.util.List;

public class YouXuanBean {


    /**
     * code : 200
     * obj : {"evening_course":[{"id":4,"name":"二年级语文","start_date":"06:03:00","end_date":"08:02:00","icon":"http://ps70o9d2f.bkt.clouddn.com/FiiGrcUHIRoOF12XG4z9sOnckqlj"},{"id":5,"name":"一年级语文课","start_date":"08:02:00","end_date":"09:03:00","icon":"http://ps70o9d2f.bkt.clouddn.com/FiiGrcUHIRoOF12XG4z9sOnckqlj"},{"id":13,"name":"撒大苏打","start_date":"04:00:00","end_date":"10:00:00","icon":"http://ps70o9d2f.bkt.clouddn.com/Fqc1z6pvib-GTJvGK5iq7fsOJKB1"},{"id":14,"name":"啦啦啦","start_date":"03:00:00","end_date":"05:00:00","icon":"http://ps70o9d2f.bkt.clouddn.com/FiZeIhlIoIhJJZvHwpHSkU42WT8n"},{"id":15,"name":"阿萨SAS·","start_date":"06:00:00","end_date":"11:00:00","icon":"http://ps70o9d2f.bkt.clouddn.com/FiiGrcUHIRoOF12XG4z9sOnckqlj"}],"carousel":[{"id":23,"type":1,"group":"OPTIMAL_CAROUSEL","code":"","text":"","hash":"http://ps70o9d2f.bkt.clouddn.com/FjGvXI8Y_n82x1M_3IedA_-7DMBo","remark":""},{"id":24,"type":1,"group":"OPTIMAL_CAROUSEL","code":"","text":"","hash":"http://ps70o9d2f.bkt.clouddn.com/FhWC0Sd-Dxpj9XITTy1p_ChxUp9S","remark":""},{"id":25,"type":1,"group":"OPTIMAL_CAROUSEL","code":"","text":"","hash":"http://ps70o9d2f.bkt.clouddn.com/FuVKASAQPX6riKk7G1JAzLsc5jjM","remark":""}]}
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
        private List<EveningCourseBean> evening_course;
        private List<CarouselBean> carousel;

        public List<EveningCourseBean> getEvening_course() {
            return evening_course;
        }

        public void setEvening_course(List<EveningCourseBean> evening_course) {
            this.evening_course = evening_course;
        }

        public List<CarouselBean> getCarousel() {
            return carousel;
        }

        public void setCarousel(List<CarouselBean> carousel) {
            this.carousel = carousel;
        }

        public static class EveningCourseBean {
            /**
             * id : 4
             * name : 二年级语文
             * start_date : 06:03:00
             * end_date : 08:02:00
             * icon : http://ps70o9d2f.bkt.clouddn.com/FiiGrcUHIRoOF12XG4z9sOnckqlj
             */

            private int id;
            private String name;
            private String start_date;
            private String end_date;
            private String icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        public static class CarouselBean {
            /**
             * id : 23
             * type : 1
             * group : OPTIMAL_CAROUSEL
             * code :
             * text :
             * hash : http://ps70o9d2f.bkt.clouddn.com/FjGvXI8Y_n82x1M_3IedA_-7DMBo
             * remark :
             */

            private int id;
            private int type;
            private String group;
            private String code;
            private String text;
            private String hash;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
