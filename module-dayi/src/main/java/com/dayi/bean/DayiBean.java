package com.dayi.bean;

import java.util.List;

public class DayiBean {


    /**
     * code : 200
     * obj : {"article_rabbit_list":[{"title":"早间新闻1","access":1,"is_rabbit_training":0,"cover":"http://pvjdparam.bkt.clouddn.com/8.jpg","artic_link":""},{"title":"东安新城","access":4,"is_rabbit_training":0,"cover":"http://www.sc.xinhuanet.com/content/2019-08/01/1124824477_15646262207951n.jpg","artic_link":""},{"title":"朝中社","access":0,"is_rabbit_training":0,"cover":"http://p0.qhimg.com/t01ab70bdfdca727226.jpg","artic_link":""}],"recent_question":[{"identity":1,"create_date":"3小时18分钟前","msg":"老师***回答了一个小学四年级的语文问题"},{"identity":1,"create_date":"3小时18分钟前","msg":"老师***回答了一个小学四年级的语文问题"},{"identity":1,"create_date":"3小时18分钟前","msg":"老师***回答了一个小学四年级的语文问题"}],"famous_teacher_list":[{"teacher_link":"","teacher_icon":"http://pvjdparam.bkt.clouddn.com/FhMjg8aNR8FYnPYEkOX2IEvhRm4F"},{"teacher_link":"","teacher_icon":"http://pvjdparam.bkt.clouddn.com/FhweFV9WY-z92gH2DmY8cDmS4kab"},{"teacher_link":"","teacher_icon":"http://pvjdparam.bkt.clouddn.com/Fj6rMNwFu_5kt4CZdSrtITZcMlDc"}],"public_class_carousel":[{"cover_url":"http://pvjdparam.bkt.clouddn.com/FjGvXI8Y_n82x1M_3IedA_-7DMBo","title":"《用欣赏的眼光看孩子》"},{"cover_url":"http://pvjdparam.bkt.clouddn.com/FjgQTLpk5pnfkla-mqmKnJFOfq3Y","title":"《中国地理公开课》"}],"public_class_list":[{"cover_url":"http://pvjdparam.bkt.clouddn.com/FksPJZgyPTmXizG8Wn_piFKK9zf-","title":"《控制脾气》"},{"cover_url":"http://pvjdparam.bkt.clouddn.com/FmI7G24bacGeIm2fVFcKYP6IjsTE","title":"《学会思考》"},{"cover_url":"http://pvjdparam.bkt.clouddn.com/FmI7G24bacGeIm2fVFcKYP6IjsTE","title":"《学会思考》"}],"article_list":[{"title":"古惑仔","access":0,"is_rabbit_training":1,"cover":"http://p9.qhimg.com/t018565da59040d2f57.jpg","artic_link":""},{"title":"烈火英雄","access":0,"is_rabbit_training":1,"cover":"http://p2.qhimgs4.com/t01a969d422524fb622.jpg","artic_link":""}],"question_introduction":"http://pvjdparam.bkt.clouddn.com/Fhok4o2iOCGPQRS65o40p6KunpWK"}
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
         * article_rabbit_list : [{"title":"早间新闻1","access":1,"is_rabbit_training":0,"cover":"http://pvjdparam.bkt.clouddn.com/8.jpg","artic_link":""},{"title":"东安新城","access":4,"is_rabbit_training":0,"cover":"http://www.sc.xinhuanet.com/content/2019-08/01/1124824477_15646262207951n.jpg","artic_link":""},{"title":"朝中社","access":0,"is_rabbit_training":0,"cover":"http://p0.qhimg.com/t01ab70bdfdca727226.jpg","artic_link":""}]
         * recent_question : [{"identity":1,"create_date":"3小时18分钟前","msg":"老师***回答了一个小学四年级的语文问题"},{"identity":1,"create_date":"3小时18分钟前","msg":"老师***回答了一个小学四年级的语文问题"},{"identity":1,"create_date":"3小时18分钟前","msg":"老师***回答了一个小学四年级的语文问题"}]
         * famous_teacher_list : [{"teacher_link":"","teacher_icon":"http://pvjdparam.bkt.clouddn.com/FhMjg8aNR8FYnPYEkOX2IEvhRm4F"},{"teacher_link":"","teacher_icon":"http://pvjdparam.bkt.clouddn.com/FhweFV9WY-z92gH2DmY8cDmS4kab"},{"teacher_link":"","teacher_icon":"http://pvjdparam.bkt.clouddn.com/Fj6rMNwFu_5kt4CZdSrtITZcMlDc"}]
         * public_class_carousel : [{"cover_url":"http://pvjdparam.bkt.clouddn.com/FjGvXI8Y_n82x1M_3IedA_-7DMBo","title":"《用欣赏的眼光看孩子》"},{"cover_url":"http://pvjdparam.bkt.clouddn.com/FjgQTLpk5pnfkla-mqmKnJFOfq3Y","title":"《中国地理公开课》"}]
         * public_class_list : [{"cover_url":"http://pvjdparam.bkt.clouddn.com/FksPJZgyPTmXizG8Wn_piFKK9zf-","title":"《控制脾气》"},{"cover_url":"http://pvjdparam.bkt.clouddn.com/FmI7G24bacGeIm2fVFcKYP6IjsTE","title":"《学会思考》"},{"cover_url":"http://pvjdparam.bkt.clouddn.com/FmI7G24bacGeIm2fVFcKYP6IjsTE","title":"《学会思考》"}]
         * article_list : [{"title":"古惑仔","access":0,"is_rabbit_training":1,"cover":"http://p9.qhimg.com/t018565da59040d2f57.jpg","artic_link":""},{"title":"烈火英雄","access":0,"is_rabbit_training":1,"cover":"http://p2.qhimgs4.com/t01a969d422524fb622.jpg","artic_link":""}]
         * question_introduction : http://pvjdparam.bkt.clouddn.com/Fhok4o2iOCGPQRS65o40p6KunpWK
         */

