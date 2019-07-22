package com.dayi.bean;

import java.util.List;

public class AnswerDetail {


    /**
     * code : 200
     * obj : {"reply_type":0,"reply_id":10,"image":[],"voice":["http://pu00k0ssj.bkt.clouddn.com/4"],"video":[],"reply_total":0,"user_name":"教师1","user_icon":"FvzNv6N9uLHXezqN7RPW_5_lqXao","is_check":1,"correct":"0%","praise":"0%","is_complaint":0,"is_evaluate":0,"teacher_link":"http://192.168.0.10/index11.html?id=29&title=%E6%95%99%E5%B8%881"}
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
         * reply_type : 0
         * reply_id : 10
         * image : []
         * voice : ["http://pu00k0ssj.bkt.clouddn.com/4"]
         * video : []
         * reply_total : 0
         * user_name : 教师1
         * user_icon : FvzNv6N9uLHXezqN7RPW_5_lqXao
         * is_check : 1
         * correct : 0%
         * praise : 0%
         * is_complaint : 0
         * is_evaluate : 0
         * teacher_link : http://192.168.0.10/index11.html?id=29&title=%E6%95%99%E5%B8%881
         */

        private int reply_type;
        private int reply_id;
        private int reply_total;
        private String user_name;
        private String user_icon;
        private int is_check;
        private String correct;
        private String praise;
        private int is_complaint;
        private int is_evaluate;
        private String teacher_link;
        private List<String> image;
        private List<String> voice;
        private List<String> video;

        public int getReply_type() {
            return reply_type;
        }

        public void setReply_type(int reply_type) {
            this.reply_type = reply_type;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public int getReply_total() {
            return reply_total;
        }

        public void setReply_total(int reply_total) {
            this.reply_total = reply_total;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_icon() {
            return user_icon;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public int getIs_check() {
            return is_check;
        }

        public void setIs_check(int is_check) {
            this.is_check = is_check;
        }

        public String getCorrect() {
            return correct;
        }

        public void setCorrect(String correct) {
            this.correct = correct;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public int getIs_complaint() {
            return is_complaint;
        }

        public void setIs_complaint(int is_complaint) {
            this.is_complaint = is_complaint;
        }

        public int getIs_evaluate() {
            return is_evaluate;
        }

        public void setIs_evaluate(int is_evaluate) {
            this.is_evaluate = is_evaluate;
        }

        public String getTeacher_link() {
            return teacher_link;
        }

        public void setTeacher_link(String teacher_link) {
            this.teacher_link = teacher_link;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public List<String> getVoice() {
            return voice;
        }

        public void setVoice(List<String> voice) {
            this.voice = voice;
        }

        public List<String> getVideo() {
            return video;
        }

        public void setVideo(List<String> video) {
            this.video = video;
        }
    }
}
