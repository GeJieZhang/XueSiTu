package com.dayi.bean;

import java.util.List;

public class QuestionDetail {


    /**
     * code : 200
     * obj : {"reply_user_list":[{"reply_id":9,"reply_total":20,"user_name":"教师13126815573","user_icon":"http://pu00k0ssj.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","is_check":1,"correct":"20%","praise":"45%"},{"reply_id":10,"reply_total":0,"user_name":"教师1","user_icon":"http://pu00k0ssj.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","is_check":1,"correct":"0%","praise":"0%"}],"question":{"question_id":2,"description":"你好","create_date":"2019-07-16 15:07:37","current_value":10,"image":["http://pu00k0ssj.bkt.clouddn.com/gsdgf","http://pu00k0ssj.bkt.clouddn.com/qeqrw","http://pu00k0ssj.bkt.clouddn.com/aaaaa"],"supplement":"aaaaa","is_supplement":1,"voice":["http://pu00k0ssj.bkt.clouddn.com/adas","http://pu00k0ssj.bkt.clouddn.com/gggd","http://pu00k0ssj.bkt.clouddn.com/aaaaa"]}}
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
         * reply_user_list : [{"reply_id":9,"reply_total":20,"user_name":"教师13126815573","user_icon":"http://pu00k0ssj.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","is_check":1,"correct":"20%","praise":"45%"},{"reply_id":10,"reply_total":0,"user_name":"教师1","user_icon":"http://pu00k0ssj.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","is_check":1,"correct":"0%","praise":"0%"}]
         * question : {"question_id":2,"description":"你好","create_date":"2019-07-16 15:07:37","current_value":10,"image":["http://pu00k0ssj.bkt.clouddn.com/gsdgf","http://pu00k0ssj.bkt.clouddn.com/qeqrw","http://pu00k0ssj.bkt.clouddn.com/aaaaa"],"supplement":"aaaaa","is_supplement":1,"voice":["http://pu00k0ssj.bkt.clouddn.com/adas","http://pu00k0ssj.bkt.clouddn.com/gggd","http://pu00k0ssj.bkt.clouddn.com/aaaaa"]}
         */

        private QuestionBean question;
        private List<ReplyUserListBean> reply_user_list;

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public List<ReplyUserListBean> getReply_user_list() {
            return reply_user_list;
        }

        public void setReply_user_list(List<ReplyUserListBean> reply_user_list) {
            this.reply_user_list = reply_user_list;
        }

        public static class QuestionBean {
            /**
             * question_id : 2
             * description : 你好
             * create_date : 2019-07-16 15:07:37
             * current_value : 10
             * image : ["http://pu00k0ssj.bkt.clouddn.com/gsdgf","http://pu00k0ssj.bkt.clouddn.com/qeqrw","http://pu00k0ssj.bkt.clouddn.com/aaaaa"]
             * supplement : aaaaa
             * is_supplement : 1
             * voice : ["http://pu00k0ssj.bkt.clouddn.com/adas","http://pu00k0ssj.bkt.clouddn.com/gggd","http://pu00k0ssj.bkt.clouddn.com/aaaaa"]
             */

            private int question_id;
            private String description;
            private String create_date;
            private int current_value;
            private String supplement;
            private int is_supplement;
            private List<String> image;
            private List<String> voice;

            private int  is_visit;

            public int getIs_visit() {
                return is_visit;
            }

            public void setIs_visit(int is_visit) {
                this.is_visit = is_visit;
            }

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public int getCurrent_value() {
                return current_value;
            }

            public void setCurrent_value(int current_value) {
                this.current_value = current_value;
            }

            public String getSupplement() {
                return supplement;
            }

            public void setSupplement(String supplement) {
                this.supplement = supplement;
            }

            public int getIs_supplement() {
                return is_supplement;
            }

            public void setIs_supplement(int is_supplement) {
                this.is_supplement = is_supplement;
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
        }

        public static class ReplyUserListBean {
            /**
             * reply_id : 9
             * reply_total : 20
             * user_name : 教师13126815573
             * user_icon : http://pu00k0ssj.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao
             * is_check : 1
             * correct : 20%
             * praise : 45%
             */

            private int reply_id;
            private int reply_total;
            private String user_name;
            private String user_icon;
            private int is_check;
            private String correct;
            private String praise;
            private int is_respondent;

            public int getIs_respondent() {
                return is_respondent;
            }

            public void setIs_respondent(int is_respondent) {
                this.is_respondent = is_respondent;
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
        }
    }
}
