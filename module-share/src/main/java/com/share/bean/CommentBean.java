package com.share.bean;

import java.util.List;

public class CommentBean {


    /**
     * code : 200
     * obj : {"total":5,"limit":10,"page":0,"rows":[{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":3,"create_date":"2019-08-21 15:10:07","content":"你好","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":4,"create_date":"2019-08-21 15:10:13","content":"哈哈哈","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":5,"create_date":"2019-08-21 15:42:30","content":"课程不错","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":6,"create_date":"2019-08-21 15:42:48","content":"挺便宜的","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":7,"create_date":"2019-08-21 15:51:11","content":"gasgasg","like_count":0,"like_status":0}]}
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
         * total : 5
         * limit : 10
         * page : 0
         * rows : [{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":3,"create_date":"2019-08-21 15:10:07","content":"你好","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":4,"create_date":"2019-08-21 15:10:13","content":"哈哈哈","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":5,"create_date":"2019-08-21 15:42:30","content":"课程不错","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":6,"create_date":"2019-08-21 15:42:48","content":"挺便宜的","like_count":0,"like_status":0},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","comment_id":7,"create_date":"2019-08-21 15:51:11","content":"gasgasg","like_count":0,"like_status":0}]
         */

        private String total;
        private String limit;
        private String page;
        private List<RowsBean> rows;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * user_name : 学员13256
             * user_icon : http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG
             * comment_id : 3
             * create_date : 2019-08-21 15:10:07
             * content : 你好
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
}
