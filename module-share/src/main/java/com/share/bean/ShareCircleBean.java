package com.share.bean;

import java.util.List;

public class ShareCircleBean {


    /**
     * code : 200
     * obj : {"total":5,"limit":10,"page":0,"rows":[{"user_name":"SiterZzz","user_icon":"http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5","title":"小学数学","share_circle_id":5,"business_id":27,"business_type":0,"content":"我正在上数学，快来加入我吧！","like_count":1,"comment_count":5,"like_status":1,"create_date":"2019-08-21 09:57:14","current_value":"","file":[],"type":"","question_id":"","artic_cover":"","business_link":"http://192.168.0.10:88/curriculum/curriculum_accompany.html?id=27&title=%E6%95%B0%E5%AD%A6","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"SiterZzz","user_icon":"http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5","title":"","share_circle_id":4,"business_id":166,"business_type":5,"content":"","like_count":2,"comment_count":1,"like_status":0,"create_date":"2019-08-21 09:44:47","current_value":"10","file":["FgooBU51fKcUdwyAJsxHY27bYY9E"],"type":"0","question_id":"166","artic_cover":"","business_link":"","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","title":"","share_circle_id":6,"business_id":30,"business_type":7,"content":"我分享了SiterZzz老师的主页。","like_count":2,"comment_count":0,"like_status":0,"create_date":"2019-08-21 09:44:47","current_value":"","file":[],"type":"","question_id":"","artic_cover":"","business_link":"http://192.168.0.10:88/personal/teacherHome.html?id=30&title=SiterZzz","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"Nnfjf","user_icon":"http://pvjdparam.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","title":"","share_circle_id":2,"business_id":165,"business_type":5,"content":"","like_count":0,"comment_count":0,"like_status":0,"create_date":"2019-08-20 17:06:06","current_value":"10","file":["FtMospkMNOQjZMjwPAn2gQSkMg8C"],"type":"2","question_id":"165","artic_cover":"","business_link":"","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"Nnfjf","user_icon":"http://pvjdparam.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","title":"","share_circle_id":3,"business_id":15,"business_type":6,"content":"","like_count":1,"comment_count":0,"like_status":0,"create_date":"2019-08-20 17:06:06","current_value":"","file":[],"type":"","question_id":"","artic_cover":"https://p0.ssl.qhimg.com/t01b272d5bb2f5550aa.png","business_link":"http://192.168.0.10:88/article/article_content.html?id=15","is_rabbit_training":"0","artic_create_date":"2019-08-07 17:37:21","artic_title":"暑假花几万体验海外沉浸式教育？业内人士：逗死了！","theme_content":""}]}
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
         * rows : [{"user_name":"SiterZzz","user_icon":"http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5","title":"小学数学","share_circle_id":5,"business_id":27,"business_type":0,"content":"我正在上数学，快来加入我吧！","like_count":1,"comment_count":5,"like_status":1,"create_date":"2019-08-21 09:57:14","current_value":"","file":[],"type":"","question_id":"","artic_cover":"","business_link":"http://192.168.0.10:88/curriculum/curriculum_accompany.html?id=27&title=%E6%95%B0%E5%AD%A6","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"SiterZzz","user_icon":"http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5","title":"","share_circle_id":4,"business_id":166,"business_type":5,"content":"","like_count":2,"comment_count":1,"like_status":0,"create_date":"2019-08-21 09:44:47","current_value":"10","file":["FgooBU51fKcUdwyAJsxHY27bYY9E"],"type":"0","question_id":"166","artic_cover":"","business_link":"","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"学员13256","user_icon":"http://pvjdparam.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","title":"","share_circle_id":6,"business_id":30,"business_type":7,"content":"我分享了SiterZzz老师的主页。","like_count":2,"comment_count":0,"like_status":0,"create_date":"2019-08-21 09:44:47","current_value":"","file":[],"type":"","question_id":"","artic_cover":"","business_link":"http://192.168.0.10:88/personal/teacherHome.html?id=30&title=SiterZzz","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"Nnfjf","user_icon":"http://pvjdparam.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","title":"","share_circle_id":2,"business_id":165,"business_type":5,"content":"","like_count":0,"comment_count":0,"like_status":0,"create_date":"2019-08-20 17:06:06","current_value":"10","file":["FtMospkMNOQjZMjwPAn2gQSkMg8C"],"type":"2","question_id":"165","artic_cover":"","business_link":"","is_rabbit_training":"","artic_create_date":"","artic_title":"","theme_content":""},{"user_name":"Nnfjf","user_icon":"http://pvjdparam.bkt.clouddn.com/FvzNv6N9uLHXezqN7RPW_5_lqXao","title":"","share_circle_id":3,"business_id":15,"business_type":6,"content":"","like_count":1,"comment_count":0,"like_status":0,"create_date":"2019-08-20 17:06:06","current_value":"","file":[],"type":"","question_id":"","artic_cover":"https://p0.ssl.qhimg.com/t01b272d5bb2f5550aa.png","business_link":"http://192.168.0.10:88/article/article_content.html?id=15","is_rabbit_training":"0","artic_create_date":"2019-08-07 17:37:21","artic_title":"暑假花几万体验海外沉浸式教育？业内人士：逗死了！","theme_content":""}]
         */

