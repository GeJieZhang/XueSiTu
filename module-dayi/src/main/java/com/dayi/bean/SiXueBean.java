package com.dayi.bean;

import java.util.List;

public class SiXueBean {


    /**
     * code : 200
     * obj : {"question_list":[{"file":["http://pu00k0ssj.bkt.clouddn.com/FttbETY71vECHeB6Ysrc8n0oOLTR"],"question_id":40,"current_value":10,"type":0,"discount_text":"1折付费旁听"},{"file":["http://pu00k0ssj.bkt.clouddn.com/FttbETY71vECHeB6Ysrc8n0oOLTR"],"question_id":39,"current_value":10,"type":0,"discount_text":"1折付费旁听"},{"file":["http://pu00k0ssj.bkt.clouddn.com/Fq1pmQ9wcGfajg1kIIlCK7NB7odk"],"question_id":38,"current_value":10,"type":0,"discount_text":"1折付费旁听"},{"file":["http://pu00k0ssj.bkt.clouddn.com/Fh74dLK2ZhuJKbiZ5n-vsMJzPlX3"],"question_id":37,"current_value":10,"type":0,"discount_text":"1折付费旁听"}],"question_file":[{"id":63,"type":2,"group":"QUESTION_INTRODUCTION","code":"video_1","text":"","hash":"http://pu00k0ssj.bkt.clouddn.com/movie.mp4","remark":"了解问答视频"},{"id":64,"type":1,"group":"QUESTION_INTRODUCTION","code":"img_1","text":"","hash":"http://pu00k0ssj.bkt.clouddn.com/微信图片_20190720155403.png","remark":""},{"id":65,"type":1,"group":"QUESTION_INTRODUCTION","code":"img_2","text":"","hash":"http://pu00k0ssj.bkt.clouddn.com/微信图片_20190720155835.png","remark":""}]}
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
        private List<QuestionListBean> question_list;
        private List<QuestionFileBean> question_file;

        public List<QuestionListBean> getQuestion_list() {
            return question_list;
        }

        public void setQuestion_list(List<QuestionListBean> question_list) {
            this.question_list = question_list;
        }

        public List<QuestionFileBean> getQuestion_file() {
            return question_file;
        }

        public void setQuestion_file(List<QuestionFileBean> question_file) {
            this.question_file = question_file;
        }

        public static class QuestionListBean {
            /**
             * file : ["http://pu00k0ssj.bkt.clouddn.com/FttbETY71vECHeB6Ysrc8n0oOLTR"]
             * question_id : 40
             * current_value : 10
             * type : 0
             * discount_text : 1折付费旁听
             */

            private int question_id;
            private int current_value;
            private int type;
            private String discount_text;
            private List<String> file;

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public int getCurrent_value() {
                return current_value;
            }

            public void setCurrent_value(int current_value) {
                this.current_value = current_value;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDiscount_text() {
                return discount_text;
            }

            public void setDiscount_text(String discount_text) {
                this.discount_text = discount_text;
            }

            public List<String> getFile() {
                return file;
            }

            public void setFile(List<String> file) {
                this.file = file;
            }
        }

        public static class QuestionFileBean {
            /**
             * id : 63
             * type : 2
             * group : QUESTION_INTRODUCTION
             * code : video_1
             * text :
             * hash : http://pu00k0ssj.bkt.clouddn.com/movie.mp4
             * remark : 了解问答视频
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