        private String question_introduction;
        private List<ArticleRabbitListBean> article_rabbit_list;
        private List<RecentQuestionBean> recent_question;
        private List<FamousTeacherListBean> famous_teacher_list;
        private List<PublicClassCarouselBean> public_class_carousel;
        private List<PublicClassListBean> public_class_list;
        private List<ArticleListBean> article_list;

        public String getQuestion_introduction() {
            return question_introduction;
        }

        public void setQuestion_introduction(String question_introduction) {
            this.question_introduction = question_introduction;
        }

        public List<ArticleRabbitListBean> getArticle_rabbit_list() {
            return article_rabbit_list;
        }

        public void setArticle_rabbit_list(List<ArticleRabbitListBean> article_rabbit_list) {
            this.article_rabbit_list = article_rabbit_list;
        }

        public List<RecentQuestionBean> getRecent_question() {
            return recent_question;
        }

        public void setRecent_question(List<RecentQuestionBean> recent_question) {
            this.recent_question = recent_question;
        }

        public List<FamousTeacherListBean> getFamous_teacher_list() {
            return famous_teacher_list;
        }

        public void setFamous_teacher_list(List<FamousTeacherListBean> famous_teacher_list) {
            this.famous_teacher_list = famous_teacher_list;
        }

        public List<PublicClassCarouselBean> getPublic_class_carousel() {
            return public_class_carousel;
        }

        public void setPublic_class_carousel(List<PublicClassCarouselBean> public_class_carousel) {
            this.public_class_carousel = public_class_carousel;
        }

        public List<PublicClassListBean> getPublic_class_list() {
            return public_class_list;
        }

        public void setPublic_class_list(List<PublicClassListBean> public_class_list) {
            this.public_class_list = public_class_list;
        }

        public List<ArticleListBean> getArticle_list() {
            return article_list;
        }

        public void setArticle_list(List<ArticleListBean> article_list) {
            this.article_list = article_list;
        }

        public static class ArticleRabbitListBean {
            /**
             * title : 早间新闻1
             * access : 1
             * is_rabbit_training : 0
             * cover : http://pvjdparam.bkt.clouddn.com/8.jpg
             * artic_link :
             */

            private String title;
            private int access;
            private int is_rabbit_training;
            private String cover;
            private String artic_link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getAccess() {
                return access;
            }

            public void setAccess(int access) {
                this.access = access;
            }

            public int getIs_rabbit_training() {
                return is_rabbit_training;
            }

            public void setIs_rabbit_training(int is_rabbit_training) {
                this.is_rabbit_training = is_rabbit_training;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getArtic_link() {
                return artic_link;
            }

            public void setArtic_link(String artic_link) {
                this.artic_link = artic_link;
            }
        }

        public static class RecentQuestionBean {
            /**
             * identity : 1
             * create_date : 3小时18分钟前
             * msg : 老师***回答了一个小学四年级的语文问题
             */

            private int identity;
            private String create_date;
            private String msg;

            public int getIdentity() {
                return identity;
            }

            public void setIdentity(int identity) {
                this.identity = identity;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }

        public static class FamousTeacherListBean {
            /**
             * teacher_link :
             * teacher_icon : http://pvjdparam.bkt.clouddn.com/FhMjg8aNR8FYnPYEkOX2IEvhRm4F
             */

            private String teacher_link;
            private String teacher_icon;

            public String getTeacher_link() {
                return teacher_link;
            }

            public void setTeacher_link(String teacher_link) {
                this.teacher_link = teacher_link;
            }

            public String getTeacher_icon() {
                return teacher_icon;
            }

            public void setTeacher_icon(String teacher_icon) {
                this.teacher_icon = teacher_icon;
            }
        }

        public static class PublicClassCarouselBean {
            /**
             * cover_url : http://pvjdparam.bkt.clouddn.com/FjGvXI8Y_n82x1M_3IedA_-7DMBo
             * title : 《用欣赏的眼光看孩子》
             */

            private String cover_url;
            private String title;

            public String getCover_url() {
                return cover_url;
            }

            public void setCover_url(String cover_url) {
                this.cover_url = cover_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class PublicClassListBean {
            /**
             * cover_url : http://pvjdparam.bkt.clouddn.com/FksPJZgyPTmXizG8Wn_piFKK9zf-
             * title : 《控制脾气》
             */

            private String cover_url;
            private String title;

            public String getCover_url() {
                return cover_url;
            }

            public void setCover_url(String cover_url) {
                this.cover_url = cover_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ArticleListBean {
            /**
             * title : 古惑仔
             * access : 0
             * is_rabbit_training : 1
             * cover : http://p9.qhimg.com/t018565da59040d2f57.jpg
             * artic_link :
             */

            private String title;
            private int access;
            private int is_rabbit_training;
            private String cover;
            private String artic_link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getAccess() {
                return access;
            }

            public void setAccess(int access) {
                this.access = access;
            }

            public int getIs_rabbit_training() {
                return is_rabbit_training;
            }

            public void setIs_rabbit_training(int is_rabbit_training) {
                this.is_rabbit_training = is_rabbit_training;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getArtic_link() {
                return artic_link;
            }

            public void setArtic_link(String artic_link) {
                this.artic_link = artic_link;
            }
        }
    }
}