        private int total;
        private int limit;
        private int page;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
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
             * user_name : SiterZzz
             * user_icon : http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5
             * title : 小学数学
             * share_circle_id : 5
             * business_id : 27
             * business_type : 0
             * content : 我正在上数学，快来加入我吧！
             * like_count : 1
             * comment_count : 5
             * like_status : 1
             * create_date : 2019-08-21 09:57:14
             * current_value :
             * file : []
             * type :
             * question_id :
             * artic_cover :
             * business_link : http://192.168.0.10:88/curriculum/curriculum_accompany.html?id=27&title=%E6%95%B0%E5%AD%A6
             * is_rabbit_training :
             * artic_create_date :
             * artic_title :
             * theme_content :
             */

            private String user_name;
            private String user_icon;
            private String title;
            private int share_circle_id;
            private int business_id;
            private int business_type;
            private String content;
            private int like_count;
            private int comment_count;
            private int like_status;
            private String create_date;
            private String current_value;
            private String type;
            private String question_id;
            private String artic_cover;
            private String business_link;
            private String is_rabbit_training;
            private String artic_create_date;
            private String artic_title;
            private String theme_content;
            private List<String> file;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getShare_circle_id() {
                return share_circle_id;
            }

            public void setShare_circle_id(int share_circle_id) {
                this.share_circle_id = share_circle_id;
            }

            public int getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(int business_id) {
                this.business_id = business_id;
            }

            public int getBusiness_type() {
                return business_type;
            }

            public void setBusiness_type(int business_type) {
                this.business_type = business_type;
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

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getLike_status() {
                return like_status;
            }

            public void setLike_status(int like_status) {
                this.like_status = like_status;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getCurrent_value() {
                return current_value;
            }

            public void setCurrent_value(String current_value) {
                this.current_value = current_value;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(String question_id) {
                this.question_id = question_id;
            }

            public String getArtic_cover() {
                return artic_cover;
            }

            public void setArtic_cover(String artic_cover) {
                this.artic_cover = artic_cover;
            }

            public String getBusiness_link() {
                return business_link;
            }

            public void setBusiness_link(String business_link) {
                this.business_link = business_link;
            }

            public String getIs_rabbit_training() {
                return is_rabbit_training;
            }

            public void setIs_rabbit_training(String is_rabbit_training) {
                this.is_rabbit_training = is_rabbit_training;
            }

            public String getArtic_create_date() {
                return artic_create_date;
            }

            public void setArtic_create_date(String artic_create_date) {
                this.artic_create_date = artic_create_date;
            }

            public String getArtic_title() {
                return artic_title;
            }

            public void setArtic_title(String artic_title) {
                this.artic_title = artic_title;
            }

            public String getTheme_content() {
                return theme_content;
            }

            public void setTheme_content(String theme_content) {
                this.theme_content = theme_content;
            }

            public List<String> getFile() {
                return file;
            }

            public void setFile(List<String> file) {
                this.file = file;
            }
        }
    }
}
