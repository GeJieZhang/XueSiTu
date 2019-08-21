package com.share.bean;

public class ToCommentBean {


    /**
     * code : 200
     * obj : {"user_name":"学员13256","user_icon":"FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":4,"create_date":"2019-08-21 15:10:13","content":"1","like_count":0,"like_status":0}
     * msg : 分享圈评论成功
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
         * user_name : 学员13256
         * user_icon : FgOLkuCYx3tqsJQAIwmXK3PHwTmG
         * comment_id : 4
         * create_date : 2019-08-21 15:10:13
         * content : 1
         * like_count : 0
         * like_status : 0
         */

        private String user_name;
        private String user_icon;
        private int comment_id;
        private String create_date;
        private String content;
        private int like_count;
        private int like_status;

        private int comment_count;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
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

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getLike_status() {
            return like_status;
        }

        public void setLike_status(int like_status) {
            this.like_status = like_status;
        }
    }
}
